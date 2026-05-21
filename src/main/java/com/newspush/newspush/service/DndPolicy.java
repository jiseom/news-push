package com.newspush.newspush.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
public class DndPolicy {

    public boolean isBlocked(String dndTime) {
        // dndTime 없으면 항상 발송
        if (dndTime == null || dndTime.equals("-")) {
            return false;
        }

        try {
            String[] parts = dndTime.split("-");
            LocalTime start = LocalTime.parse(parts[0].trim());
            LocalTime end = LocalTime.parse(parts[1].trim());
            LocalTime now = LocalTime.now();

            // 자정 넘기는 케이스 (23:00-11:00)
            if (start.isAfter(end)) {
                return now.isAfter(start) || now.isBefore(end);
            }

            // 일반 케이스 (05:00-22:00)
            return now.isAfter(start) && now.isBefore(end);

        } catch (Exception e) {
            log.warn("DND 시간 파싱 실패: {}", dndTime);
            return false;
        }
    }
}