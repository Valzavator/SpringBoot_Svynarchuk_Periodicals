package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.presentation.util.constants.Attributes;
import com.gmail.maxsvynarchuk.presentation.util.constants.PagesPaths;
import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import com.gmail.maxsvynarchuk.presentation.util.dto.UserDTO;
import com.gmail.maxsvynarchuk.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
@Log4j2
public class RegistrationController {
    private final UserService userService;

    private final ModelMapper mapper;

    @GetMapping("/signup")
    public String getSignUpPage(Model model) {
        log.debug("Attempt to get sign Up page");
        if (!model.containsAttribute(Attributes.USER_DTO)) {
            model.addAttribute(Attributes.USER_DTO, new UserDTO());
        }
        return Views.SIGN_UP_VIEW;
    }

    @PostMapping("/signup")
    public String signUp(@Valid UserDTO userDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.debug("Start of new user registration");

        if (!bindingResult.hasErrors()) {
            User user = mapper.map(userDTO, User.class);
            boolean isRegistered = userService.registerUser(user);
            if (isRegistered) {
                log.debug("User was successfully register");
                return ControllerUtil.redirectTo(PagesPaths.SIGN_IN_PATH);
            } else {
                log.debug("Such user already registered");
                redirectAttributes.addFlashAttribute(Attributes.ERROR_REGISTRATION, true);
            }
        } else {
            log.debug("Invalid registration parameters");
            Map<String, Boolean> errors = ControllerUtil.getErrors(bindingResult);
            redirectAttributes.addFlashAttribute(Attributes.ERRORS, errors);
            log.debug("User registration fail");
        }
        redirectAttributes.addFlashAttribute(Attributes.USER_DTO, userDTO);
        log.debug("User registration fail");
        return ControllerUtil.redirectTo(PagesPaths.SIGN_UP_PATH);
    }
}
