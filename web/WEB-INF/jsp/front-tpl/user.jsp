<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12">

        <jsp:include page="chunks/entityEditButtons.jsp"/>

        <div class="blog-post">
            <h1><fmt:message key="user.user"/> ${requestScope.entity.username}</h1>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="user.key"/></th>
                    <th scope="col"><fmt:message key="user.value"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>id</td>
                    <td>${requestScope.entity.id}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.name"/></td>
                    <td>${requestScope.entity.name}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.surname"/></td>
                    <td>${requestScope.entity.surname}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.email"/></td>
                    <td>${requestScope.entity.email}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.phone"/></td>
                    <td>${requestScope.entity.phone}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.active"/></td>
                    <td>${requestScope.entity.active}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.role"/></td>
                    <td>${requestScope.entity.role}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.registerdate"/></td>
                    <td>${requestScope.entity.registerDate}</td>
                </tr>
                <tr>
                    <td><fmt:message key="user.form.birthdate"/></td>
                    <td>${requestScope.entity.birthDate}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>