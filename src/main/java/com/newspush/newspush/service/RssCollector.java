package com.newspush.newspush.service;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.enums.RssCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RssCollector {

    public List<Article> fetch(RssCategory category) {
        ArrayList<Article> articles = new ArrayList<>();

        try{
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(category.getUrl())));

            for (SyndEntry entry : feed.getEntries()) {
                String link = entry.getLink();
                //articleId 추출
                String articleId = link.substring(link.lastIndexOf("/") + 1);

                Article article = Article.builder()
                        .articleId(articleId)
                        .title(entry.getTitle())
                        .link(link)
                        .category(category)
                        .author(getAuthor(entry))
                        .pubDate(convertToLocalDateTime(entry.getPublishedDate()))
                        .build();

                articles.add(article);
            }

        }catch(Exception e){
            log.error("RSS 수집 실패 category={}", category.name(), e);

        }
        return articles;
    }


    private String getAuthor(SyndEntry entry) {
        return entry.getContributors().isEmpty()
                ? entry.getAuthor()
                : entry.getContributors().get(0).getName();
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        if(date == null ) return LocalDateTime.now();
        return date.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }


}
