package com.kornas.mybookcase.repository;

import com.kornas.mybookcase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
}
