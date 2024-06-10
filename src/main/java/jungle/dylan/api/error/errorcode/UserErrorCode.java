package jungle.dylan.api.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found"),
    DUPLICATE_USER_NAME(HttpStatus.FORBIDDEN, "that username already exist"),
    UNAUTHORIZED_MODIFY(HttpStatus.FORBIDDEN, "you didn't have permission to modify."),
    UNAUTHORIZED_DELETE(HttpStatus.FORBIDDEN, "You didn't have permission to delete."),
    ;


    private final HttpStatus httpStatus;
    private final String message;

}
