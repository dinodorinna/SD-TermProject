package dev.sd.project.service;


import dev.sd.project.model.Article;
import dev.sd.project.model.SecurityUserDetail;
import dev.sd.project.model.User;
import dev.sd.project.repository.ArticleRepository;
import dev.sd.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.logging.Level;

@Service
@Log
@AllArgsConstructor
public class UserService { //create new user & check password -> control model and manage data from repository
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArticleRepository articleRepository;

    public User createUser(String username, String email, String password) throws Exception {
        String encryptPassword = passwordEncoder.encode(password);

        if (userRepository.findByUsername(username) != null
            || userRepository.findByEmail(email) != null) {
            log.log(Level.INFO,"Can not create this user account, User existed");
            throw new Exception("User existed");
        }

        User user = new User(username, email, encryptPassword, new HashSet<>());
        userRepository.save(user);
        log.log(Level.INFO,"Created user account , User id = "+user.getUserId());
        return user;
    }

    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getClass() != AnonymousAuthenticationToken.class;
    }

    public String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!isLoggedIn()) {
            return null;
        }

        return ((SecurityUserDetail )auth.getPrincipal()).getUser().getUserId();
    }

    public void addArticleFavorite(String articleId, User user) throws Exception {
        Article article = articleRepository.findByArticleId(articleId);
        if (article == null){
            log.log(Level.INFO,"Article Not Found");
            throw new Exception("Article Not Found");
        }
        article.setFavoriteCount(article.getFavoriteCount()+1);
        user.getFavArticleList().add(article);
        article.setScore(article.getScore()+1);
        articleRepository.save(article);
        userRepository.save(user);

    }
    public boolean checkLogin(User user, String password) {

        return passwordEncoder.matches(password, user.getPassword());
    }

    public void changePassword(User user, String password) {
        String encryptPassword = passwordEncoder.encode(password);
        user.setPassword(encryptPassword);

        userRepository.save(user);
    }
}
