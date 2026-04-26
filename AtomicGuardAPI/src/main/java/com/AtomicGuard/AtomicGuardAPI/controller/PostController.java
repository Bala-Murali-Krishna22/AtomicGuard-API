package com.AtomicGuard.AtomicGuardAPI.controller;

import com.AtomicGuard.AtomicGuardAPI.entity.CommentEntity;
import com.AtomicGuard.AtomicGuardAPI.entity.PostEntity;
import com.AtomicGuard.AtomicGuardAPI.enums.InteractionType;
import com.AtomicGuard.AtomicGuardAPI.service.CommentService;
import com.AtomicGuard.AtomicGuardAPI.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    private CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping
    public PostEntity createPost(@RequestBody PostEntity post){
        return postService.createPost(post);
    }

    @PostMapping("{postId}/like")
    public String likePost(@PathVariable Long postId){
        postService.handleLike(postId);
        return "Liked successfully";
    }

    @PostMapping("/{postId}/bot-reply")
    public String botReply(@PathVariable Long postId,
                           @RequestParam Long botId) {

        postService.handleBotReply(postId, botId);

        return "Bot replied successfully";
    }

}
