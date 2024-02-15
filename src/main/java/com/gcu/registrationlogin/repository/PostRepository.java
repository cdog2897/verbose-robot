package com.gcu.registrationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gcu.registrationlogin.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
