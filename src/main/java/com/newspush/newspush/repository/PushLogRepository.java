package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.PushLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushLogRepository extends JpaRepository<PushLog,Long> {
}
