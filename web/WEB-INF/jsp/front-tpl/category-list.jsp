<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row pt-3">
    <div class="col-md-8 blog-main">
        <h1 class="pb-4 mb-4 font-italic border-bottom">
            <fmt:message key="category.list.heading"/>
        </h1>

        <c:forEach items="${requestScope.categoriesWithContent}" var="category">
            <c:forEach items="${category.translation}" var="categoryTranslation">
                <c:if test="${categoryTranslation.key == sessionScope.lang}">
                    <h2>${categoryTranslation.value.name}</h2>

                    <ul>
                    <c:forEach items="${category.contents}" var="content">
                        <c:forEach items="${content.translation}" var="contentTranslation">
                            <c:if test="${contentTranslation.key == sessionScope.lang}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/app/content?id=${content.id}">${contentTranslation.value.name}</a>
                                    <span class="content-list-created">${content.created} </span>
                                    <span class="content-list-created">${content.createdBy.name} ${content.createdBy.surname}</span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    </ul>
                    <a class="btn btn-success btn-sm" role="button" aria-pressed="true" href="${pageContext.request.contextPath}/app/category?id=${category.id}"><fmt:message key="category.list.tocategory"/> </a>
                    <hr>
                </c:if>
            </c:forEach>
        </c:forEach>

    </div><!-- /.blog-main -->

    <aside class="col-md-4 blog-sidebar">
        <div class="p-4 mb-3 bg-light rounded">
            <h4 class="font-italic">About</h4>
            <p class="mb-0">Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus
                sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>
        </div>

        <div class="p-4">
            <h4 class="font-italic">Archive</h4>
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

</div><!-- /.row -->
