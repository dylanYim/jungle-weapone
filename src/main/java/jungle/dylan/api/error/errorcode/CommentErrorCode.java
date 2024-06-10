package jungle.dylan.api.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode{
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "comment not found"),
    TITLE_IS_EMPTY(HttpStatus.BAD_REQUEST, "title is empty"),
    CONTENTS_IS_EMPTY(HttpStatus.BAD_REQUEST, "contents is empty"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
