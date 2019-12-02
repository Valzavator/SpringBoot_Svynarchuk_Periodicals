<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>

<html>
<head>
    <%@ include file="/WEB-INF/views/snippets/stylesheets.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/error.css"/>">
</head>
<body class="d-flex flex-column h-100">
<%@ include file="/WEB-INF/views/snippets/navbar.jsp" %>
<main role="main" class="container h-100">
    <div class="row h-100 justify-content-md-center align-items-center">
        <div class="text-center">
            <h1 class="status-error"><fmt:message key="error.400.status"/></h1>
            <p class="text-muted-error"><fmt:message key="error.400.message"/></p>
            <a class="btn btn-lg btn-primary" href="<c:url value="/app/"/>" role="button">
                <fmt:message key="error.backbtn"/>
            </a>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>
</body>
</html>
