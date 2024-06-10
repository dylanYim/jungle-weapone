package jungle.dylan.api.domain.board;

import jakarta.persistence.*;
import jungle.dylan.api.domain.comment.Comment;
import jungle.dylan.api.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

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
    private String writer;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String contents;
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    private String password;
    private LocalDateTime createDate;

    public void update(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
