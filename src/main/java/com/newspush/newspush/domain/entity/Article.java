package com.newspush.newspush.domain.entity;

import com.newspush.newspush.domain.enums.RssCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @Column(unique = true, nullable = false)
    private String articleId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RssCategory category;

    @Column(nullable = false)
    private String link;

    private String author;

    private LocalDateTime pubDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Article(String articleId, String title, String link, RssCategory category,
                   String author, LocalDateTime pubDate, LocalDateTime createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.link = link;
        this.category = category;
        this.author = author;
        this.pubDate = pubDate;
    }


}
