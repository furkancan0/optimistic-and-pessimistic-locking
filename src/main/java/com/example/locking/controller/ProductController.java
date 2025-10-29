package com.example.locking.controller;

import com.example.locking.service.PostService;
import com.example.locking.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/{id}/stock")
    public ResponseEntity<String> stockOptimistic(@PathVariable Long id) {
        productService.decreaseStock(id, 1);
        return ResponseEntity.ok("Stock decreased (pessimistic)");
    }
}