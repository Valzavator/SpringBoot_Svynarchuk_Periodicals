<%--@elvariable id="activeSubscriptions" type="com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO"--%>
<%--@elvariable id="expiredSubscriptions" type="com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO"--%>
<%--@elvariable id="active" type="java.lang.String"--%>
<%--@elvariable id="expired" type="java.lang.String"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.gmail.maxsvynarchuk.util.type.PeriodicalStatus" %>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>

<c:choose>
    <c:when test="${empty active && empty expired  && not empty activeSubscriptions.elements}">
        <c:set var="active" scope="page" value="${true}"/>
    </c:when>
    <c:when test="${empty active && empty expired && not empty expiredSubscriptions.elements}">
        <c:set var="expired" scope="page" value="${true}"/>
    </c:when>
</c:choose>

<html>
<head>
    <%@ include file="/WEB-INF/views/snippets/stylesheets.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="/WEB-INF/views/snippets/navbar.jsp" %>
<main role="main" class="container">
    <div class="d-flex justify-content-center align-items-center">
        <h1 class="display-3">
            <strong>
                <fmt:message key="subscriptions"/>
            </strong>
        </h1>
    </div>
    <div class="progress mb-5">
        <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
    <c:choose>
        <c:when test="${empty activeSubscriptions.elements && empty expiredSubscriptions.elements }">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-4 text-info">
                    <span class="badge badge-info"><fmt:message key="subscriptions.empty"/></span>
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="nav nav-pills mb-5" id="pills-tab" role="tablist">
                <li class="nav-item w-50 text-center">
                    <a class="nav-link
                                <c:if test="${not empty active}">
                                    active
                                </c:if>
                                <c:if test="${empty activeSubscriptions.elements}">
                                    disabled
                                </c:if>"
                       id="pills-home-tab"
                       data-toggle="pill" href="#pills-home" role="tab"
                       aria-controls="pills-home" aria-selected="true">
                        <fmt:message key="subscriptions.active"/>
                    </a>
                </li>
                <li class="nav-item w-50 text-center">
                    <a class="nav-link
                              <c:if test="${not empty expired}">
                                    active
                                </c:if>
                                <c:if test="${empty expiredSubscriptions.elements}">
                                    disabled
                                </c:if>"
                       id="pills-profile-tab"
                       data-toggle="pill" href="#pills-profile" role="tab"
                       aria-controls="pills-profile" aria-selected="false">
                        <fmt:message key="subscriptions.expired"/>
                    </a>
                </li>
            </ul>
            <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade show <c:if test="${not empty active}">active</c:if>" id="pills-home"
                     role="tabpanel" aria-labelledby="pills-home-tab">
                    <div class=" table-responsive">
                        <table class="table table-striped table-hover text-center align-middle">
                            <thead>
                            <tr class="bg-primary">
                                <th scope="col" class="align-middle">№</th>
                                <th scope="col" class="align-middle"><fmt:message key="cart.name"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="periodical.status"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="cart.type"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="cart.frequency"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.start.date"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.end.date"/></th>
                                <th scope="col" class="align-middle"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="subscription" items="${activeSubscriptions.elements}"
                                       varStatus="counter">
                                <tr>
                                    <th scope="row" class="align-middle">${counter.count}</th>
                                    <td class="align-middle overflow-text">
                                        <c:out value="${subscription.periodical.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:if test="${subscription.periodical.status eq PeriodicalStatus.ACTIVE}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.active"/>
                                </span>
                                        </c:if>
                                        <c:if test="${subscription.periodical.status eq PeriodicalStatus.SUSPENDED}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.suspended"/>
                                </span>
                                        </c:if>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.periodicalType.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.frequency.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.startDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.endDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <a href="<c:url value="/app/periodical?periodicalId=${subscription.periodical.id}"/>">
                                            <i class="fa fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                                            <fmt:message key="subscription.see"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${activeSubscriptions.numberOfPages gt 1}">
                            <nav aria-label="Page navigation" class="mb-5">
                                <ul class="pagination pagination-lg justify-content-center">
                                    <li class="page-item <c:if test="${activeSubscriptions.currentPage eq 0}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=active&activePage=${activeSubscriptions.currentPage - 1}&expiredPage=${expiredSubscriptions.currentPage}"/>"
                                           aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="0" end="${activeSubscriptions.numberOfPages - 1}" var="counter">
                                        <li class="page-item <c:if test="${activeSubscriptions.currentPage eq counter}">disabled</c:if>">
                                            <a class="page-link"
                                               href="<c:url value="?pill=active&activePage=${counter}&expiredPage=${expiredSubscriptions.currentPage}"/>">
                                                    ${counter + 1}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item <c:if test="${activeSubscriptions.currentPage eq activeSubscriptions.numberOfPages - 1}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=active&activePage=${activeSubscriptions.currentPage + 1}&expiredPage=${expiredSubscriptions.currentPage}"/>"
                                           aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>

                    </div>
                </div>
                <div class="tab-pane fade show <c:if test="${not empty expired}">active</c:if>" id="pills-profile"
                     role="tabpanel" aria-labelledby="pills-profile-tab">
                    <div class=" table-responsive">
                        <table class="table table-striped table-hover text-center align-middle">
                            <thead>
                            <tr class="bg-primary">
                                <th scope="col" class="align-middle">№</th>
                                <th scope="col" class="align-middle"><fmt:message key="cart.name"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="periodical.status"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="cart.type"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="cart.frequency"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.start.date"/></th>
                                <th scope="col" class="align-middle"><fmt:message key="subscription.end.date"/></th>
                                <th scope="col" class="align-middle"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="subscription" items="${expiredSubscriptions.elements}"
                                       varStatus="counter">
                                <tr>
                                    <th scope="row" class="align-middle">${counter.count}</th>
                                    <td class="align-middle overflow-text">
                                        <c:out value="${subscription.periodical.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:if test="${subscription.periodical.status eq PeriodicalStatus.ACTIVE}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.active"/>
                                </span>
                                        </c:if>
                                        <c:if test="${subscription.periodical.status eq PeriodicalStatus.SUSPENDED}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.suspended"/>
                                </span>
                                        </c:if>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.periodicalType.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.periodical.frequency.name}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.startDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${subscription.endDate}"/>
                                    </td>
                                    <td class="align-middle">
                                        <a href="<c:url value="/app/periodical?periodicalId=${subscription.periodical.id}"/>">
                                            <i class="fa fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                                            <fmt:message key="subscription.see"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${expiredSubscriptions.numberOfPages gt 1}">
                            <nav aria-label="Page navigation" class="mb-5">
                                <ul class="pagination pagination-lg justify-content-center">
                                    <li class="page-item <c:if test="${expiredSubscriptions.currentPage eq 0}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=expired&activePage=${activeSubscriptions.currentPage}&expiredPage=${expiredSubscriptions.currentPage - 1}"/>"
                                           aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="0" end="${expiredSubscriptions.numberOfPages - 1}" var="counter">
                                        <li class="page-item <c:if test="${expiredSubscriptions.currentPage eq counter}">disabled</c:if>">
                                            <a class="page-link"
                                               href="<c:url value="?pill=expired&activePage=${activeSubscriptions.currentPage}&expiredPage=${counter}"/>">
                                                    ${counter + 1}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item <c:if test="${expiredSubscriptions.currentPage eq expiredSubscriptions.numberOfPages - 1}">disabled</c:if>">
                                        <a class="page-link"
                                           href="<c:url value="?pill=expired&activePage=${activeSubscriptions.currentPage}&expiredPage=${expiredSubscriptions.currentPage + 1}"/>"
                                           aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>

                    </div>
                </div>
            </div>

        </c:otherwise>
    </c:choose>

</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>
</body>
</html>