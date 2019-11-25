package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.presentation.util.constants.Views;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return Views.ERROR_404_VIEW;
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return Views.ERROR_GLOBAL_VIEW;
            }
        }

        return Views.ERROR_GLOBAL_VIEW;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
