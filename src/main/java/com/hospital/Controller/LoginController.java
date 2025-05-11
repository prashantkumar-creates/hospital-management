package com.hospital.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    // Default login page mapping (handled by Spring Security)
    @GetMapping("/login")
    public String login() {
        return "login"; // returns the login.html template
    }

    // Spring Security handles login automatically, no need for manual POST mapping here
    // If login fails, Spring Security will redirect to /login?error=true
    @GetMapping("/login?error=true")
    public String loginError(Model model) {
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}
