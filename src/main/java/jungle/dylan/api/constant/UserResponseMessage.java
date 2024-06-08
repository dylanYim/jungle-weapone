package jungle.dylan.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserResponseMessage {
    JOIN_SUCCESS("join success"),
    LOGIN_SUCCESS("login success"),
    ;

    private final String message;
}
