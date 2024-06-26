package jungle.dylan.api.constant.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtConst {
    ACCESS_TOKEN_EXPIRE_TIME(3600000),
    REFRESH_TOKEN_EXPIRE_TIME(3600000 * 24);

    private final long time;
}
