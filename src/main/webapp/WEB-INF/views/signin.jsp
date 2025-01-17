<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="errorAuthentication" type="java.lang.Boolean"--%>
<%--@elvariable id="signInDTO" type="com.gmail.maxsvynarchuk.presentation.util.dto.SignInDTO"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/snippets/header.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/snippets/stylesheets.jsp" %>
</head>
<body class="d-flex flex-column h-100">
<%@ include file="/WEB-INF/views/snippets/navbar.jsp" %>
<main role="main" class="container h-100">
    <div class="row h-100 justify-content-md-center align-items-center">
        <div class="card w-50 mx-auto">
            <div class="card-header h2 text-center">
                <fmt:message key="signin"/>
            </div>
            <div class="card-body mx-auto w-100">
                <form accept-charset="UTF-8" role="form" method="post">
                    <div class="form-group">
                        <label for="email"><fmt:message key="email"/></label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroupPrepend">
                            <i class="fa fa-envelope fa-lg" aria-hidden="true"></i>
                        </span>
                            </div>
                            <input type="email" id="email"
                                   name="email"
                                   value="<c:out value="${signInDTO.email}"/>"
                                   class="form-control form-control-lg
                                   <c:if test="${errors.emailError}">
                                            is-invalid
                                   </c:if>"
                                   aria-describedby="emailHelp"
                                   placeholder="<fmt:message key="email.placeholder"/>"
                                   maxlength="255"
                                   required>
                            <c:if test="${errors.emailError}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.email"/>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password"><fmt:message key="password"/></label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend2">
                                    <i class="fa fa-lock fa-2x" aria-hidden="true"></i>
                                </span>
                            </div>
                            <input type="password" id="password"
                                   name="password"
                                   class="form-control form-control-lg
                                   <c:if test="${errors.passwordError}">
                                            is-invalid
                                   </c:if>"
                                   placeholder="<fmt:message key="password.placeholder"/>"
                                   minlength="5"
                                   maxlength="255"
                                   required>
                            <c:if test="${errors.passwordError}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.password"/>
                                </div>
                            </c:if>
                        </div>

                    </div>
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary btn-lg mt-3"><fmt:message key="signin"/></button>
                    </div>
                </form>
            </div>

            <c:if test="${errorAuthentication}">
                <div class="card-footer text-muted">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <fmt:message key="error.authentication"/>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/views/snippets/footer.jsp" %>
</body>
</html>
