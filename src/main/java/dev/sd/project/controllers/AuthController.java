package dev.sd.project.controllers;

import dev.sd.project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.apache.zookeeper.Op;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Log
@Controller
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView loginPage(Optional<String> error, Optional<String> logout, Optional<String> register) {
        ModelAndView view = new ModelAndView("login");
        if (error.isPresent()) {
            view.addObject("msg", "Username or Password is incorrect");
        } else if (logout.isPresent()) {
            view.addObject("msg", "You have been logged out");
        } else if (register.isPresent()) {
            view.addObject("msg", "You have been registered! Please login again");
        }

        return view;
    }

    @GetMapping("/logout")
    public RedirectView logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new RedirectView("/login?logout=true");
    }

    @PostMapping("/register")
    public RedirectView registerPost(String username, String email, String password, String password2) {
        if (!password.contentEquals(password2)) {
            return new RedirectView("/register?error=password");
        }

        try {
            userService.createUser(username, email, password);
            return new RedirectView("/login?register=true");
        } catch (Exception e) {
            return new RedirectView("/register?error=exists");
        }
    }

    @GetMapping("/register")
    public ModelAndView registerForm(Optional<String> error) {
        ModelAndView view = new ModelAndView("registerForm");

        if (error.isPresent()) {
            switch (error.get()) {
                case "password":
                    view.addObject("msg", "Password mismatch");
                    break;
                case "exists":
                    view.addObject("msg", "Can not create this user account, User existed");
                    break;
            }
        }

        return view;
    }
}
