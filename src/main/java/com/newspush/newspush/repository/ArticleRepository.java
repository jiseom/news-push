package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByArticleId(String articleId);
}
