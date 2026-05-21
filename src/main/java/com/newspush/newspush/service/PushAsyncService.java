package com.newspush.newspush.service;

import com.newspush.newspush.domain.entity.Article;
import com.newspush.newspush.domain.entity.PushLog;
import com.newspush.newspush.domain.entity.User;
import com.newspush.newspush.domain.enums.PushType;
import com.newspush.newspush.repository.PushLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushAsyncService {
    private final PushNotificationService pushNotificationService;
    private final PushLogRepository pushLogRepository;
    private final DndPolicy dndPolicy;

    @Async("pushExecutor")
    public void send(User user, Article article) {
        //DND 체크
        if (dndPolicy.isBlocked(user.getDndTime())) {
            log.info("DND 로 발송 제외 userId={}, articleId={}",
                    user.getId(), article.getArticleId());
            return;
        }

        String result = "fail";

        try {
            // APNS / FCM 분기
            if (PushType.APNS == user.getPushType()) {
                result = pushNotificationService.sendAPNS(
                        user.getDeviceId(),
                        article.getArticleId(),
                        article.getTitle()
                );
            }else{
                result = pushNotificationService.sendFCM(
                        user.getDeviceId(),
                        article.getArticleId(),
                        article.getTitle()
                );
            }

        } catch (Exception e) {
            log.error("푸시 발송 중 예외 userId={}, articleId={}",
                    user.getId(), article.getArticleId(), e);
            result = "fail";
        }

        PushLog pushLog = PushLog.builder()
                .userId(user.getId())
                .deviceId(user.getDeviceId())
                .pushType(user.getPushType())
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .category(article.getCategory().name())
                .status(result)
                .build();

        pushLogRepository.save(pushLog);
        log.info("푸시 발송 완료 userId={}, articleId={}, status={}",
                user.getId(), article.getArticleId(), result);
    }
}
