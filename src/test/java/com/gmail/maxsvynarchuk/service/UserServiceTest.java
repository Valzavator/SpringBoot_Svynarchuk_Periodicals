package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.UserDao;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.util.PasswordManager;
import com.gmail.maxsvynarchuk.util.type.RoleType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private PasswordManager passwordManager;

    private final String email = "email@gmail.com";
    private final String password = "password";
    private final String hashPassword = "hashPassword";

    @Test
    void findUserByIdWithExistingUserTest() {
        Long userId = 1L;
        Optional<User> expected = Optional.of(User.builder()
                .id(userId)
                .build());
        when(userDao.findOne(userId)).thenReturn(expected);

        Optional<User> actual = userService.findUserById(userId);

        assertEquals(expected, actual);
        verify(userDao, times(1)).findOne(userId);
    }

    @Test
    void findUserByIdWithNotExistingUserTest() {
        Long userId = 1L;
        when(userDao.findOne(userId)).thenReturn(Optional.empty());

        Optional<User> periodicalOpt = userService.findUserById(userId);

        assertFalse(periodicalOpt.isPresent());
        verify(userDao, times(1)).findOne(userId);
    }

    @Test
    void signInWithCorrectEmailAndPasswordTest() {
        Optional<User> expected = Optional.of(User.builder()
                .email(email)
                .password(hashPassword)
                .build());
        when(userDao.findOneByEmail(email)).thenReturn(expected);
        when(passwordManager.checkSecurePassword(password, hashPassword)).thenReturn(true);

        Optional<User> actual = userService.signIn(email, password);

        assertEquals(expected, actual);
        verify(passwordManager, times(1))
                .checkSecurePassword(password, hashPassword);
        verify(userDao, times(1)).findOneByEmail(email);
    }

    @Test
    void signInWithWrongPasswordTest() {
        Optional<User> findByEmailUser = Optional.of(User.builder()
                .email(email)
                .password(hashPassword)
                .build());
        when(userDao.findOneByEmail(email)).thenReturn(findByEmailUser);

        Optional<User> actual = userService.signIn(email, "another password");

        assertFalse(actual.isPresent());
        verify(userDao, times(1)).findOneByEmail(email);
    }

    @Test
    void signInWithWrongEmailTest() {
        when(userDao.findOneByEmail(email)).thenReturn(Optional.empty());

        Optional<User> actual = userService.signIn(email, password);

        assertFalse(actual.isPresent());
        verify(userDao, times(1)).findOneByEmail(email);
    }

    @Test
    void signInWithNullEmailTest() {
        Optional<User> actual = userService.signIn(null, password);

        assertFalse(actual.isPresent());
        verify(userDao, never()).findOneByEmail(email);
    }

    @Test
    void signInWithNullPasswordTest() {
        Optional<User> actual = userService.signIn(email, null);

        assertFalse(actual.isPresent());
        verify(userDao, never()).findOneByEmail(email);
    }

    @Test
    void registerUserWithValidParametersTest() {
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        when(userDao.existByEmail(email)).thenReturn(false);
        when(passwordManager.hashPassword(password)).thenReturn(hashPassword);

        assertTrue(userService.registerUser(user));
        assertTrue(RoleType.USER.isEquals(user));
        assertEquals(hashPassword, user.getPassword());
        verify(userDao, times(1)).insert(any(User.class));
        verify(passwordManager, times(1)).hashPassword(password);
    }

    @Test
    void registerUserWithSameEmailTest() {
        User user = User.builder()
                .email(email)
                .password(password)
                .build();
        when(userDao.existByEmail(email)).thenReturn(true);

        assertFalse(userService.registerUser(user));
        verify(userDao, never()).insert(any(User.class));
    }

    @Test
    void registerUserWithNullEmailTest() {
        User user = User.builder()
                .email(null)
                .password(password)
                .build();

        assertFalse(userService.registerUser(user));
        verify(userDao, never()).insert(any(User.class));
    }

    @Test
    void registerUserWithNullPasswordTest() {
        User user = User.builder()
                .email(email)
                .password(null)
                .build();

        assertFalse(userService.registerUser(user));
        verify(userDao, never()).insert(any(User.class));
    }
}