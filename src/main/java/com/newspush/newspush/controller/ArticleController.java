package com.newspush.newspush.controller;

import com.newspush.newspush.domain.enums.RssCategory;
import com.newspush.newspush.dto.ArticleResponse;
import com.newspush.newspush.dto.UserResponse;
import com.newspush.newspush.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    //유저 목록 (프론트 드롭다운용)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(articleService.getUsers());
    }

    //카테고리 목록
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories(){
        return ResponseEntity.ok(articleService.getCategories());
    }


    //카테고리별 기사 목록 (읽음 여부 포함)
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @RequestParam RssCategory category,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(articleService.getArticles(category, userId));
    }

    // 읽음 처리
    @PostMapping("/articles/{articleId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable String articleId,
            @RequestParam Long userId) {
        articleService.markAsRead(articleId, userId);
        return ResponseEntity.ok().build();
    }


}
