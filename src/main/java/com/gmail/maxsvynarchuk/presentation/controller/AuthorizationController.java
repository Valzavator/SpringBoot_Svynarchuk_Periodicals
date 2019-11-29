package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.presentation.util.constants.Attributes;
import com.gmail.maxsvynarchuk.presentation.util.constants.PagesPaths;
import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import com.gmail.maxsvynarchuk.presentation.util.dto.SignInDTO;
import com.gmail.maxsvynarchuk.presentation.util.dto.UserDTO;
import com.gmail.maxsvynarchuk.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
@Log4j2
public class AuthorizationController {
    private final UserService userService;

    @GetMapping("/signin")
    public String getSignInPage(Model model) {
        log.debug("Attempt to get sign In page");
        if (!model.containsAttribute(Attributes.USER_DTO)) {
            model.addAttribute(Attributes.USER_DTO, new UserDTO());
        }
        return Views.SIGN_IN_VIEW;
    }

    @GetMapping("/signout")
    public String signOut(HttpSession session) {
        log.debug("Attempt to Sign out");
        session.invalidate();
        return ControllerUtil.redirectTo(PagesPaths.HOME_PATH);
    }

    @PostMapping("/signin")
    public String signIn(@Valid SignInDTO dto,
                         BindingResult bindingResult,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        log.debug("Start of sign in process");

        if (!bindingResult.hasErrors()) {
            log.debug("Try to sign in");
            Optional<User> userOpt =
                    userService.signIn(dto.getEmail(), dto.getPassword());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute(Attributes.USER, user);
                log.debug("User successfully signed in");
                if (user.isAdmin()) {
                    return ControllerUtil.redirectTo(PagesPaths.ADMIN_CATALOG_PATH);
                } else {
                    return ControllerUtil.redirectTo(PagesPaths.CATALOG_PATH);
                }
            } else {
                log.debug("Email and password don't matches");
                redirectAttributes.addFlashAttribute(Attributes.ERROR_AUTHENTICATION, true);
            }
        } else {
            log.debug("Invalid authentication parameters");
            Map<String, Boolean> errors = ControllerUtil.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute(Attributes.ERRORS, errors);
        }

        redirectAttributes.addFlashAttribute(Attributes.SIGN_IN_DTO, dto);
        log.debug("User fail sign in");
        return ControllerUtil.redirectTo(PagesPaths.SIGN_IN_PATH);
    }
}
