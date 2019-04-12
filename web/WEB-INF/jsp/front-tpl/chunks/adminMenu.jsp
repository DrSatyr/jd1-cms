<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<c:if test="${sessionScope.user.role eq 'ADMINISTRATOR'}">
    <div class="collapse" id="adminMenu">
        <div class="col-12 border border-secondary rounded align-items-end mt-3 p-1 d-flex flex-row-reverse">
            <a class="btn btn-outline-secondary btn-sm" role="button"
               href="${pageContext.request.contextPath}/app/user"><fmt:message key="admin.button.users"/></a>
            <a class="btn btn-outline-secondary btn-sm" role="button"
               href="${pageContext.request.contextPath}/app/comments"><fmt:message key="admin.button.comments"/></a>
        </div>
    </div>
</c:if>
