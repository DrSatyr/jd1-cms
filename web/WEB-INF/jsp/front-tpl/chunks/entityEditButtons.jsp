<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<c:if test="${sessionScope.user.role eq 'ADMINISTRATOR' or sessionScope.user.role eq 'EDITOR'}">
    <div class="edit-buttons col-md-12">
        <a class="btn btn-outline-success btn-sm"
           href="${pageContext.request.contextPath}/app/${requestScope.view}?action=create"
           role="button"><fmt:message key="action.create"/></a>
        <a class="btn btn-outline-warning btn-sm"
           href="${pageContext.request.contextPath}/app/${requestScope.view}?id=${requestScope.entity.id}&action=update"
           role="button"><fmt:message key="action.update"/></a>
        <a class="btn btn-outline-danger btn-sm"
           href="${pageContext.request.contextPath}/app/${requestScope.view}?id=${requestScope.entity.id}&action=delete"
           role="button"><fmt:message key="action.delete"/></a>
    </div>
</c:if>