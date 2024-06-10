package jungle.dylan.api.error.exception;

import jungle.dylan.api.error.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
