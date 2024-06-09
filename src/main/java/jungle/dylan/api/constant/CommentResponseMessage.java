package jungle.dylan.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentResponseMessage {
    SUCCESS("success"),
    WRITE_SUCCESS("write comment id [%s] success"),
    UPDATE_SUCCESS("update comment id [%s] success"),
    DELETE_SUCCESS("delete comment id [%s] success"),
    ;

    private final String message;

}
