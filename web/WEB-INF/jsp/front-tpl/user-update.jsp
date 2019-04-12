<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12 bg-light border border-primary rounded mb-5 mt-5">
        <div class="blog-post">
            <h1 class="blog-post-title"><fmt:message key="user.update"/></h1>
            <form action="${pageContext.request.contextPath}/app/user?action=update&id=${requestScope.entity.id}"
                  method="POST"
                  accept-charset="UTF-8">
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="username">Id</label>
                        <input type="text" class="form-control" id="id" name="id" placeholder=""
                               value="${requestScope.entity.id}" readonly>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="username"><fmt:message key="user.form.username"/></label>
                        <input type="text" class="form-control" id="username" name="username" placeholder=""
                               value="${requestScope.entity.username}">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="email"><fmt:message key="user.form.email"/></label>
                        <input type="email" class="form-control" id="email" name="email" placeholder=""
                               value="${requestScope.entity.email}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="name"><fmt:message key="user.form.name"/></label>
                        <input type="text" class="form-control" id="name" name="name" placeholder=""
                               value="${requestScope.entity.name}">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="surname"><fmt:message key="user.form.surname"/></label>
                        <input type="text" class="form-control" id="surname" name="surname" placeholder=""
                               value="${requestScope.entity.surname}">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="registerDate"><fmt:message key="user.form.registerdate"/></label>
                        <input type="date" class="form-control" id="registerDate" name="registerDate" placeholder=""
                               value="${requestScope.entity.registerDate}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="active"><fmt:message key="user.form.active"/></label>
                        <input type="checkbox" class="form-control-file" id="active" name="active" value="Опубликован"
                               <c:if test="${requestScope.entity.active eq 'true'}">checked</c:if>
                        >
                    </div>
                    <div class="form-group col-md-2">
                        <label for="role"><fmt:message key="user.form.role"/></label>
                        <select id="role" name="role" class="form-control">
                            <c:forEach items="${applicationScope.roles}" var="role">
                                <option
                                        <c:if test="${requestScope.entity.role == role}">selected</c:if>
                                        value="${role}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="phone"><fmt:message key="user.form.phone"/></label>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder=""
                               value="${requestScope.entity.phone}">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="birthDate"><fmt:message key="user.form.birthdate"/></label>
                        <input type="date" class="form-control" id="birthDate" name="birthDate" placeholder=""
                               value="${requestScope.entity.birthDate}">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="action.update"/></button>
            </form>
            <hr>
            <form action="${pageContext.request.contextPath}/app/user?action=update-pass&id=${requestScope.entity.id}" method="post"
                  class="text-right">
                <div class="form-group offset-8 col-md-4">
                    <label for="password"><fmt:message key="user.form.password"/></label>
                    <input type="password" class="form-control" name="password" id="password">
                </div>
                <div class="form-group offset-8 col-md-4">
                    <button type="submit" class="btn btn-secondary"><fmt:message key="action.update"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
