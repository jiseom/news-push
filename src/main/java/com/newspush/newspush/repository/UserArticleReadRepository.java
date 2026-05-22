package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.UserArticleRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserArticleReadRepository extends JpaRepository<UserArticleRead, Long> {
    boolean existsByUserIdAndArticleId(Long userId, String articleId);

    @Query("SELECT u.articleId FROM UserArticleRead u WHERE u.userId = :userId")
    Set<String> findArticleIdsByUserId(@Param("userId") Long userId);

    void deleteByArticleIdIn(List<String> articleIds);


}
