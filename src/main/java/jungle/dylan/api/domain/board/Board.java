package jungle.dylan.api.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    Long id;
    String userName;
    String title;
    String contents;
    String password;
    LocalDateTime createDate;

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
