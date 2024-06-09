package jungle.dylan.api.dto;

import jungle.dylan.api.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private String comments;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .comments(comment.getComments())
                .build();
    }
}
