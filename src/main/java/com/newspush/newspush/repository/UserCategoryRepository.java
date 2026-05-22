package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.UserCategory;
import com.newspush.newspush.domain.enums.RssCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    @Query("SELECT uc FROM UserCategory uc JOIN FETCH uc.user WHERE uc.category = :category")
    List<UserCategory> findByCategoryWithUser(@Param("category") RssCategory category);
}