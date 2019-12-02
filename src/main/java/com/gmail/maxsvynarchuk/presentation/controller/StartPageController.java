package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@Slf4j
public class StartPageController {

    @GetMapping("/")
    public String startPage() {
        log.debug("Attempt to get home page");
        return Views.HOME_VIEW;
    }
}
