package com.newspush.newspush.service;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.entity.UserCategory;
import com.newspush.newspush.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncPushProcessor implements PushProcessor {
    private final UserCategoryRepository userCategoryRepository;
    private final PushAsyncService pushAsyncService;

    @Override
    public void process(List<Article> articles) {
        if (articles.isEmpty()) {
            log.info("신규 기사 없음. 발송 스킵");
            return;
        }

        for (Article article : articles) {
            List<UserCategory> matchedUsers =
                    userCategoryRepository.findByCategoryWithUser(article.getCategory());

            log.info("기사={}, 매칭 유저={}명", article.getArticleId(),matchedUsers.size());

            // 유저 단위 @Async 발송
            for (UserCategory userCategory : matchedUsers) {
                pushAsyncService.send(userCategory.getUser(), article);
            }
        }
    }

}
