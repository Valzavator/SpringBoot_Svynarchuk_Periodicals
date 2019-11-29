package com.gmail.maxsvynarchuk.service;

import com.gmail.maxsvynarchuk.persistence.dao.UserDao;
import com.gmail.maxsvynarchuk.persistence.entity.Role;
import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.util.PasswordManager;
import com.gmail.maxsvynarchuk.util.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Service responsible for processing user-related operations
 *
 * @author Maksym Svynarchuk
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService {
    private final Role defaultRole = RoleType.USER.getValue();
    private final PasswordManager passwordManager;
    private final UserDao userDao;

    @Transactional(readOnly = true)
    public Optional<User> signIn(String email, String password) {
        log.debug("Attempt to sign in");
        if (Objects.isNull(email) || Objects.isNull(password)) {
            return Optional.empty();
        }
        Optional<User> user = userDao.findOneByEmail(email);
        return user
                .filter(u -> passwordManager.checkSecurePassword(
                        password, u.getPassword()))
                .map(u -> {
                    u.setPassword(null);
                    return u;
                });
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        log.debug("Attempt to find user by id");
        return userDao.findOne(id).map(user -> {
            user.setPassword(null);
            return user;
        });
    }

    public boolean registerUser(User userToRegister) {
        log.debug("Attempt to register new user");
        if (Objects.isNull(userToRegister.getEmail()) ||
                Objects.isNull(userToRegister.getPassword())) {
            return false;
        }
        if (userToRegister.getRole() == null) {
            userToRegister.setRole(defaultRole);
        }
        boolean userIsPresent = userDao.existByEmail(userToRegister.getEmail());
        if (!userIsPresent) {
            String hash = passwordManager.hashPassword(
                    userToRegister.getPassword());
            userToRegister.setPassword(hash);
            userDao.insert(userToRegister);
            return true;
        }
        return false;
    }
}
