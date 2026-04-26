package com.AtomicGuard.AtomicGuardAPI.controller;

import com.AtomicGuard.AtomicGuardAPI.entity.CommentEntity;
import com.AtomicGuard.AtomicGuardAPI.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public CommentEntity addComment(@RequestBody CommentEntity comment, @PathVariable Long postId){
        return commentService.addComment(comment, postId);
    }

}
