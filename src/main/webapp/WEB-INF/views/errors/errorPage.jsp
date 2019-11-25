<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>

<html>
<head>
    <%@ include file="/WEB-INF/views/snippets/stylesheets.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/error.css"/>">
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="/WEB-INF/views/snippets/navbar.jsp" %>
<main role="main" class="container min-vh-100 mb-5">
    <div class="row min-vh-100 justify-content-md-center align-items-center">
        <div class=" text-center">
            <h1 class="default-status-error"><fmt:message key="error.default.status"/></h1>
            <c:if test="${not empty requestScope.message}">
                <p class="default-text-muted-error text-break">
                    <c:out value="${requestScope.message}"/>
                </p>
            </c:if>
            <p class="default-text-muted-error"><fmt:message key="error.default.message"/></p>
            <a class="btn btn-lg btn-primary mt-5" href="<c:url value="/app/"/>" role="button">
                <fmt:message key="error.backbtn"/>
            </a>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>
</body>
</html>
