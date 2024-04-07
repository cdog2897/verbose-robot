package com.gcu.registrationlogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.registrationlogin.entity.Post;
import com.gcu.registrationlogin.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        logger.info("Fetching all posts");
        return postRepository.findAll();
    }

    @Override
    public void createPost(Post post) {
        logger.info("Creating post: {}", post);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        logger.info("Deleting post with id: {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public Post getById(Long id) {
        logger.info("Fetching post by id: {}", id);
        return postRepository.getReferenceById(id);
    }

    @Override
    public void editPost(Post post) {
        logger.info("Editing post: {}", post);
        postRepository.save(post);
    }
}
