package com.newspush.newspush.dto;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.enums.RssCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleResponse {

    private String articleId;
    private String title;
    private RssCategory category;
    private String link;
    private String author;
    private String pubDate;
    private boolean read;


    public static ArticleResponse of(Article article, boolean read) {
        return ArticleResponse.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .category(article.getCategory())
                .link(article.getLink())
                .author(article.getAuthor())
                .pubDate(article.getPubDate() != null
                        ? article.getPubDate().toString() : null)
                .read(read)
                .build();
    }

}
