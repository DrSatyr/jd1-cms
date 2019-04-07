<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="nav-scroller py-1 mb-2">
    <nav class="nav d-flex justify-content-between">
        <c:forEach items="${requestScope.topMenu}" var="menuItem">
            <c:forEach items="${menuItem.translation}" var="translation">
                <c:if test="${translation.key == sessionScope.lang}">
                    <a class="p-2 text-muted"
                       href="${pageContext.request.contextPath}/app/category?id=${menuItem.id}">${translation.value.name}</a>
                </c:if>
            </c:forEach>
        </c:forEach>
    </nav>
</div>
