package com.newspush.newspush.domain.entity;

import com.newspush.newspush.domain.enums.RssCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_categories")
@Getter
@NoArgsConstructor
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RssCategory category;


    public UserCategory(User user, RssCategory category) {
        this.user = user;
        this.category = category;
    }
}

