package com.pymes.inventario.controller;

import com.pymes.inventario.model.User;
import com.pymes.inventario.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Muestra el formulario de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Muestra el formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Procesa el formulario de registro
    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user, Model model) {
        // Validación por nombre de usuario duplicado
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "El nombre de usuario ya existe.");
            return "register";
        }

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login?registered=true";
    }
}
