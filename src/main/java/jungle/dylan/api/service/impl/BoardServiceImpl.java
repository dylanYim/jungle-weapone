package jungle.dylan.api.service.impl;

import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.user.Role;
import jungle.dylan.api.domain.user.User;
import jungle.dylan.api.dto.BoardRequest;
import jungle.dylan.api.dto.BoardResponse;
import jungle.dylan.api.exception.BoardNotFoundException;
import jungle.dylan.api.exception.UserNotFoundException;
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
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public BoardResponse update(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        User currentUser = getCurrentUser();
        if(currentUser.getRole().equals(Role.USER) && !currentUser.equals(board.getUser())){
            throw new IllegalStateException("Invalid Password");
        }
            board.update(boardRequest.getTitle(), boardRequest.getContents());

        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        User currentUser = getCurrentUser();
        if(currentUser.getRole().equals(Role.USER) && !currentUser.equals(board.getUser())){
            throw new IllegalStateException("Invalid Password");
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
                    .orElseThrow(UserNotFoundException::new);

        }

        return currentUser;
    }
}
