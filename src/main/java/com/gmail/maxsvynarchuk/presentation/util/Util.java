package com.gmail.maxsvynarchuk.presentation.util;

import com.gmail.maxsvynarchuk.persistence.entity.User;
import com.gmail.maxsvynarchuk.presentation.util.constants.Attributes;
import com.gmail.maxsvynarchuk.presentation.util.constants.PagesPaths;
//import com.gmail.maxsvynarchuk.service.entity.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Util {
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

//    /**
//     * Get shopping cart for user
//     *
//     * @param session HttpSession
//     * @return existing shopping cart or new one
//     */
//    public static ShoppingCart getShoppingCart(HttpSession session) {
//        ShoppingCart shoppingCart =
//                (ShoppingCart) session.getAttribute(Attributes.SHOPPING_CART);
//        if (Objects.isNull(shoppingCart)) {
//            shoppingCart = new ShoppingCart();
//            session.setAttribute(Attributes.SHOPPING_CART, shoppingCart);
//        }
//        return shoppingCart;
//    }

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

    /**
     * Add parameter to exist URI
     */
    public static String addParameterToURI(String uri,
                                           String parameterName,
                                           String parameterValue) {
        Objects.requireNonNull(parameterName);
        Objects.requireNonNull(parameterValue);

        try {
            String newParameter = parameterName + "=" + parameterValue;
            URI oldUri = new URI(uri);
            String newQuery = oldUri.getQuery();

            if (Objects.isNull(newQuery)) {
                newQuery = newParameter;
            } else if (newQuery.contains(parameterName)) {
                newQuery = newQuery.replaceFirst(parameterName + "=" + "[^&]+", newParameter);
            } else {
                newQuery = newQuery + "&" + newParameter;
            }

            URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                    oldUri.getPath(), newQuery, oldUri.getFragment());

            return newUri.getPath() + "?" + newUri.getQuery();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String addParameterToURI(String uri,
                                           String parameterName,
                                           int parameterValue) {
        return addParameterToURI(uri, parameterName, String.valueOf(parameterValue));
    }

    /**
     * Remove parameter from exist URI
     */
    public static String removeParameterFromURI(String uri, String parameterName) {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(parameterName);

        return uri.replaceFirst("\\??&?" + parameterName + "=((.+&)|[^&]+)", "");
    }

    /**
     * Checking the error parameter in the request.
     * If present, set it as a request attribute.
     */
    public static void checkErrorParameter(HttpServletRequest request,
                                           String requestAttribute) {
        String error = request.getParameter(requestAttribute);
        if (Objects.nonNull(error) && !error.isEmpty()) {
            request.setAttribute(error, true);
        }
    }
}
