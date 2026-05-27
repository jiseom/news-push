package com.newspush.newspush.service;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.entity.PushLog;
import com.newspush.newspush.domain.entity.UserCategory;
import com.newspush.newspush.repository.PushLogRepository;
import com.newspush.newspush.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncPushProcessor implements PushProcessor {
    private final UserCategoryRepository userCategoryRepository;
    private final PushAsyncService pushAsyncService;
    private final PushLogRepository pushLogRepository;

    @Override
    public void process(List<Article> articles) {
        if (articles.isEmpty()) {
            log.info("신규 기사 없음. 발송 스킵");
            return;
        }

        List<PushLog> logs = new ArrayList<>();

        for (Article article : articles) {
            List<UserCategory> matchedUsers =
                    userCategoryRepository.findByCategoryWithUser(article.getCategory());

            log.info("기사={}, 매칭 유저={}명", article.getArticleId(), matchedUsers.size());

            for (UserCategory userCategory : matchedUsers) {
                PushLog log = pushAsyncService.send(userCategory.getUser(), article);
                if (log != null) logs.add(log);
            }
        }

        pushLogRepository.saveAll(logs);
        log.info("푸시 로그 저장 완료. {}건", logs.size());
    }

}
