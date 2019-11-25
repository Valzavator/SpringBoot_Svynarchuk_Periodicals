package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.User;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/app")
@SessionAttributes(types = User.class)
@AllArgsConstructor
@Log4j2
public class AuthorizationController {
    private final UserService userService;

    @GetMapping("/signin")
    public String getSignIn() {
        log.info("Try to get signIn page");
        return Views.SIGN_IN_VIEW;
    }

    @GetMapping("/signup")
    public String getSignUp() {
        log.info("Try to get signIn page");
        return Views.SIGN_UP_VIEW;
    }


    @GetMapping("/signout")
    public String signOut(HttpSession session) {
        log.info("Sign out");
        session.invalidate();
        return Views.HOME_VIEW;
    }

//    @PostMapping("/signIn")
//    public String postSignIn(@RequestParam("email") String email,
//                             @RequestParam("password") String password,
//                             Model model) {
//        log.debug("Start of sign in process");
//        User userDTO = User.builder()
//                .email(email)
//                .password(password)
//                .build();
//
//        Map<String, Boolean> errors = ValidatorManager
//                .validateSignInParameters(userDTO);
//
//        if (errors.isEmpty()) {
//            log.debug("Try to sign in");
//            Optional<User> userOpt =
//                    userService.signIn(userDTO.getEmail(), userDTO.getPassword());
//            if (userOpt.isPresent()) {
//                User user = userOpt.get();
//                user.setPassword(null);
//                model.addAttribute(Attributes.USER, user);
//                log.debug("User successfully signed in");
//                if (user.isAdmin()) {
//                    return Views.ADMIN_CATALOG_VIEW;
//                } else {
//                    return Views.CATALOG_VIEW;
//                }
//            } else {
//                log.debug("Email and password don't matches");
//                errors.put(Attributes.ERROR_AUTHENTICATION, true);
//            }
//        } else {
//            log.debug("Invalid authentication parameters");
//        }
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject(AttributeNames.SIGN_IN_ERROR, e.getMessage());
//        modelAndView.setViewName("signIn");
//        return modelAndView;
//
//        model.addAttribute(Attributes.ERRORS, errors);
//        model.addAttribute(Attributes.USER, userDTO);
//        log.debug("User fail sign in");
//        return Views.SIGN_IN_VIEW;
//    }

}
