package jungle.dylan.api.dto;

import jakarta.validation.constraints.Pattern;
import jungle.dylan.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class UserRequest {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "Username must be between 4 and 10 characters long and contain only lowercase letters and digits.")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "Password must be between 8 and 15 characters long and contain only letters and digits.")
    private String password;

    public User createUser() {
        return User.builder()
                .username(username)
                .password(password)
                .createDate(LocalDateTime.now())
                .build();
    }
}
