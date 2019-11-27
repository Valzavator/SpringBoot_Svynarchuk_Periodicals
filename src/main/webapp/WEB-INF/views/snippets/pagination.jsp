<%--@elvariable id="pageDTO" type="com.gmail.maxsvynarchuk.presentation.util.dto.PageDTO"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:if test="${pageDTO.numberOfPages gt 1}">
    <nav aria-label="Page navigation" class="mb-5">
        <ul class="pagination pagination-lg justify-content-center">
            <li class="page-item <c:if test="${pageDTO.currentPage eq 0}">disabled</c:if>">
                <a class="page-link" href="<c:url value="?page=${pageDTO.currentPage - 1}"/>" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="0" end="${pageDTO.numberOfPages - 1}" var="counter">
                <li class="page-item <c:if test="${pageDTO.currentPage eq counter}">disabled</c:if>">
                    <a class="page-link" href="<c:url value="?page=${counter}"/>">
                            ${counter + 1}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item <c:if test="${pageDTO.currentPage eq pageDTO.numberOfPages - 1}">disabled</c:if>">
                <a class="page-link" href="<c:url value="?page=${pageDTO.currentPage + 1}"/>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</c:if>
