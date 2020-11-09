package dev.sd.project.service;

import dev.sd.project.model.User;
import dev.sd.project.repository.ArticleRepository;
import dev.sd.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService underTest;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private ArticleRepository mockArticleRepository;
    @BeforeEach

    void setUp() {
        underTest = new UserService(mockUserRepository,mockPasswordEncoder,mockArticleRepository);
    }

    @Test
    void createUserWithNonExistUserDetail() {
        String username = "admin";
        String password = "1234";
        String email = "test@web.com";

        when(mockPasswordEncoder.encode(password)).thenReturn("4321");

        User searchUser = new User();
        searchUser.setUsername(username);
        searchUser.setEmail(email);
        when(mockUserRepository.exists(Example.of(searchUser))).thenReturn(false);

        searchUser.setPassword("4321");
        when(mockUserRepository.save(searchUser)).thenReturn(searchUser);

        assertDoesNotThrow( () -> underTest.createUser(username, email, password));
        verify(mockUserRepository, times(1)).save(searchUser);
    }

    @Test
    void createUserWithExistUserDetail() {
        String username = "admin";
        String password = "1234";
        String email = "test@web.com";

        when(mockPasswordEncoder.encode(password)).thenReturn("4321");

        User searchUser = new User();
        searchUser.setUsername(username);
        searchUser.setEmail(email);
        when(mockUserRepository.exists(Example.of(searchUser))).thenReturn(true);

        searchUser.setPassword("4321");
        when(mockUserRepository.save(searchUser)).thenReturn(searchUser);

        assertThrows ( Exception.class,() -> underTest.createUser(username, email, password));
        verify(mockUserRepository, times(1)).save(searchUser);
    }

    @Test
    void checkLoginWithCorrectInfo() {
        String username = "admin";
        String password = "1234";

        User returnUser = new User(username,"test@web.com",password,new HashSet<>());
       when(mockUserRepository.findByUsername(username)).thenReturn(returnUser);
       when(mockPasswordEncoder.matches(password,password)).thenReturn(true);
       assertTrue(underTest.checkLogin(username,password)); //check ->( true )??
    }

    @Test
    void checkLoginWithWrongPassword() {
        String username = "admin";
        String password = "12345";

        User returnUser = new User(username, "test@test.com", "1234",new HashSet<>());
        when(mockUserRepository.findByUsername(username)).thenReturn(returnUser);
        when(mockPasswordEncoder.matches(password, "1234")).thenReturn(false);

        assertFalse(underTest.checkLogin(username, password));
    }

    @Test
    void checkLoginWithWrongUsername() {
        String username = "admin1";
        String password = "12345";

        when(mockUserRepository.findByUsername(username)).thenReturn(null);

        assertFalse(underTest.checkLogin(username, password));
    }
}