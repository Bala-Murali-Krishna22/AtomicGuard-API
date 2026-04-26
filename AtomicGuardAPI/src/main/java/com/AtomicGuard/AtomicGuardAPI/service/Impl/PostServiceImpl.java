package com.AtomicGuard.AtomicGuardAPI.service.Impl;

import com.AtomicGuard.AtomicGuardAPI.entity.PostEntity;
import com.AtomicGuard.AtomicGuardAPI.enums.InteractionType;
import com.AtomicGuard.AtomicGuardAPI.repository.PostRepository;
import com.AtomicGuard.AtomicGuardAPI.service.PostService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private StringRedisTemplate redisTemplate;

    public PostServiceImpl(PostRepository postRepo, StringRedisTemplate redisTemplate) {
        this.postRepo = postRepo;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public PostEntity createPost(PostEntity post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public void handleLike(Long postId) {
        PostEntity post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        updateViralityScore(postId, InteractionType.HUMAN_LIKE);
    }

    public void updateViralityScore(Long postId, InteractionType type) {

        String key = "post:" + postId + ":virality_score";

        int points = switch (type) {
            case BOT_REPLY -> 1;
            case HUMAN_LIKE -> 20;
            case HUMAN_COMMENT -> 50;
        };

        redisTemplate.opsForValue().increment(key, points);
    }

    @Override
    public void handleBotReply(Long postId, Long botId) {
        postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found" + postId));


        String botCountKey = "post:" + postId + ":bot_count";
        String cooldownKey = "cooldown:bot:" + botId + ":post:" + postId;

        //100 bots
        Long botCount = redisTemplate.opsForValue().increment(botCountKey);

        if (botCount > 100) {
            throw new RuntimeException("429 Too Many Requests - Bot limit reached");
        }

        //cool down
        Boolean exists = redisTemplate.hasKey(cooldownKey);

        if (Boolean.TRUE.equals(exists)) {
            throw new RuntimeException("Bot is in cooldown period");
        }

        redisTemplate.opsForValue().set(cooldownKey, "1", Duration.ofMinutes(10));

        // comment depth
        int depthLevel = 1;
        if (depthLevel > 20) {
            throw new RuntimeException("Max thread depth exceeded");
        }
        //redis
        updateViralityScore(postId, InteractionType.BOT_REPLY);
    }

}