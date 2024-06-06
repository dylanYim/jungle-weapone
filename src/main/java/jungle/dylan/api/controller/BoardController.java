package jungle.dylan.api.controller;

import jungle.dylan.api.constant.BoardResponseMessage;
import jungle.dylan.api.dto.BoardRequest;
import jungle.dylan.api.dto.BoardResponse;
import jungle.dylan.api.dto.common.ApiResponse;
import jungle.dylan.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jungle.dylan.api.constant.BoardResponseMessage.*;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ApiResponse<Object> getBoardAll() {
        List<BoardResponse> findBoards = boardService.findAll();
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(findBoards)
                .message(SUCCESS.getMessage())
                .build();
    }

    @PostMapping
    public ApiResponse<Object> write(@RequestBody BoardRequest boardRequest) {
        BoardResponse writeBoard = boardService.write(boardRequest);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(writeBoard)
                .message(String.format(WRITE_SUCCESS.getMessage(), writeBoard.getId()))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<Object> getBoardById(@PathVariable Long id) {
        BoardResponse findBoard = boardService.findById(id);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(findBoard)
                .message(SUCCESS.getMessage())
                .build();
    }
}
