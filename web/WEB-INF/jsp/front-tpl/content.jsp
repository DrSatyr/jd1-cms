<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${requestScope.entity.translation}" var="translation">
    <c:if test="${translation.key == sessionScope.lang}">
        <c:set var="translation" scope="request" value="${translation.value}"/>
    </c:if>
</c:forEach>

<c:forEach items="${requestScope.entity.category.translation}" var="categoryTranslation">
    <c:if test="${categoryTranslation.key == sessionScope.lang}">
        <c:set var="categoryTranslation" scope="request" value="${categoryTranslation.value}"/>
    </c:if>
</c:forEach>

<div class="row">
    <div class="col-md-8 blog-main">

        <jsp:include page="chunks/entityEditButtons.jsp"/>

        <div class="blog-post">
            <h1 class="blog-post-title">${requestScope.translation.name}</h1>
            <p class="blog-post-meta"><span>Дата: </span> ${requestScope.entity.created} <span>Автор: </span>
                <a href="${pageContext.request.contextPath}/app/user?id=${requestScope.entity.userId}">
                    ${requestScope.entity.userName} ${requestScope.entity.userSurname}
                </a> <span>Опубликовано в: </span>
                <a href="${pageContext.request.contextPath}/app/category?id=${requestScope.entity.category.id}">
                    ${requestScope.categoryTranslation.name}
                </a>
            </p>

            <div class="mb-3 content-intro-text"><p>${requestScope.translation.introText}</p></div>

            <div class="mb-3 content-intro-text">${requestScope.translation.fullText}</div>

            <c:if test="${not empty requestScope.comments}">
                <h4 class="font-italic">Комментарии:</h4>
                <c:forEach var="comment" items="${requestScope.comments}">
                    <div class="p-4 mb-3 bg-light rounded">
                        <h6 class="font-italic">${comment.user.name} ${comment.user.surname}</h6>
                        <div class="comment-date">${comment.created}</div>
                        <p class="mb-0">${comment.fullText}</p>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty requestScope.tags}">
                <h4 class="font-italic">Тэги:</h4>
                <nav class="blog-pagination">
                    <c:forEach var="tag" items="${requestScope.tags}">
                        <a class="btn btn-outline-primary"
                           href="${pageContext.request.contextPath}app/tag?id=${tag.id}">${tag.value}</a>
                    </c:forEach>
                </nav>
            </c:if>
        </div>
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
    </aside><!-- /.blog-sidebar -->

</div>
<!-- /.row -->
