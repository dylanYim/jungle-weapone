package jungle.dylan.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentWriteRequest {
    private String comments;

    public CommentWriteRequest() {
    }

    public CommentWriteRequest(String comments) {
        this.comments = comments;
    }
}
