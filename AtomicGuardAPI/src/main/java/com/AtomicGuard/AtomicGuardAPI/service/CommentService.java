package com.AtomicGuard.AtomicGuardAPI.service;

import com.AtomicGuard.AtomicGuardAPI.entity.CommentEntity;

public interface CommentService {

    CommentEntity addComment(CommentEntity entity, Long postId);

}
