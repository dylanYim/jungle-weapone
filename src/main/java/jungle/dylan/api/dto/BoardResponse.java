package jungle.dylan.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jungle.dylan.api.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    Long id;
    String writer;
    String title;
    String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    public static BoardResponse of(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .writer(board.getWriter())
                .title(board.getTitle())
                .contents(board.getContents())
                .createDate(board.getCreateDate())
                .build();
    }

}
