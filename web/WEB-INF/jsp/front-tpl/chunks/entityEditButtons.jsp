<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="edit-buttons col-md-12">
    <a class="btn btn-outline-success btn-sm"
       href="${pageContext.request.contextPath}/app/${requestScope.view}?id=${requestScope.entity.id}&action=create"
       role="button">Создать</a>
    <a class="btn btn-outline-warning btn-sm"
       href="${pageContext.request.contextPath}/app/${requestScope.view}?id=${requestScope.entity.id}&action=update"
       role="button">Редактировать</a>
    <a class="btn btn-outline-danger btn-sm"
       href="${pageContext.request.contextPath}/app/${requestScope.view}?id=${requestScope.entity.id}&action=delete"
       role="button">Удалить</a>
</div>