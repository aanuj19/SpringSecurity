package com.aanuj.spring.app.SpringApplication.repositories;

import com.aanuj.spring.app.SpringApplication.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}