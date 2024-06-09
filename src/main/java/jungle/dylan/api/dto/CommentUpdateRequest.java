package jungle.dylan.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateRequest {

    private String comments;

    public CommentUpdateRequest() {
    }

    public CommentUpdateRequest(String comments) {
        this.comments = comments;
    }
}
