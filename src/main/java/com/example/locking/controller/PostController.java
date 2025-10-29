package com.example.locking.controller;

import com.example.locking.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeOptimistic(@PathVariable Long id) {
        postService.likePost(id);
        //postService.databaseLevelIncrement(id);
        return ResponseEntity.ok("Liked (optimistic)");
    }
}
