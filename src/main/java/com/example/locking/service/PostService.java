package com.example.locking.service;

import com.example.locking.model.Post;
import com.example.locking.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    @Retryable(retryFor = StaleObjectStateException.class, maxAttempts = 3, recover = "recoverPostProcess")
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post); // Version check happens here before commit transaction
    }

    @Recover
    public void recoverPostProcess(StaleObjectStateException e, Long postId) {
        logger.warn("Recovering likePost after retries failed for post {}", postId);
    }

    public void databaseLevelIncrement(Long postId) {
        postRepository.incrementLikeCount(postId);
    }
}
