package jungle.dylan.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BoardResponse {
    Long id;
    String username;
    String title;
    String contents;
    List<CommentResponse> comments;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    public static BoardResponse of(Board board) {
        List<Comment> comments = board.getComments();
        List<CommentResponse> commentResponses = Collections.emptyList();
        if (comments != null) {
            commentResponses = comments.stream()
                    .map(CommentResponse::of)
                    .collect(Collectors.toList());
        }

        return BoardResponse.builder()
                .id(board.getId())
                .username(board.getUser().getUsername())
                .title(board.getTitle())
                .contents(board.getContents())
                .createDate(board.getCreateDate())
                .comments(commentResponses)
                .build();
    }
}
