package com.newspush.newspush.domain.entity;

import com.newspush.newspush.domain.enums.PushType;
import jakarta.persistence.*;
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

}
