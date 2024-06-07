package jungle.dylan.api.service;

import jungle.dylan.api.dto.BoardRequest;
import jungle.dylan.api.dto.BoardResponse;

import java.util.List;

public interface BoardService {
    List<BoardResponse> findAll();
    BoardResponse write(BoardRequest boardRequest);
    BoardResponse findById(Long id);
    BoardResponse update(Long id, BoardRequest boardRequest);
    Long delete(Long id);
}
