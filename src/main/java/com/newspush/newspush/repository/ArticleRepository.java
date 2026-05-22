package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.enums.RssCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    long countAllBy();

    List<Article> findAllByOrderByPubDateAsc();

    List<Article> findByCategoryOrderByPubDateDesc(RssCategory category);

    @Query("SELECT a.articleId FROM Article a")
    List<String> findAllArticleIds();
}
