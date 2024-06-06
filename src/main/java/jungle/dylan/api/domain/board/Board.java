package jungle.dylan.api.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Board {

    @Id
    @GeneratedValue
    Long id;
    String userName;
    String title;
    String contents;
    String password;
    LocalDateTime createDate;
    LocalDateTime updateDate;
}
