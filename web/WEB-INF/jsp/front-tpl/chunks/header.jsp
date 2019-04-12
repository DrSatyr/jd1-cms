<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<header class="blog-header py-3">
    <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1">
            <c:forEach items="${applicationScope.languages}" var="language">
                <a class="btn <c:if test="${sessionScope.lang == language}">btn-outline-success</c:if>
                 btn-sm" href="${pageContext.request.contextPath}/app/locale?lang=${language}"
                   role="button">${language}</a>
            </c:forEach>
        </div>
        <div class="col-4 text-center">
            <a class="blog-header-logo text-dark" href="${pageContext.request.contextPath}/app">
                <fmt:message key="site.name"/>
            </a>
        </div>
        <c:if test="${empty sessionScope.user}">
            <div class="col-4 d-flex justify-content-end align-items-center">
                <a class="btn btn-sm btn-outline-secondary"
                   href="${pageContext.request.contextPath}/app/login"><fmt:message key="header.login"/></a>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <div class="col-4 d-flex justify-content-end align-items-center">
                <div>
                    <span><fmt:message key="header.greating"/> ${sessionScope.user.username} </span>
                    <a class="btn btn-sm btn-outline-secondary"
                       href="${pageContext.request.contextPath}/app/login?action=logout"><fmt:message key="header.logout"/></a>
                    <c:if test="${sessionScope.user.role == 'ADMINISTRATOR'}">
                        <a class="btn btn-sm btn-outline-danger" data-toggle="collapse" href="#adminMenu"
                           href="${pageContext.request.contextPath}/app/login?action=logout"><fmt:message key="header.adminmenu"/></a>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
        <jsp:include page="adminMenu.jsp"/>
</header>
