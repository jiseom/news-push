package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.UserCategory;
import com.newspush.newspush.domain.enums.RssCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findByCategory(RssCategory category);
}
