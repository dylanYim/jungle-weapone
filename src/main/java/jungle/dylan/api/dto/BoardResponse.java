package jungle.dylan.api.dto;

import jungle.dylan.api.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    Long id;
    String userName;
    String title;
    String contents;
    LocalDateTime createDate;

    public static BoardResponse of(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .userName(board.getUserName())
                .title(board.getTitle())
                .contents(board.getContents())
                .createDate(board.getCreateDate())
                .build();
    }

}
