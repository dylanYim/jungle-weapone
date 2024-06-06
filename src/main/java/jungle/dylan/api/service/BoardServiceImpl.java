package jungle.dylan.api.service;

import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.dto.BoardRequest;
import jungle.dylan.api.dto.BoardResponse;
import jungle.dylan.api.exception.BoardNotFoundException;
import jungle.dylan.api.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

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
        Board board = boardRequest.toEntity();
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
        board.update(boardRequest.getTitle(), boardRequest.getContents());

        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Long boardId = board.getId();
        boardRepository.delete(board);

        return boardId;
    }
}
