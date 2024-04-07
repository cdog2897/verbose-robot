package com.gcu.registrationlogin.controller;

import jakarta.validation.Valid;
import com.gcu.registrationlogin.dto.UserDto;
import com.gcu.registrationlogin.entity.Post;
import com.gcu.registrationlogin.entity.User;
import com.gcu.registrationlogin.service.PostService;
import com.gcu.registrationlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        logger.info("Entering getAllPosts method");
        List<Post> posts = postService.getAllPosts();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Retrieved authentication details: {}", authentication);
        model.addAttribute("emailAddress", authentication.getName());
        model.addAttribute("posts", posts);
        logger.info("Exiting getAllPosts method");
        return "posts";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Model model) {
        logger.info("Entering deletePost method with id: {}", id);
        postService.deletePost(id);
        logger.info("Post deleted successfully");
        logger.info("Exiting deletePost method");
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        logger.info("Entering editPage method with id: {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Retrieved authentication details: {}", authentication);
        model.addAttribute("email", authentication.getName());
        Post post = postService.getById(id);
        model.addAttribute("post", post);
        logger.info("Exiting editPage method");
        return "edit";
    }

    @PostMapping("/editSuccess/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute Post post) {
        logger.info("Entering editPost method with id: {}", id);
        logger.info("Editing post: {}", post);
        postService.editPost(post);
        logger.info("Post edited successfully");
        logger.info("Exiting editPost method");
        return "redirect:/posts";
    }

    @GetMapping("/create")
    public String createPost(Model model) {
        logger.info("Entering createPost method");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Retrieved authentication details: {}", authentication);
        model.addAttribute("post", new Post());
        User user = userService.findUserByEmail(authentication.getName());
        logger.info("Retrieved user details: {}", user);
        model.addAttribute("email", user.getEmail());
        logger.info("Exiting createPost method");
        return "create";
    }

    @PostMapping("/create")
    public String submitForm(Post post) {
        logger.info("Entering submitForm method");
        logger.info("Creating new post: {}", post);
        postService.createPost(post);
        logger.info("Post created successfully");
        logger.info("Exiting submitForm method");
        return "redirect:/posts";
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        logger.info("Entering home method");
        logger.info("Exiting home method");
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Entering showRegistrationForm method");
        // create model object to store form data
        UserDto user = new UserDto();
        logger.info("Creating new UserDto: {}", user);
        model.addAttribute("user", user);
        logger.info("Exiting showRegistrationForm method");
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        logger.info("Entering registration method");
        logger.info("UserDto received: {}", userDto);
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            logger.warn("User already exists with email: {}", userDto.getEmail());
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            logger.info("Exiting registration method with errors");
            return "/register";
        }

        userService.saveUser(userDto);
        logger.info("User registration successful for email: {}", userDto.getEmail());
        logger.info("Exiting registration method");
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        logger.info("Entering users method");
        List<UserDto> users = userService.findAllUsers();
        logger.info("Retrieved users: {}", users);
        model.addAttribute("users", users);
        logger.info("Exiting users method");
        return "users";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        logger.info("Entering login method");
        logger.info("Exiting login method");
        return "login";
    }

    @GetMapping("/")
    public String index() {
        logger.info("Entering index method");
        logger.info("Exiting index method");
        return "index";
    }
}
