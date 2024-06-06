package jungle.dylan.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardResponseMessage {
    SUCCESS("success"),
    WRITE_SUCCESS("write article id [%s] success"),
    UPDATE_SUCCESS("update article id [%s] success"),
    DELETE_SUCCESS("delete article id [%s] success"),
    ;

    private final String message;

}
