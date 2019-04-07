<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="jumbotron p-4 p-md-5 text-white rounded bg-dark">
    <div class="col-md-6 px-0">
        <c:forEach items="${requestScope.mainNews}" var="mainNews">
            <c:forEach items="${mainNews.translation}" var="translation">
                <c:if test="${translation.key == sessionScope.lang}">
                    <h1 class="display-6 font-italic">${translation.value.name}</h1>
                    <p class="lead my-3">${translation.value.introText}</p>
                    <p class="lead mb-0">
                        <a href="${pageContext.request.contextPath}/app/content?id=${mainNews.id}"
                           class="text-white font-weight-bold">подробнее...</a>
                    </p>
                </c:if>
            </c:forEach>
        </c:forEach>
    </div>
</div>

<div class="row mb-2">
    <c:forEach items="${requestScope.categoriesWithContent}" var="category">
        <div class="col-md-6">
            <div class="row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                <div class="col p-4 d-flex flex-column position-static">
                    <c:forEach items="${category.translation}" var="translation">
                        <c:if test="${translation.key == sessionScope.lang}">
                            <strong class="d-inline-block mb-2 text-success">
                                <a href="${pageContext.request.contextPath}/app/category?id=${category.id}">
                                        ${translation.value.name}
                                </a>
                            </strong>
                        </c:if>
                    </c:forEach>
                    <ul>
                        <c:forEach items="${category.contents}" var="content">
                            <c:forEach items="${content.translation}" var="translation">
                                <c:if test="${translation.key == sessionScope.lang}">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/app/content?id=${content.id}">${translation.value.name}</a>
                                        <span class="content-list-created">${content.created} </span>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </c:forEach>
</div>