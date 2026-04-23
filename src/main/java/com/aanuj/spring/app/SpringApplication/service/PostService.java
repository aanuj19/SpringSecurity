package com.aanuj.spring.app.SpringApplication.service;

import com.aanuj.spring.app.SpringApplication.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
