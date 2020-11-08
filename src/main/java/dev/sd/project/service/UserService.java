package dev.sd.project.service;

import dev.sd.project.model.User;
import dev.sd.project.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class UserService { //create new user & check password -> control model and manage data from repository
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String email, String password) throws Exception {
        String encrypPassword = passwordEncoder.encode(password);
        User searchUser = new User();
        searchUser.setUsername(username);
        searchUser.setEmail(email);

        if (userRepository.exists(Example.of(searchUser))) {
            log.log(Level.INFO,"Can not create this user account, User existed");
            throw new Exception("User existed");
        }

        User user = new User(username, email, encrypPassword);
        userRepository.save(user);
        log.log(Level.INFO,"Created user account , User id = "+user.getUserId());
        return user;
    }
    public boolean checkLogin(String username, String password){
        User foundUser = userRepository.findByUsername(username);
        if(foundUser == null){
            return false;
        }
        return passwordEncoder.matches(password, foundUser.getPassword());
    }
}
