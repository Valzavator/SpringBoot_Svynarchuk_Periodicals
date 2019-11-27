package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.util.Util;
import com.gmail.maxsvynarchuk.presentation.util.constants.Attributes;
import com.gmail.maxsvynarchuk.presentation.util.constants.PagesPaths;
import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import com.gmail.maxsvynarchuk.presentation.util.validator.ValidatorManager;
import com.gmail.maxsvynarchuk.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
@Log4j2
public class AuthorizationController {
    private final UserService userService;

    @GetMapping("/signin")
    public String getSignInPage() {
        log.info("Try to get sign In page");
        return Views.SIGN_IN_VIEW;
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        log.info("Try to get sign Up page");
        return Views.SIGN_UP_VIEW;
    }

    @GetMapping("/signout")
    public String signOut(HttpSession session) {
        log.info("Sign out");
        session.invalidate();
        return Util.redirectTo(PagesPaths.HOME_PATH);
    }

    @PostMapping("/signin")
    public String postSignIn(User userDTO,
                             HttpSession session,
                             Model model) {
        log.debug("Start of sign in process");

        Map<String, Boolean> errors = ValidatorManager
                .validateSignInParameters(userDTO);

        if (errors.isEmpty()) {
            log.debug("Try to sign in");
            Optional<User> userOpt =
                    userService.signIn(userDTO.getEmail(), userDTO.getPassword());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setPassword(null);
                session.setAttribute(Attributes.USER, user);
                log.debug("User successfully signed in");
                if (user.isAdmin()) {
                    return Util.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
                } else {
                    return Util.redirectTo(PagesPaths.CATALOG_PATH);
                }
            } else {
                log.debug("Email and password don't matches");
                errors.put(Attributes.ERROR_AUTHENTICATION, true);
            }
        } else {
            log.debug("Invalid authentication parameters");
        }

        model.addAttribute(Attributes.ERROR_IS_ALREADY_IN_CART, true);
        model.addAttribute(Attributes.ERRORS, errors);
        model.addAttribute(Attributes.USER, userDTO);

        log.debug("User fail sign in");
        return Views.SIGN_IN_VIEW;
    }

    @PostMapping("/signup")
    public String postSignUp(User userDTO,
                             Model model) {
        log.debug("Start of new user registration");

        Map<String, Boolean> errors = ValidatorManager
                .validateSignUpParameters(userDTO);

        if (errors.isEmpty()) {
            boolean isRegistered = userService.registerUser(userDTO);
            if (isRegistered) {
                log.debug("User was successfully register");
                return Util.redirectTo(PagesPaths.SIGN_IN_PATH);
            } else {
                log.debug("Such user already registered");
                errors.put(Attributes.ERROR_REGISTRATION, true);
            }
        } else {
            log.debug("Invalid registration parameters");
        }

        model.addAttribute(Attributes.ERRORS, errors);
        model.addAttribute(Attributes.USER_DTO, userDTO);

        log.debug("User registration fail");
        return Views.SIGN_UP_VIEW;
    }
}
