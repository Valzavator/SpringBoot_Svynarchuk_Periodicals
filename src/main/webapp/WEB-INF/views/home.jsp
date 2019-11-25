<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/snippets/stylesheets.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="/WEB-INF/views/snippets/navbar.jsp" %>
<main role="main" class="container">
    <div class="text-center">
        <img src="<c:url value="/resources/images/logo.png"/>" alt="Logo"/>
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="display-4 text-break"><fmt:message key="title"/></h1>
                <hr class="my-4">
                <p class="h4 text-justify"><fmt:message key="site.description"/></p>
            </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>
</body>
</html>