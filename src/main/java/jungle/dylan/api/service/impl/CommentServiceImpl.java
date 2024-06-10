package jungle.dylan.api.service.impl;

import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.comment.Comment;
import jungle.dylan.api.domain.user.Role;
import jungle.dylan.api.domain.user.User;
import jungle.dylan.api.dto.CommentUpdateRequest;
import jungle.dylan.api.dto.CommentWriteRequest;
import jungle.dylan.api.dto.CommentResponse;
import jungle.dylan.api.exception.BoardNotFoundException;
import jungle.dylan.api.exception.CommentNotFoundException;
import jungle.dylan.api.exception.UserNotFoundException;
import jungle.dylan.api.repository.BoardRepository;
import jungle.dylan.api.repository.CommentRepository;
import jungle.dylan.api.repository.UserRepository;
import jungle.dylan.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @Override
    public CommentResponse write(Long boardId, CommentWriteRequest commentWriteRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        User currentUser = getCurrentUser();

        Comment comment = Comment.createComment(currentUser, board, commentWriteRequest.getComments());

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }


    @Override
    public CommentResponse update(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        User currentUser = getCurrentUser();
        if (currentUser.getRole().equals(Role.USER) && !currentUser.equals(findComment.getUser())) {
            throw new RuntimeException("권한없음"); // 수정 필요
        }

        findComment.update(commentUpdateRequest.getComments());
        return CommentResponse.of(findComment);
    }

    @Override
    public Long delete(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        Long deletedId = findComment.getId();
        User currentUser = getCurrentUser();

        if (currentUser.getRole().equals(Role.USER) && !currentUser.equals(findComment.getUser())) {
            throw new RuntimeException("권한없음"); // 수정 필요
        }

        commentRepository.delete(findComment);

        return deletedId;
    }


    private User getCurrentUser() {
        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal.getUsername());
            currentUser = userRepository.findUserByUsername(principal.getUsername())
                    .orElseThrow(UserNotFoundException::new);

        }

        return currentUser;
    }
}
