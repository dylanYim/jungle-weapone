package jungle.dylan.api.dto;

import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardRequest {

    String title;
    String contents;

    public Board toEntity(User user) {
        return Board.builder()
                .user(user)
                .title(title)
                .contents(contents)
                .createDate(LocalDateTime.now())
                .build();
    }

}
