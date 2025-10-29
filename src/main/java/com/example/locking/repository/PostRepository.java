package com.example.locking.repository;

import com.example.locking.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query(value = "UPDATE post SET like_count = like_count + 1 WHERE id = :postId", nativeQuery = true)
    void incrementLikeCount(@Param("postId") Long postId);
}
