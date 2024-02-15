package net.enjoy.springboot.registrationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.enjoy.springboot.registrationlogin.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
