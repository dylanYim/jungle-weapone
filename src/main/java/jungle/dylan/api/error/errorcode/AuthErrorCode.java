package jungle.dylan.api.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "token not found please login"),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "token is invalid"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "token is expired"),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "login fail check your username and password")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
