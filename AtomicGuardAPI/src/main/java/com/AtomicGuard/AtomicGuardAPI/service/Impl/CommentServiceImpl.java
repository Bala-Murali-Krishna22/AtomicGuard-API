package com.AtomicGuard.AtomicGuardAPI.service.Impl;

import com.AtomicGuard.AtomicGuardAPI.entity.CommentEntity;
import com.AtomicGuard.AtomicGuardAPI.enums.InteractionType;
import com.AtomicGuard.AtomicGuardAPI.repository.CommentRepository;
import com.AtomicGuard.AtomicGuardAPI.repository.PostRepository;
import com.AtomicGuard.AtomicGuardAPI.service.CommentService;
import com.AtomicGuard.AtomicGuardAPI.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private PostService postService;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, PostService postService) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.postService = postService;
    }


    @Override
    public CommentEntity addComment(CommentEntity comment, Long postId) {

        if(!postRepo.existsById(postId)){
            throw new RuntimeException("Post not found with id: " + postId);
        }

        comment.setPostId(postId);
        comment.setCreatedAt(LocalDateTime.now());
        //db
        CommentEntity savedComment = commentRepo.save(comment);
        //redis
        postService.updateViralityScore(postId, InteractionType.HUMAN_COMMENT);

        return commentRepo.save(savedComment);
    }


}
