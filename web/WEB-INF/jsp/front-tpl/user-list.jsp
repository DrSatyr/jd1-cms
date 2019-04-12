<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12">
        <div class="blog-post">
            <h1><fmt:message key="user.list.title"/></h1>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">id</th>
                    <th scope="col"><fmt:message key="user.form.username"/></th>
                    <th scope="col"><fmt:message key="user.form.name"/></th>
                    <th scope="col"><fmt:message key="user.form.surname"/></th>
                    <th scope="col"><fmt:message key="user.form.email"/></th>
                    <th scope="col"><fmt:message key="user.form.role"/></th>
                    <th scope="col"><fmt:message key="user.form.active"/></th>
                    <th scope="col"><fmt:message key="user.list.actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.entities}" var="user">
                    <tr>
                        <th scope="row"><a
                                href="${pageContext.request.contextPath}/app/user?id=${user.id}">${user.id}</a></th>
                        <th scope="row"><a class="btn btn-sm btn-success"
                                           href="${pageContext.request.contextPath}/app/user?id=${user.id}">${user.username}</a>
                        </th>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>${user.active}</td>
                        <td>
                            <a class="btn btn-outline-warning btn-sm"
                               href="${pageContext.request.contextPath}/app/user?id=${user.id}&action=update"
                               role="button"><fmt:message key="action.update"/></a>
                            <a class="btn btn-outline-danger btn-sm"
                               href="${pageContext.request.contextPath}/app/user?id=${user.id}&action=delete"
                               role="button"><fmt:message key="action.delete"/></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="edit-buttons col-md-12">
                <a class="btn btn-outline-success btn-sm"
                   href="${pageContext.request.contextPath}/app/user?action=create"
                   role="button"><fmt:message key="action.add"/></a>
            </div>
        </div>
    </div>
</div>