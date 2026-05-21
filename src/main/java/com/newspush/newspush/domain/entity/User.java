package com.newspush.newspush.domain.entity;

import com.newspush.newspush.domain.enums.PushType;
import com.newspush.newspush.domain.enums.RssCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PushType pushType;

    private String dndTime;


    @Builder
    public User(String name, String deviceId, PushType pushType, String dndTime) {
        this.name = name;
        this.deviceId = deviceId;
        this.pushType = pushType;
        this.dndTime = dndTime;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategory> categories = new ArrayList<>();

    public void addCategory(RssCategory category) {
        categories.add(new UserCategory(this, category));
    }
}
