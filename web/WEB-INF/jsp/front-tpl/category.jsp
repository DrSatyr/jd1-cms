<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<c:forEach items="${requestScope.entity.translation}" var="translation">
    <c:if test="${translation.key == sessionScope.lang}">
        <c:set var="translation" scope="request" value="${translation.value}"/>
    </c:if>
</c:forEach>

<div class="row pt-3">
    <div class="col-md-8 blog-main">

        <jsp:include page="chunks/entityEditButtons.jsp"/>

        <span><fmt:message key="category.namelabel"/> </span>
        <h1 class="pb-2 font-italic">
            ${translation.name}:
        </h1>
        <p class="border-bottom">${translation.introText}</p>

        <div class="blog-post">
            <ul>
                <c:forEach var="content" items="${requestScope.categoryContents}">
                    <c:forEach items="${content.translation}" var="translation">
                        <c:if test="${translation.key == sessionScope.lang}">
                            <li>
                                <a href="${pageContext.request.contextPath}/app/content?id=${content.id}">${translation.value.name}</a>
                                <span class="content-list-created">${content.created} </span>
                                <span class="content-list-created">${content.createdBy.name} ${content.createdBy.surname}</span>
                            </li>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </ul>
        </div>

        <c:if test="${sessionScope.user.role eq 'ADMINISTRATOR' or sessionScope.user.role eq 'EDITOR'}">
            <div class="edit-buttons col-md-12">
                <a class="btn btn-outline-success btn-sm"
                   href="${pageContext.request.contextPath}/app/content?action=create&categoryId=${requestScope.entity.id}"
                   role="button"><fmt:message key="action.add"/></a>
            </div>
        </c:if>

    </div>

    <aside class="col-md-4 blog-sidebar">
        <div class="p-4 mb-3 bg-light rounded">
            <h4 class="font-italic">About</h4>
            <p class="mb-0">Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus
                sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>
        </div>

        <div class="p-4">
            <h4 class="font-italic">Archives</h4>
            <ol class="list-unstyled mb-0">
                <li><a href="#">March 2014</a></li>
                <li><a href="#">February 2014</a></li>
                <li><a href="#">January 2014</a></li>
                <li><a href="#">December 2013</a></li>
                <li><a href="#">November 2013</a></li>
                <li><a href="#">October 2013</a></li>
                <li><a href="#">September 2013</a></li>
                <li><a href="#">August 2013</a></li>
                <li><a href="#">July 2013</a></li>
                <li><a href="#">June 2013</a></li>
                <li><a href="#">May 2013</a></li>
                <li><a href="#">April 2013</a></li>
            </ol>
        </div>

        <div class="p-4">
            <h4 class="font-italic">Elsewhere</h4>
            <ol class="list-unstyled">
                <li><a href="#">GitHub</a></li>
                <li><a href="#">Twitter</a></li>
                <li><a href="#">Facebook</a></li>
            </ol>
        </div>
    </aside>

</div>
