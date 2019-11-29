<%--@elvariable id="periodicalTypes" type="java.util.List"--%>
<%--@elvariable id="frequencies" type="java.util.List"--%>
<%--@elvariable id="publishers" type="java.util.List"--%>
<%--@elvariable id="errors" type="java.util.Map"--%>
<%--@elvariable id="periodicalDTO" type="com.gmail.maxsvynarchuk.presentation.util.dto.PeriodicalDTO"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="form-group">
    <label for="name">
        <fmt:message key="periodical.name"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend0">
                                    <i class="fa fa-file-text fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <input type="text" id="name"
               name="name"
               value="<c:out value="${periodicalDTO.name}"/>"
               class="form-control form-control-lg
               <c:if test="${errors.nameError}">
                        is-invalid
               </c:if>"
               placeholder="<fmt:message key="create.periodical.name.placeholder"/>"
               required>
    <c:if test="${errors.nameError}">
        <div class="invalid-feedback">
            <fmt:message key="error.periodical.name"/>
        </div>
    </c:if>
    </div>
</div>

<div class="form-group">
    <label for="description">
        <fmt:message key="periodical.description"/>
    </label>
    <textarea id="description"
              name="description"
              class="form-control
              <c:if test="${errors.descriptionError}">
                        is-invalid
              </c:if>"
              placeholder="<fmt:message key="create.periodical.description.placeholder"/>"
              rows="5"
              required><c:out value="${periodicalDTO.description}"/></textarea>
<c:if test="${errors.descriptionError}">
    <div class="invalid-feedback">
        <fmt:message key="error.periodical.description"/>
    </div>
</c:if>
</div>


<div class="form-group">
    <label for="price">
        <fmt:message key="periodical.price"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend1">
                                    <i class="fa fa-usd fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <input type="number" id="price"
               min="1" step="0.01"
               name="price"
               value="<c:out value="${periodicalDTO.price}"/>"
               class="form-control form-control-lg
               <c:if test="${errors.priceError}">
                        is-invalid
               </c:if>"
               placeholder="<fmt:message key="create.periodical.price.placeholder"/>"
               required>
    <c:if test="${errors.priceError}">
        <div class="invalid-feedback">
            <fmt:message key="error.periodical.price"/>
        </div>
    </c:if>
    </div>
</div>

<div class="form-group">
    <label for="periodicalType">
        <fmt:message key="periodical.type"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend2">
                                    <i class="fa fa-file-text fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <select id="periodicalType"
                name="periodicalTypeId"
                class="form-control form-control-lg"
                required>
        <c:forEach var="type" items="${periodicalTypes}">
            <option value="${type.id}"
                    <c:if test="${type.id == periodicalDTO.periodicalTypeId}">
                        selected
                    </c:if>
            >
                <c:out value="${type.name}"/>
            </option>
        </c:forEach>
        </select>
    </div>
</div>

<div class="form-group">
    <label for="frequency">
        <fmt:message key="periodical.frequency"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend3">
                                    <i class="fa fa-files-o fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <select id="frequency"
                name="frequencyId"
                class="form-control form-control-lg"
                required>
        <c:forEach var="frequency" items="${frequencies}">
            <option value="${frequency.id}"
                    <c:if test="${frequency.id == periodicalDTO.frequencyId}">
                        selected
                    </c:if>
            >
                <c:out value="${frequency.name}"/> - <c:out value="${frequency.meaning}"/>
            </option>
        </c:forEach>
        </select>
    </div>
</div>

<div class="form-group">
    <label for="publisher">
        <fmt:message key="periodical.publisher"/>
    </label>
    <div class="input-group">
        <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupPrepend4">
                                    <i class="fa fa-print fa-lg" aria-hidden="true"></i>
                                </span>
        </div>
        <select id="publisher"
                name="publisherId"
                class="form-control form-control-lg"
                required>
        <c:forEach var="publisher" items="${publishers}">
            <option value="${publisher.id}"
                    <c:if test="${publisher.id == periodicalDTO.publisherId}">
                        selected
                    </c:if>
            >
                <c:out value="${publisher.name}"/>
            </option>
        </c:forEach>
        </select>
    </div>
</div>
