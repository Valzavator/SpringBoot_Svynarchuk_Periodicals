<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.gmail.maxsvynarchuk.util.type.PeriodicalStatus" %>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>

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
                <fmt:message key="admin.management.catalog"/>
            </strong>
        </h1>
    </div>
    <div class="progress mb-5">
        <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
    <c:choose>
        <c:when test="${empty requestScope.catalog}">
            <div class="d-flex justify-content-center align-items-center mb-5">
                <h1 class="display-4 ">
                    <span class="badge badge-info"><fmt:message key="admin.management.catalog.empty"/></span>
                </h1>
            </div>
        </c:when>
        <c:otherwise>
            <div class=" table-responsive">
                <table class="table table-striped table-hover text-center align-middle">
                    <thead>
                    <tr class="bg-primary">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="periodical.name"/></th>
                        <th scope="col"><fmt:message key="periodical.status"/></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="periodical" items="${requestScope.catalog}" varStatus="counter">
                        <tr>
                            <th scope="row" class="align-middle">${counter.count}</th>
                            <td class="align-middle overflow-text"><c:out value="${periodical.name}"/></td>
                            <td class="align-middle">
                            <c:if test="${periodical.status eq PeriodicalStatus.ACTIVE}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.active"/>
                                </span>
                            </c:if>
                            <c:if test="${periodical.status eq PeriodicalStatus.SUSPENDED}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.suspended"/>
                                </span>
                            </c:if>
                            </td>
                            <td class="align-middle">
                                <a href="<c:url value="/app/periodical?periodicalId=${periodical.id}"/>">
                                    <i class="fa fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                                    <fmt:message key="admin.management.catalog.see"/>
                                </a>
                            </td>
                            <td class="align-middle">
                                <a href="<c:url value="/app/admin/catalog/issue-create?periodicalId=${periodical.id}"/>"
                                    <c:if test="${periodical.status eq PeriodicalStatus.SUSPENDED}">
                                        class="btn-link disabled"
                                    </c:if>
                                >
                                    <i class="fa fa-plus fa-lg" aria-hidden="true">&nbsp;</i>
                                    <fmt:message key="admin.management.create.add.issue"/>
                                </a>
                            </td>
                            <td class="align-middle">
                                <a href="<c:url value="/app/admin/catalog/periodical-edit?periodicalId=${periodical.id}"/>"
                                    <c:if test="${periodical.status eq PeriodicalStatus.SUSPENDED}">
                                        class="btn-link disabled"
                                    </c:if>
                                >
                                    <i class="fa fa-pencil fa-lg" aria-hidden="true">&nbsp;</i>
                                    <fmt:message key="admin.management.catalog.edit"/>
                                </a>
                            </td>
                            <td class="align-middle">
                                <form method="post" class="table-form"
                                      action="<c:url value="/app/admin/catalog/change-status"/>">
                                    <input type="hidden" name="periodicalId" value="${periodical.id}">
                                    <c:choose>
                                        <c:when test="${periodical.status eq PeriodicalStatus.SUSPENDED}">
                                        <input type="hidden" name="periodicalStatus" value="active">
                                        <button type="submit"  class="btn btn-link">
                                            <i class="fa fa-check-circle-o fa-lg" aria-hidden="true">&nbsp;</i>
                                            <fmt:message key="admin.management.catalog.activate"/>
                                        </button>
                                        </c:when>
                                        <c:otherwise>
                                        <input type="hidden" name="periodicalStatus" value="suspended">
                                        <button type="submit"  class="btn btn-link text-warning">
                                                <i class="fa fa-ban fa-lg" aria-hidden="true">&nbsp;</i>
                                                <fmt:message key="admin.management.catalog.suspend"/>
                                        </button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@ include file="/WEB-INF/views/snippets/pagination.jsp" %>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>

</body>
</html>