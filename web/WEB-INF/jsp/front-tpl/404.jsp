<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12 blog-main">
        <h1 class="pb-4 mb-4 font-italic border-bottom">
            <fmt:message key="page404.heading"/>
        </h1>
        <p><fmt:message key="page404.text"/></p>
    </div>
</div>
