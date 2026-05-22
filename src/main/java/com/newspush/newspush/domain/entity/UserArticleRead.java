package com.newspush.newspush.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_article_reads",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "article_id"})}
)
@Getter
@NoArgsConstructor
public class UserArticleRead {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "article_id", nullable = false)
    private String articleId;

    @Column (nullable = false)
    private LocalDateTime readAt;

    @PrePersist
    public void prePersist() {
        this.readAt = LocalDateTime.now();
    }

    @Builder
    public UserArticleRead(Long userId, String articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }





}
