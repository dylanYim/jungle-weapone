package jungle.dylan.api.domain.comment;

import jakarta.persistence.*;
import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    private String comments;
    private LocalDateTime createDate;

    public static Comment createComment(User user, Board board, String comments) {
        return Comment.builder()
                .user(user)
                .board(board)
                .comments(comments)
                .createDate(LocalDateTime.now())
                .build();
    }

    public void update(String comments) {
        this.comments = comments;
    }
}
