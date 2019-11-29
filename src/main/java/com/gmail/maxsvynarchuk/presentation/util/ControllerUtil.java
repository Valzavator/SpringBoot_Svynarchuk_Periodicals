package com.gmail.maxsvynarchuk.presentation.util;

import com.gmail.maxsvynarchuk.presentation.util.constants.PagesPaths;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtil {
    /**
     * Add next page to redirect
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param pageToRedirect page to redirect
     * @throws IOException IOException
     */
    public static void redirectTo(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String pageToRedirect) throws IOException {
        response.sendRedirect(
                request.getContextPath() + pageToRedirect);
    }

    /**
     * Add next page to redirect
     *
     * @param pageToRedirect page to redirect
     */
    public static String redirectTo(String pageToRedirect) {
        return "redirect:" + pageToRedirect;
    }

    /**
     * @return referer path without servlet path at the beginning
     */
    public static String getReferer(HttpServletRequest request) {
        String referer = PagesPaths.HOME_PATH;
        String header = request.getHeader("referer");
        if (header != null && !header.isEmpty()) {
            try {
                URI uri = new URI(header);
                String path = uri.getPath();
                String query = uri.getQuery();
                referer = Objects.isNull(query) ? path : path + "?" + query;
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return referer.replaceFirst(request.getContextPath(), "");
    }


    public static Map<String, Boolean> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, Boolean>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                fieldError -> true
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}
