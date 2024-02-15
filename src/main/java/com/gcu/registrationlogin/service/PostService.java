package com.gcu.registrationlogin.service;

import java.util.List;

import com.gcu.registrationlogin.entity.Post;

public interface PostService {

    List<Post> getAllPosts();
    void createPost(Post post);
    void deletePost(Long id);
    Post getById(Long id);
    void editPost(Post post);
}
