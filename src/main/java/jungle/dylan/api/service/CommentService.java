package jungle.dylan.api.service;

import jungle.dylan.api.dto.CommentUpdateRequest;
import jungle.dylan.api.dto.CommentWriteRequest;
import jungle.dylan.api.dto.CommentResponse;

public interface CommentService {
    CommentResponse write(CommentWriteRequest commentWriteRequest);
    CommentResponse update(Long commentId, CommentUpdateRequest commentUpdateRequest);
    Long delete(Long commentId);

}
