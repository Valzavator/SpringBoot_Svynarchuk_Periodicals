<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="userDTO" type="com.gmail.maxsvynarchuk.presentation.util.dto.UserDTO"--%>
<%--@elvariable id="errorRegistration" type="java.lang.Boolean"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.gmail.maxsvynarchuk.util.type.Gender" %>
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
                <fmt:message key="signup"/>
            </div>
            <div class="card-body mx-auto w-100">
                <form accept-charset="UTF-8" role="form" method="post">

                    <div class="form-group">
                        <label for="email"><fmt:message key="email"/></label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend0">
                                    <i class="fa fa-envelope fa-lg" aria-hidden="true"></i>
                                </span>
                            </div>
                            <input type="email"
                                   id="email"
                                   name="email"
                                   value="<c:out value="${userDTO.email}"/>"
                                   class="form-control form-control-lg
                                   <c:if test="${errors.emailError}">
                                            is-invalid
                                   </c:if>"
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

                    <div class="form-group form-row justify-content-center">
                        <div class="col-auto mr-auto w-50">
                            <label for="firstName"><fmt:message key="firstname"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupPrepend">
                                        <i class="fa fa-user fa-2x" aria-hidden="true"></i>
                                    </span>
                                </div>
                                <input type="text" id="firstName"
                                       name="firstName"
                                       value="<c:out value="${userDTO.firstName}"/>"
                                       class="form-control form-control-lg
                                       <c:if test="${errors.firstNameError}">
                                            is-invalid
                                       </c:if>"
                                       placeholder="<fmt:message key="firstname.placeholder"/>"
                                       aria-describedby="inputGroupPrepend"
                                       maxlength="255"
                                       required>
                                <c:if test="${errors.firstNameError}">
                                    <div class="invalid-feedback">
                                        <fmt:message key="error.firstname"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div class="col-auto ml-auto w-50">
                            <label for="lastName"><fmt:message key="lastname"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupPrepend2">
                                        <i class="fa fa-user fa-2x" aria-hidden="true"></i>
                                    </span>
                                </div>
                                <input type="text" id="lastName"
                                       name="lastName"
                                       value="<c:out value="${userDTO.lastName}"/>"
                                       class="form-control form-control-lg
                                       <c:if test="${errors.lastNameError}">
                                            is-invalid
                                       </c:if>"
                                       placeholder="<fmt:message key="lastname.placeholder"/>"
                                       maxlength="255"
                                       aria-describedby="inputGroupPrepend"
                                       required>
                                <c:if test="${errors.lastNameError}">
                                    <div class="invalid-feedback">
                                        <fmt:message key="error.lastname"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password"><fmt:message key="password"/></label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend3">
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

                    <div class="form-group form-row">
                        <div class="col-auto mr-auto w-50">
                            <label for="gender"><fmt:message key="gender"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend4">
                                    <i class="fa fa-venus-mars fa-lg" aria-hidden="true"></i>
                                </span>
                                </div>
                                <select id="gender"
                                        name="gender"
                                        class="form-control form-control-lg">
                                    <option value="MALE"
                                            <c:if test="${userDTO.gender eq Gender.MALE}">
                                                selected
                                            </c:if>
                                    >
                                        <fmt:message key="gender.male"/>
                                    </option>
                                    <option value="FEMALE"
                                            <c:if test="${userDTO.gender eq Gender.FEMALE}">
                                                selected
                                            </c:if>
                                    >
                                        <fmt:message key="gender.female"/>
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="col-auto mr-auto w-50">
                            <label for="dateOfBirth"><fmt:message key="dateofbirth"/></label>

                            <div class="input-group">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend5">
                                    <i class="fa fa-calendar fa-lg" aria-hidden="true"></i>
                                </span>
                                </div>
                                <input type="date" id="dateOfBirth"
                                       name="dateOfBirth"
                                       value="<c:out value="${userDTO.dateOfBirth}"/>"
                                       class="form-control form-control-lg"
                                       max="3000-12-31" min="1900-01-01" required>
                            </div>
                        </div>

                    </div>

                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary btn-lg mt-3"><fmt:message key="signup"/></button>
                    </div>

                </form>
            </div>
            <c:if test="${errorRegistration}">
                <div class="card-footer text-muted">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <fmt:message key="error.registration"/>
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
