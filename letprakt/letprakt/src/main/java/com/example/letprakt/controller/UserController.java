package com.example.letprakt.controller;

import com.example.letprakt.model.User;
import com.example.letprakt.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user,
                               BindingResult result,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/editProfile")
    public String showEditProfileForm(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "editProfile";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editProfile")
    public String updateUserProfile(@ModelAttribute("user") @Valid User user, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            return "editProfile";
        }

        String currentUsername = principal.getName();
        User currentUser = userRepository.findByUsername(currentUsername);
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (!user.getUsername().equals(currentUser.getUsername())) {
            Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
            if (existingUser.isPresent()) {
                model.addAttribute("error", "Username already exists!");
                return "editProfile";
            }
        }

        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
        return "redirect:/home";
    }

}

