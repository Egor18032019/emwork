package em.home.work.service;

import em.home.work.model.CommentIdResponse;
import em.home.work.model.CommentRequest;

public interface CommentServiceCommon {
    CommentIdResponse greatComment(CommentRequest request);
}
