<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row justify-content-center">
    <div class="col-md-5 m-5 p-5 bg-light border border-primary rounded">
        <form action="${pageContext.request.contextPath}/app/login" method="post">
            <div class="form-group">
                <label for="username"><fmt:message key="login.username"/></label>
                <input type="text" class="form-control" id="username" name="username" aria-describedby="loginHelp" placeholder="">
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="login.pass"/></label>
                <input type="password" class="form-control" id="password" name="password" placeholder="">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="login.login"/></button>
        </form>
    </div>
</div>