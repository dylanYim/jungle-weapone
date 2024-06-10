package jungle.dylan.api.service.impl;

import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.user.Role;
import jungle.dylan.api.domain.user.User;
import jungle.dylan.api.dto.BoardRequest;
import jungle.dylan.api.dto.BoardResponse;
import jungle.dylan.api.error.exception.ApiException;
import jungle.dylan.api.repository.BoardRepository;
import jungle.dylan.api.repository.UserRepository;
import jungle.dylan.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static jungle.dylan.api.error.errorcode.BoardErrorCode.BOARD_NOT_FOUND;
import static jungle.dylan.api.error.errorcode.UserErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public List<BoardResponse> findAll() {
        List<Board> findBoards = boardRepository.findAllByOrderByCreateDate();
        return findBoards.stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BoardResponse write(BoardRequest boardRequest) {
        User currentUser = getCurrentUser();
        Board board = boardRequest.toEntity(currentUser);
        Board savedBoard = boardRepository.save(board);

        return BoardResponse.of(savedBoard);
    }

    @Override
    public BoardResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(BOARD_NOT_FOUND));

        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public BoardResponse update(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(BOARD_NOT_FOUND));
        User currentUser = getCurrentUser();
        if(currentUser.getRole().equals(Role.USER) && !currentUser.equals(board.getUser())){
            throw new ApiException(UNAUTHORIZED_MODIFY);
        }
            board.update(boardRequest.getTitle(), boardRequest.getContents());

        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(BOARD_NOT_FOUND));
        User currentUser = getCurrentUser();

        if(currentUser.getRole().equals(Role.USER) && !currentUser.equals(board.getUser())){
            throw new ApiException(UNAUTHORIZED_DELETE);
        }
        Long boardId = board.getId();
        boardRepository.delete(board);

        return boardId;
    }

    private User getCurrentUser() {
        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal.getUsername());
            currentUser = userRepository.findUserByUsername(principal.getUsername())
                    .orElseThrow(()-> new ApiException(USER_NOT_FOUND));
        }

        return currentUser;
    }
}
