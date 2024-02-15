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
import java.util.List;


@Controller
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("emailAddress", authentication.getName());
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Model model) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("email", authentication.getName());
        Post post = postService.getById(id);
        model.addAttribute("post", post);
        return "edit";
    }

    @PostMapping("/editSuccess/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute Post post) {
        postService.editPost(post);
        return "redirect:/posts";
    }
    
    

    @GetMapping("/create")
    public String createPost(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("post", new Post());
        User user = userService.findUserByEmail(authentication.getName());

        model.addAttribute("email", user.getEmail());
        return "create";
    }

    @PostMapping("/create")
    public String submitForm(Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }
    

    // handler method to handle home page request
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
}