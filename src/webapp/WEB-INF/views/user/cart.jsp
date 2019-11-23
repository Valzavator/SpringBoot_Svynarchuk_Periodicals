<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.gmail.maxsvynarchuk.util.type.PeriodicalStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/head.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>
<main role="main" class="container">
    <div class="d-flex justify-content-center align-items-center">
        <h1 class="display-3">
            <strong>
                <fmt:message key="cart"/>
            </strong>
        </h1>
    </div>
    <div class="progress mb-5">
        <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
    <c:choose>
        <c:when test="${empty sessionScope.shoppingCart.items}">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-4 text-info">
                    <span class="badge badge-info"><fmt:message key="cart.empty"/></span>
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${sessionScope.shoppingCart.isHasSuspendedPeriodical()}">
                <div class="alert alert-danger" role="alert">
                    <fmt:message key="error.some.periodical.invalid"/>
                </div>
            </c:if>
            <div class=" table-responsive">
                <table class="table table-striped table-hover text-center align-middle">
                    <thead>
                    <tr class="bg-primary">
                        <th scope="col" class="align-middle">№</th>
                        <th scope="col" class="align-middle"><fmt:message key="cart.name"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="periodical.status"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="cart.type"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="cart.frequency"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="cart.publisher"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="cart.subscription.plan"/></th>
                        <th scope="col" class="align-middle"><fmt:message key="cart.price"/></th>
                        <th scope="col" class="align-middle">
                            <form class="table-form" method="post" action="<c:url value="/app/cart/remove-all"/>">
                                <button type="submit" class="btn btn-danger btn-sm">
                                    <fmt:message key="cart.remove.all"/>
                                </button>
                            </form>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${sessionScope.shoppingCart.items}" varStatus="counter">
                        <tr>
                            <th scope="row" class="align-middle">${counter.count}</th>
                            <td class="align-middle overflow-text">
                                <c:out value="${item.periodical.name}"/>
                            </td>
                            <td class="align-middle">
                                <c:if test="${item.periodical.status eq PeriodicalStatus.ACTIVE}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.active"/>
                                </span>
                                </c:if>
                                <c:if test="${item.periodical.status eq PeriodicalStatus.SUSPENDED}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.suspended"/>
                                </span>
                                </c:if>
                            </td>
                            <td class="align-middle">
                                <c:out value="${item.periodical.periodicalType.name}"/>
                            </td>
                            <td class="align-middle">
                                <c:out value="${item.periodical.frequency.name}"/>
                            </td>
                            <td class="align-middle">
                                <c:out value="${item.periodical.publisher.name}"/>
                            </td>
                            <td class="align-middle">
                                <c:out value="${item.subscriptionPlan.name}"/> - <c:out
                                    value="${item.subscriptionPlan.rate}"/>
                            </td>
                            <td class="align-middle">
                                <c:out value="${item.periodical.price}"/>
                            </td>
                            <td class="align-middle">
                                <form class="table-form" method="post" action="<c:url value="/app/cart/remove"/>">
                                    <input name="cartItemId" value="${item.periodical.id}" type="hidden"/>
                                    <button type="submit" class="btn btn-link" style="color: red;">
                                        <i class="fa fa-times-circle-o fa-lg" aria-hidden="true"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0"
                     aria-valuemax="100"></div>
            </div>
            <div class="d-flex align-items-center">
                <div class="p-2">
                    <h1 class="display-4">
                        <strong>
                            <fmt:message key="cart.total.price"/>:
                            <c:out value="${sessionScope.shoppingCart.getTotalPrice()}"/> $
                        </strong>
                    </h1>
                </div>
                <div class="p-2 ml-auto">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-success btn-lg" data-toggle="modal" data-target="#payModal"
                            <c:if test="${sessionScope.shoppingCart.isHasSuspendedPeriodical()}">
                                disabled
                            </c:if>
                    >
                        <fmt:message key="cart.pay"/>
                        &nbsp;<i class="fa fa-chevron-circle-right fa-lg" aria-hidden="true"></i>
                    </button>
                </div>
                <!-- Modal -->
                <div class="modal fade" id="payModal" tabindex="-1" role="dialog"
                     aria-labelledby="payModalTitle" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle">
                                    <fmt:message key="credit.card.information"/>
                                </h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form class="table-form" method="post"
                                  action="<c:url value="/app/cart/subscription-payment"/>">

                                <div class="modal-body">

                                    <div class="form-group">
                                        <label for="name">
                                            <fmt:message key="credit.cart.number"/>
                                        </label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                            <span class="input-group-text" id="inputGroupPrepend0">
                                                <i class="fa fa-credit-card fa-lg" aria-hidden="true"></i>
                                            </span>
                                            </div>
                                            <input type="text" id="name"
                                                   name="creditCardNumber"
                                                   value="5168 **** **** 4905"
                                                   class="form-control form-control-lg"
                                                   minlength="16"
                                                   maxlength="19"
                                                   required>
                                        </div>
                                    </div>

                                    <div class="form-group form-row">

                                        <div class="col-auto mr-auto w-50">
                                            <label for="number">
                                                <fmt:message key="credit.cart.date"/>
                                            </label>
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                <span class="input-group-text" id="inputGroupPrepend1">
                                                    <i class="fa fa-credit-card fa-lg" aria-hidden="true"></i>
                                                </span>
                                                </div>
                                                <input type="text" id="number"
                                                       name="creditCardDate"
                                                       value="06/22"
                                                       class="form-control form-control-lg"
                                                       minlength="4"
                                                       maxlength="5"
                                                       required>
                                            </div>
                                        </div>

                                        <div class="col-auto mr-auto w-50">
                                            <label for="publicationDate">
                                                <fmt:message key="credit.cart.cvv"/>
                                            </label>
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                <span class="input-group-text" id="inputGroupPrepend5">
                                                    <i class="fa fa-credit-card fa-lg" aria-hidden="true"></i>
                                                </span>
                                                </div>
                                                <input type="text" id="publicationDate"
                                                       name="creditCardCVV"
                                                       value="***"
                                                       class="form-control form-control-lg"
                                                       minlength="3"
                                                       maxlength="3"
                                                       required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger btn-lg" data-dismiss="modal">
                                        <i class="fa fa-chevron-circle-left fa-lg" aria-hidden="true">&nbsp;</i>
                                        <fmt:message key="periodical.back"/>
                                    </button>

                                    <button type="submit" class="btn btn-success btn-lg"
                                            <c:if test="${sessionScope.shoppingCart.isHasSuspendedPeriodical()}">
                                                disabled
                                            </c:if>
                                    >
                                        <fmt:message key="cart.pay"/>
                                        &nbsp;<i class="fa fa-check fa-lg" aria-hidden="true"></i>
                                    </button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</main>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>