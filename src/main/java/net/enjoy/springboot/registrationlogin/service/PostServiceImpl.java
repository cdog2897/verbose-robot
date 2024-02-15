package net.enjoy.springboot.registrationlogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.enjoy.springboot.registrationlogin.entity.Post;
import net.enjoy.springboot.registrationlogin.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.getReferenceById(id);
    }

    @Override
    public void editPost(Post post) {
        postRepository.save(post);
    }
    

    

}
