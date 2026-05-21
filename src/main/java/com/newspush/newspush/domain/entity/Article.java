package com.newspush.newspush.domain.entity;

import com.newspush.newspush.domain.enums.RssCategory;
import jakarta.persistence.*;
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

    private String author;

    private LocalDateTime pubDate;

    private LocalDateTime createdAt;

}
