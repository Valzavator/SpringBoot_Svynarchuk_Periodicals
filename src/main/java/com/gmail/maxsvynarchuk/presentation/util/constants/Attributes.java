package com.gmail.maxsvynarchuk.presentation.util.constants;

import com.gmail.maxsvynarchuk.util.ResourceManager;

/**
 * Constants which relates to view part
 */
public final class Attributes {
    public static final String USER = ResourceManager.ATTRIBUTE.getProperty("user");
    public static final String SHOPPING_CART = ResourceManager.ATTRIBUTE.getProperty("shopping.cart");

    public static final String CATALOG = ResourceManager.ATTRIBUTE.getProperty("catalog");
    public static final String PERIODICAL = ResourceManager.ATTRIBUTE.getProperty("periodical");
    public static final String ISSUES = ResourceManager.ATTRIBUTE.getProperty("issues");
    public static final String SUBSCRIPTIONS = ResourceManager.ATTRIBUTE.getProperty("subscriptions");
    public static final String ACTIVE_SUBSCRIPTIONS = ResourceManager.ATTRIBUTE.getProperty("subscriptions.active");
    public static final String EXPIRED_SUBSCRIPTIONS = ResourceManager.ATTRIBUTE.getProperty("subscriptions.expired");
    public static final String PAYMENTS = ResourceManager.ATTRIBUTE.getProperty("payments");

    public static final String SUBSCRIPTION_PLANS = ResourceManager.ATTRIBUTE.getProperty("subscription.plans");
    public static final String PERIODICAL_TYPES = ResourceManager.ATTRIBUTE.getProperty("periodical.types");
    public static final String FREQUENCIES = ResourceManager.ATTRIBUTE.getProperty("frequencies");
    public static final String PUBLISHERS = ResourceManager.ATTRIBUTE.getProperty("publishers");

    public static final String USER_DTO = ResourceManager.ATTRIBUTE.getProperty("user.dto");
    public static final String SIGN_IN_DTO = ResourceManager.ATTRIBUTE.getProperty("sign.in.dto");
    public static final String PERIODICAL_DTO = ResourceManager.ATTRIBUTE.getProperty("periodical.dto");
    public static final String ISSUE_DTO = ResourceManager.ATTRIBUTE.getProperty("issue.dto");
    public static final String PAYMENT_DTO = ResourceManager.ATTRIBUTE.getProperty("payment.dto");

    public static final String ERRORS = ResourceManager.ATTRIBUTE.getProperty("errors");
    public static final String ERROR_AUTHENTICATION = ResourceManager.ATTRIBUTE.getProperty("error.authentication");
    public static final String ERROR_REGISTRATION = ResourceManager.ATTRIBUTE.getProperty("error.registration");
    public static final String ERROR_IS_ALREADY_SUBSCRIBED = ResourceManager.ATTRIBUTE.getProperty("error.is.already.subscribed");
    public static final String ERROR_PERIODICAL_INVALID = ResourceManager.ATTRIBUTE.getProperty("error.periodical.invalid");
    public static final String ERROR_IS_ALREADY_IN_CART = ResourceManager.ATTRIBUTE.getProperty("error.is.already.in.cart");
    public static final String ERROR_ISSUE_EXIST = ResourceManager.ATTRIBUTE.getProperty("error.issue.exist");

    public static final String PAGE_DTO = ResourceManager.ATTRIBUTE.getProperty("dto.page");

    public static final String PAGINATION_PAGE = ResourceManager.PARAMETER.getProperty("pagination.page");
    public static final String PAGINATION_PILL = ResourceManager.PARAMETER.getProperty("pagination.pill");
    public static final String PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE = ResourceManager.PARAMETER.getProperty("pagination.active.subscriptions.page");
    public static final String PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE = ResourceManager.PARAMETER.getProperty("pagination.expired.subscriptions.page");

    private Attributes() {
    }
}
