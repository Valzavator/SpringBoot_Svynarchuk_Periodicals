<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.gmail.maxsvynarchuk.presentation.util.constants.Views" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myLib" uri="/WEB-INF/tag/requestedViewTag" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<c:set var="homeView" scope="page" value="${Views.HOME_VIEW}"/>
<c:set var="profileView" scope="page" value="${Views.PROFILE_VIEW}"/>
<c:set var="signInView" scope="page" value="${Views.SIGN_IN_VIEW}"/>
<c:set var="signUpView" scope="page" value="${Views.SIGN_UP_VIEW}"/>
<c:set var="catalogView" scope="page" value="${Views.CATALOG_VIEW}"/>
<c:set var="cartView" scope="page" value="${Views.CART_VIEW}"/>
<c:set var="subscriptionsView" scope="page" value="${Views.SUBSCRIPTIONS_VIEW}"/>
<c:set var="adminCatalogView" scope="page" value="${Views.ADMIN_CATALOG_VIEW}"/>
<c:set var="adminCreatePeriodicalView" scope="page" value="${Views.CREATE_PERIODICAL_VIEW}"/>
<c:set var="adminPaymentsView" scope="page" value="${Views.PAYMENTS_VIEW}"/>
<c:set var="currView" scope="page">
    <myLib:viewUri/>
</c:set>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
    <a class="navbar-brand active" href="<c:url value="/app/"/>">Periodicals</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01"
            aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li
                    <c:if test="${catalogView.equals(currView)}">
                        class="active"
                    </c:if>
            >
                <a class="nav-link" href="<c:url value="/app/catalog"/>">
                    <i class="fa fa-home fa-list-alt fa-lg" aria-hidden="true">&nbsp;</i> <fmt:message key="catalog"/>
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="langDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fa fa-language fa-lg" aria-hidden="true">&nbsp;</i>
                    ${sessionScope.locale.getLanguage().toUpperCase()}
                </a>
                <div class="dropdown-menu" aria-labelledby="langDropdownMenuLink">
                    <c:forEach items="${applicationScope.supportedLocales}" var="lang">
                        <a class="dropdown-item" href="?lang=${lang}">${lang.toUpperCase()}</a>
                    </c:forEach>
                </div>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <c:if test="${sessionScope.user eq null}">
                <li
                        <c:if test="${signUpView.equals(currView)}">
                            class="active"
                        </c:if>
                >
                    <a class="nav-link" href="<c:url value="/app/signup"/>">
                        <i class="fa fa-user-plus fa-lg" aria-hidden="true">&nbsp;</i> <fmt:message key="signup"/>
                    </a>
                </li>
                <li
                        <c:if test="${signInView.equals(currView)}">
                            class="active"
                        </c:if>
                >
                    <a class="nav-link" href="<c:url value="/app/signin"/>">
                        <i class="fa fa-sign-in fa-lg" aria-hidden="true">&nbsp;</i> <fmt:message key="signin"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <c:if test="${!sessionScope.user.isAdmin()}">
                    <li
                            <c:if test="${cartView.equals(currView)}">
                                class="active"
                            </c:if>
                    >
                        <a class="nav-link" href="<c:url value="/app/cart"/>">
                            <i class="fa fa-shopping-cart fa-lg" aria-hidden="true">&nbsp;</i>
                            <fmt:message key="cart"/>&nbsp;
                            <span class="badge badge-light">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.shoppingCart}">
                                        ${sessionScope.shoppingCart.items.size()}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </a>
                    </li>
                    <li
                            <c:if test="${subscriptionsView.equals(currView)}">
                                class="active"
                            </c:if>
                    >
                        <a class="nav-link" href="<c:url value="/app/subscriptions"/>">
                            <i class="fa fa-home fa-list fa-lg" aria-hidden="true">&nbsp;</i>
                            <fmt:message key="user.subscriptions"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.isAdmin()}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="adminDropdownMenuLink" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-cogs fa-lg" aria-hidden="true">&nbsp;</i>
                            <fmt:message key="admin.management"/>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="adminDropdownMenuLink">
                            <a class="dropdown-item
                            <c:if test="${adminCatalogView.equals(currView)}">
                                active
                            </c:if>"
                               href="<c:url value="/app/admin/catalog"/>">
                                <i class="fa fa-list fa-lg" aria-hidden="true">&nbsp;</i>
                                <fmt:message key="admin.management.catalog"/>
                            </a>
                            <a class="dropdown-item
                            <c:if test="${adminCreatePeriodicalView.equals(currView)}">
                                active
                            </c:if>"
                               href="<c:url value="/app/admin/catalog/periodical-create"/>">
                                <i class="fa fa-plus-square fa-lg" aria-hidden="true">&nbsp;</i>
                                <fmt:message key="admin.management.create.periodical"/>
                            </a>
                        </div>
                    </li>
                    <li
                            <c:if test="${adminPaymentsView.equals(currView)}">
                                class="active"
                            </c:if>
                    >
                        <a class="nav-link" href="<c:url value="/app/admin/payments"/>">
                            <i class="fa fa-money fa-lg" aria-hidden="true">&nbsp;</i>
                            <fmt:message key="admin.payments"/>
                        </a>
                    </li>
                </c:if>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="profileDropdownMenuLink" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-user-circle-o fa-lg" aria-hidden="true">&nbsp;</i>
                        <c:out value="${sessionScope.user.firstName}"/>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdownMenuLink">
                        <a class="dropdown-item
                            <c:if test="${profileView.equals(currView)}">
                                    active
                            </c:if>"
                           href="<c:url value="/app/profile"/>"><fmt:message key="profile"/>
                        </a>
                        <a class="dropdown-item" href="<c:url value="/app/signout"/>"><fmt:message key="signout"/></a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>