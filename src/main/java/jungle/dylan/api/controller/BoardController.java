package jungle.dylan.api.controller;

import jungle.dylan.api.dto.*;
import jungle.dylan.api.dto.common.ApiResponse;
import jungle.dylan.api.service.BoardService;
import jungle.dylan.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jungle.dylan.api.constant.BoardResponseMessage.*;
import static jungle.dylan.api.constant.CommentResponseMessage.UPDATE_SUCCESS;
import static jungle.dylan.api.constant.CommentResponseMessage.WRITE_SUCCESS;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

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

    @PutMapping("{id}")
    public ApiResponse<Object> updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest) {
        BoardResponse updateBoard = boardService.update(id, boardRequest);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(updateBoard)
                .message(String.format(UPDATE_SUCCESS.getMessage(), updateBoard.getId()))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Object> deleteBoard(@PathVariable Long id) {
        Long deleteBoardId = boardService.delete(id);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format(DELETE_SUCCESS.getMessage(), deleteBoardId))
                .build();
    }

    @PostMapping("{boardId}/comment")
    public ApiResponse<Object> writeComment(@PathVariable Long boardId,
                                            @RequestBody CommentWriteRequest commentWriteRequest) {
        CommentResponse commentResponse = commentService.write(boardId, commentWriteRequest);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format(WRITE_SUCCESS.getMessage(), commentResponse.getCommentId()))
                .data(commentResponse)
                .build();
    }

    @PutMapping("{boardId}/comment/{commentId}")
    public ApiResponse<Object> updateComment(@PathVariable Long boardId,
                                             @PathVariable Long commentId,
                                      @RequestBody CommentUpdateRequest commentUpdateRequest) {
        CommentResponse commentResponse = commentService.update(commentId, commentUpdateRequest);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format(UPDATE_SUCCESS.getMessage(), commentResponse.getCommentId()))
                .data(commentResponse)
                .build();
    }

    @DeleteMapping("{boardId}/comment/{commentId}")
    public ApiResponse<Object> deleteComment(@PathVariable Long boardId,
                                             @PathVariable Long commentId) {
        Long deletedId = commentService.delete(commentId);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format(DELETE_SUCCESS.getMessage(), deletedId))
                .build();
    }
}
