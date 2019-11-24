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
    <c:if test="${empty requestScope.catalog}">
        <div class="d-flex justify-content-center align-items-center mb-5">
            <h1 class="display-4 text-info">
                <span class="badge badge-info"><fmt:message key="catalog.empty"/></span>
            </h1>
        </div>
    </c:if>
    <c:forEach var="periodical" items="${requestScope.catalog}">
        <div class="card border-dark mb-5" id="periodical-${periodical.id}">
            <div class="row no-gutters ">
                <div class="col-md-4 d-flex align-content-center flex-wrap">
                    <img src="<c:url value="/resources/images/logo.png"/>" class="card-img" alt="logo">
                </div>
                <div class="col-md-8 text-white">
                    <div class="card-header card-title text-center">
                        <a href="<c:url value="/app/periodical?periodicalId=${periodical.id}"/>">
                            <h3><c:out value="${periodical.name}"/></h3>
                        </a>
                    </div>
                    <div class="card-body bg-primary accordion" id="accordion-${periodical.id}">
                        <div class="card-text text-center" id="heading-${periodical.id}">
                            <button class="btn btn-link" type="button" data-toggle="collapse"
                                    data-target="#collapse-${periodical.id}"
                                    aria-expanded="false" aria-controls="collapse-${periodical.id}">
                                <fmt:message key="periodical.description"/>&nbsp;
                                <i class="fa fa-caret-square-o-down fa-lg" aria-hidden="true"></i>
                            </button>
                        </div>
                        <div id="collapse-${periodical.id}" class="collapse" aria-labelledby="heading-${periodical.id}"
                             data-parent="#accordion-${periodical.id}">
                            <p class="card-text"><c:out value="${periodical.description}"/></p>
                        </div>
                    </div>

                    <ul class="bg-primary text-white list-group list-group-flush">
                        <li class="list-group-item bg-primary">
                            <fmt:message key="periodical.type"/>: <c:out value="${periodical.periodicalType.name}"/>
                        </li>
                        <li class="list-group-item bg-primary">
                            <fmt:message key="periodical.frequency"/>:
                            <c:out value="${periodical.frequency.name}"/> - <c:out value="${periodical.frequency.meaning}"/>
                        </li>
                        <li class="list-group-item bg-primary">
                            <fmt:message key="periodical.publisher"/>: <c:out value="${periodical.publisher.name}"/>
                        </li>
                        <li class="list-group-item bg-primary">
                            <fmt:message key="periodical.price"/>: <c:out value="${periodical.price} $"/></li>
                    </ul>
                    <c:if test="${!sessionScope.user.isAdmin() and periodical.status ne PeriodicalStatus.SUSPENDED}">
                        <div class="card-footer d-flex justify-content-sm-center justify-content-lg-end ">
                            <form accept-charset="UTF-8" role="form" method="post"
                                  action="<c:url value="/app/cart/add"/>">
                                <!-- Button trigger modal -->
                                <div class="input-group ">
                                    <input type="hidden" class="form-control" name="periodicalId"
                                           value="${periodical.id}">
                                    <button type="button" class="btn btn-info btn-lg" data-toggle="modal"
                                            data-target="#modal-${periodical.id}">
                                        <i class="fa fa-chevron-circle-right fa-lg" aria-hidden="true">&nbsp;</i>
                                        <fmt:message key="periodical.subscribe"/>
                                    </button>
                                </div>
                                <!-- Modal -->
                                <div class="modal fade" id="modal-${periodical.id}" tabindex="-1" role="dialog"
                                     aria-labelledby="modal-${periodical.id}" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">

                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">
                                                    <fmt:message key="periodical.choose.subscription.plan"/>
                                                </h5>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <select class="custom-select" name="subscriptionPlanId" required>
                                                        <c:forEach var="subscriptionPlan"
                                                                   items="${requestScope.subscriptionPlans}">
                                                            <option value="${subscriptionPlan.id}">
                                                                <c:out value="${subscriptionPlan.name}"/>
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-danger" data-dismiss="modal">
                                                    <i class="fa fa-chevron-circle-left fa-lg"
                                                       aria-hidden="true">&nbsp;</i>
                                                    <fmt:message key="periodical.back"/>
                                                </button>
                                                <button type="submit" class="btn btn-info">
                                                    <i class="fa fa-shopping-cart fa-lg" aria-hidden="true">&nbsp;</i>
                                                    <fmt:message key="periodical.add.to.cart"/>
                                                </button>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
    <jsp:include page="/WEB-INF/views/snippets/pagination.jsp"/>
</main>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>

<!-- Modal -->
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content bg-danger">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="error.add.to.cart"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <c:choose>
                    <c:when test="${requestScope.errorIsAlreadySubscribed}">
                        <fmt:message key="error.is.already.subscribed"/>
                    </c:when>
                    <c:when test="${requestScope.errorIsAlreadyInCart}">
                        <fmt:message key="error.is.already.in.cart"/>
                    </c:when>
                    <c:when test="${requestScope.errorPeriodicalInvalid}">
                        <fmt:message key="error.periodical.invalid"/>
                    </c:when>
                </c:choose>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" class="btn btn-info" data-dismiss="modal">
                    <fmt:message key="modal.ok"/>
                </button>
            </div>
        </div>
    </div>
</div>

<c:if test="${requestScope.errorIsAlreadySubscribed or
 requestScope.errorIsAlreadyInCart or
  requestScope.errorPeriodicalInvalid}">
    <script type="text/javascript" defer>
        $(document).ready(function () {
            $("#errorModal").modal("show");
        });
    </script>
</c:if>
</body>
</html>
