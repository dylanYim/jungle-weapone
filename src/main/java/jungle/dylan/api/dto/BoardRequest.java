package jungle.dylan.api.dto;

import jungle.dylan.api.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardRequest {

    String writer;
    String title;
    String contents;
    String password;

    public Board toEntity() {
        return Board.builder()
                .writer(writer)
                .password(password)
                .title(title)
                .contents(contents)
                .createDate(LocalDateTime.now())
                .build();
    }

}
