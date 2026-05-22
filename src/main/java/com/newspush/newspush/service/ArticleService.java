package com.newspush.newspush.service;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.entity.UserArticleRead;
import com.newspush.newspush.domain.enums.RssCategory;
import com.newspush.newspush.dto.ArticleResponse;
import com.newspush.newspush.dto.UserResponse;
import com.newspush.newspush.repository.ArticleRepository;
import com.newspush.newspush.repository.UserArticleReadRepository;
import com.newspush.newspush.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserArticleReadRepository userArticleReadRepository;
    private final UserRepository userRepository;

    //유저 목록 (드롭다운용)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
    }

    public List<String> getCategories() {
        return Arrays.stream(RssCategory.values())
                .map(RssCategory::name)
                .collect(Collectors.toList());
    }

    public List<ArticleResponse> getArticles(RssCategory category, Long userId) {
        List<Article> articles = articleRepository.findByCategoryOrderByPubDateDesc(category);
        Set<String> readArticleIds = userArticleReadRepository.findArticleIdsByUserId(userId);

        return articles.stream()
                .map(article -> ArticleResponse.of(
                        article, readArticleIds.contains(article.getArticleId())))
                .collect(Collectors.toList());



    }

    @Transactional
    public void markAsRead(String articleId, Long userId) {
        if (userArticleReadRepository.existsByUserIdAndArticleId(userId, articleId)) {
            return;
        }
        userArticleReadRepository.save(
                UserArticleRead.builder()
                        .userId(userId)
                        .articleId(articleId)
                        .build());

    }
}
