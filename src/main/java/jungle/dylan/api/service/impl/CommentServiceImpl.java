package jungle.dylan.api.service.impl;

import jungle.dylan.api.domain.board.Board;
import jungle.dylan.api.domain.comment.Comment;
import jungle.dylan.api.domain.user.Role;
import jungle.dylan.api.domain.user.User;
import jungle.dylan.api.dto.CommentUpdateRequest;
import jungle.dylan.api.dto.CommentWriteRequest;
import jungle.dylan.api.dto.CommentResponse;
import jungle.dylan.api.error.exception.ApiException;
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

import static jungle.dylan.api.error.errorcode.BoardErrorCode.BOARD_NOT_FOUND;
import static jungle.dylan.api.error.errorcode.CommentErrorCode.COMMENT_NOT_FOUND;
import static jungle.dylan.api.error.errorcode.UserErrorCode.*;

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
                .orElseThrow(() -> new ApiException(BOARD_NOT_FOUND));
        User currentUser = getCurrentUser();

        Comment comment = Comment.createComment(currentUser, board, commentWriteRequest.getComments());

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }


    @Override
    public CommentResponse update(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(COMMENT_NOT_FOUND));

        User currentUser = getCurrentUser();
        if (currentUser.getRole().equals(Role.USER) && !currentUser.equals(findComment.getUser())) {
            throw new ApiException(UNAUTHORIZED_MODIFY);
        }

        findComment.update(commentUpdateRequest.getComments());
        return CommentResponse.of(findComment);
    }

    @Override
    public Long delete(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(COMMENT_NOT_FOUND));
        Long deletedId = findComment.getId();
        User currentUser = getCurrentUser();

        if (currentUser.getRole().equals(Role.USER) && !currentUser.equals(findComment.getUser())) {
            throw new ApiException(UNAUTHORIZED_DELETE);
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
                    .orElseThrow(() -> new ApiException(USER_NOT_FOUND));

        }

        return currentUser;
    }
}
