package dev.sd.project.controllers;

import dev.sd.project.model.Article;
import dev.sd.project.model.SecurityUserDetail;
import dev.sd.project.model.User;
import dev.sd.project.repository.UserRepository;
import dev.sd.project.service.ArticleService;
import dev.sd.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/me")
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;
    private UserService userService;
    private ArticleService articleService;

    @GetMapping("/settingEmail")
    public ModelAndView userSettingEmail(Optional<String> currentPasswdIncorrect,Optional<String> passwdMisMatch,
                                    Optional<String> emailError, Optional<String> success){
        ModelAndView view = new ModelAndView ("userSettingEmailForm");
        if (currentPasswdIncorrect.isPresent()) {
            view.addObject("err", "Current Password is incorrect");
        /*}else if (passwdMisMatch.isPresent()) {
            view.addObject("err", "Both New Password doesn't Match");*/
        }
        else if (emailError.isPresent()) {
            view.addObject("err", "Email has been used ");
        }else if (success.isPresent()){
            view.addObject("msg","Success!!!");
        }
        return view;
    }
    @GetMapping("/settingPassword")
    public ModelAndView userSettingPassword(Optional<String> currentPasswdIncorrect,Optional<String> passwdMisMatch,
                                    Optional<String> emailError, Optional<String> success){
        ModelAndView view = new ModelAndView ("userSettingPasswordForm");
        if (currentPasswdIncorrect.isPresent()) {
            view.addObject("err", "Current Password is incorrect");
        }else if (passwdMisMatch.isPresent()) {
            view.addObject("err", "Both New Password doesn't Match");
        }else if (success.isPresent()){
            view.addObject("msg","Success!!!");
        }
        return view;
    }

    @PostMapping("/updateEmail")
    public RedirectView updateEmailPost(String email,String password){
        User user = userRepository.findByUserId(userService.getCurrentUserId());
        if (userRepository.findByEmail(email)!= null){
            return new RedirectView("/me?emailError=true");
        }else {
            if (userService.checkLogin(user,password)){
                user.setEmail(email);
                userRepository.save(user);
                return new RedirectView("/me?success=true");
            }else{
                return new RedirectView("/me?currentPasswdIncorrect=true");
            }
        }

    }

    @PostMapping("/updatePassword")
    public RedirectView updatePasswordPost(String currentPassword, String newPassword, String confirmNewPassword){
        User user = userRepository.findByUserId(userService.getCurrentUserId());
        if (!newPassword.equals(confirmNewPassword)){
            return new RedirectView("/me?passwdMisMatch=true");
        }else {
            if (userService.checkLogin(user,currentPassword)){
                userService.changePassword(user, newPassword);
                return new RedirectView("/me?success=true");
            }else{
                return new RedirectView("/me?currentPasswdIncorrect=true");
            }
        }

    }

    @GetMapping("/myarticle")
    public ModelAndView articleOfUser(@AuthenticationPrincipal SecurityUserDetail securityUserDetail){
        ModelAndView view = new ModelAndView("myArticle");

        List<Article> articles = articleService.getArticleFromUser(securityUserDetail.getUser());
        view.addObject("articles", articles);

        return view;
    }
}
