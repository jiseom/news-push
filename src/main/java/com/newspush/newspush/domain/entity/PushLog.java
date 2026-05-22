package com.newspush.newspush.domain.entity;

import com.newspush.newspush.domain.enums.PushType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "push_logs")
@Getter
@NoArgsConstructor
public class PushLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PushType pushType;

    @Column(nullable = false)
    private String articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String status;

    private String failReason;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }

    @Builder
    public PushLog(Long userId, String deviceId, PushType pushType, String articleId,
                   String title, String category, String status, String failReason) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.pushType = pushType;
        this.articleId = articleId;
        this.title = title;
        this.category = category;
        this.status = status;
        this.failReason = failReason;
    }

}
