<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>

<html>
<head>
    <%@ include file="/WEB-INF/views/snippets/stylesheets.jsp" %>
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="/WEB-INF/views/snippets/navbar.jsp" %>
<main role="main" class="container min-vh-100 mb-5">
    <div class="row min-vh-100 justify-content-md-center align-items-center">
        <div class="card w-75 mx-auto">
            <div class="card-header h2 text-center">
                <fmt:message key="admin.management.create.periodical"/>
            </div>
            <div class="card-body mx-auto w-100">

                <form accept-charset="UTF-8" role="form" method="post">
                    <%@ include file="/WEB-INF/views/snippets/periodicalFormFields.jsp" %>

                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary btn-lg mt-3">
                            <fmt:message key="admin.management.create.periodical"/>
                        </button>
                    </div>

                </form>

            </div>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>
</body>
</html>