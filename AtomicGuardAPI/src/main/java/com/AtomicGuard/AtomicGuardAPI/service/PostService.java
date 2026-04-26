package com.AtomicGuard.AtomicGuardAPI.service;

import com.AtomicGuard.AtomicGuardAPI.entity.PostEntity;
import com.AtomicGuard.AtomicGuardAPI.enums.InteractionType;
import org.springframework.stereotype.Service;

public interface PostService {

    PostEntity createPost(PostEntity post);

    void handleLike(Long postId);

    void updateViralityScore(Long postId, InteractionType interactionType);

    void handleBotReply(Long postId, Long botId);

}
