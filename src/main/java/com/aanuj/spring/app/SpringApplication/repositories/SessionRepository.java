package com.aanuj.spring.app.SpringApplication.repositories;

import com.aanuj.spring.app.SpringApplication.entities.Session;
import com.aanuj.spring.app.SpringApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
