package com.newspush.newspush.repository;

import com.newspush.newspush.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
