package com.newspush.newspush.scheduler;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.enums.RssCategory;
import com.newspush.newspush.repository.ArticleRepository;
import com.newspush.newspush.repository.UserArticleReadRepository;
import com.newspush.newspush.service.PushProcessor;
import com.newspush.newspush.service.RssCollector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
@Slf4j
public class RssScheduler {

    private final RssCollector rssCollector;
    private final ArticleRepository articleRepository;
    private final UserArticleReadRepository userArticleReadRepository;
    private final PushProcessor pushProcessor;
    private final @Qualifier("rssExecutor") Executor rssExecutor;

    @Scheduled(fixedDelay = 600_000)
    public void collect() {
        log.info("RSS 수집 시작");

        // 1. 5개 카테고리 병렬 수집
        List<CompletableFuture<List<Article>>> futures = Arrays.stream(RssCategory.values())
                .map(category -> CompletableFuture.supplyAsync(
                        () -> rssCollector.fetch(category), rssExecutor))
                .collect(toList());

        // 2. 전체 완료 후 합치기
        List<Article> articles = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(toList());

        log.info("RSS 수집 완료. 총 {}건", articles.size());

        // 3. 신규 기사만 저장
        List<String> existingIds = articleRepository.findAllArticleIds(); // 기존 ID 목록
        List<Article> newArticles = articles.stream()
                .filter(a -> !existingIds.contains(a.getArticleId()))
                .collect(toList());

        articleRepository.saveAll(newArticles);
        log.info("신규 기사 저장 완료. {}건", newArticles.size());

        // 기사는 총 1000건 제한
        deleteOldArticlesIfExceed();

        // 4. @Async로 푸시 발송 넘기기
        pushProcessor.process(newArticles);
    }

    private static final int MAX_ARTICLE_COUNT = 1000;

    private void deleteOldArticlesIfExceed() {
        long count = articleRepository.countAllBy();

        if (count > MAX_ARTICLE_COUNT) {
            long deleteCount = count - MAX_ARTICLE_COUNT;
            List<Article> oldArticles = articleRepository
                    .findAllByOrderByPubDateAsc();

            List<Article> toDelete = oldArticles.stream()
                    .limit(deleteCount)
                    .collect(toList());

            //읽음 여부 저장 삭제
            deleteRelatedReadHistory(toDelete);

            articleRepository.deleteAll(toDelete);
            log.info("오래된 기사 {}건 삭제", deleteCount);
        }
    }

    private void deleteRelatedReadHistory(List<Article> articles) {
        List<String> ids = articles.stream()
                .map(Article::getArticleId)
                .collect(toList());
        userArticleReadRepository.deleteByArticleIdIn(ids);
    }

}
