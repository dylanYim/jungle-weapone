package jungle.dylan.api.domain.board;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String username;
    private String title;
    private String contents;
    private String password;
    private LocalDateTime createDate;

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
