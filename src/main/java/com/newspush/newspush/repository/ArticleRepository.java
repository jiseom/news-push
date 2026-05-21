package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    long countAllBy();
    List<Article> findAllByOrderByPubDateAsc();

}
