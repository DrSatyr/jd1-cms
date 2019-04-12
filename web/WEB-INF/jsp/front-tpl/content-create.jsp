<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12 bg-light border border-primary rounded mb-5 mt-5">
        <div class="blog-post">
            <h1 class="blog-post-title">Создание контента:</h1>
            <form action="${pageContext.request.contextPath}/app/content?action=create" method="POST" accept-charset="UTF-8" enctype="multipart/form-data"  >
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="created">Дата создания</label>
                        <input type="date" class="form-control" id="created" name="created" placeholder="">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="alias">Алиас</label>
                        <input type="text" class="form-control" id="alias" name="alias" placeholder="Alias">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="user">Пользователь</label>
                        <input type="text" class="form-control" id="user" name="user" placeholder="" readonly>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="categoryId">Категория</label>
                        <select id="CategoryId" name="CategoryId"  class="form-control">
                            <c:forEach items="${requestScope.allCategories}" var="category">
                                <c:forEach items="${category.translation}" var="translation">
                                    <c:if test="${translation.key == sessionScope.lang}">
                                        <option <c:if test="${category.id == param.get('categoryId')}">selected</c:if>
                                                value="${category.id}">${translation.value.name}</option>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="contentTypeId">Тип контента</label>
                        <select id="contentTypeId" name="contentTypeId"  class="form-control">
                            <c:forEach items="${requestScope.contentTypes}" var="contentType">
                                        <option value="${contentType.id}">${contentType.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="image">Картинка</label>
                        <input type="file" class="form-control-file" id="image" name="image">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="active">Опубликован</label>
                        <input type="checkbox" class="form-control-file" id="active" name="active" value="Опубликован" checked>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="hits">Посещения</label>
                        <input type="text" class="form-control" id="hits" name="hits" readonly>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="id">Id</label>
                        <input type="text" class="form-control" id="id" name="id" readonly>
                    </div>
                </div>

                <hr>
                <ul class="nav nav-tabs" id="translation-nav" role="tablist">
                    <c:forEach items="${applicationScope.languages}" var="language">
                        <li class="nav-item">
                            <c:set var="navlink" scope="request" value=""/>
                            <c:set var="ariaselected" scope="request" value="false"/>
                            <c:if test="${language == sessionScope.lang}">
                                <c:set var="navlink" scope="request" value="active"/>
                                <c:set var="ariaselected" scope="request" value="true"/>
                            </c:if>
                            <a class="nav-link ${navlink}" data-toggle="tab" href="#${language}" role="tab"
                               aria-controls="${language}" aria-selected="${ariaselected}">${language}</a>
                        </li>
                    </c:forEach>
                </ul>
                <div class="tab-content" id="translation">
                    <c:forEach items="${applicationScope.languages}" var="language">
                        <c:set var="tabpane" scope="request" value=""/>
                        <c:if test="${language == sessionScope.lang}">
                            <c:set var="tabpane" scope="request" value="active"/>
                        </c:if>
                        <div class="tab-pane fade show ${tabpane}" id="${language}" role="tabpanel"
                             aria-labelledby="${language}">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="name${language}">Заголовок</label>
                                    <input type="text" class="form-control" id="name${language}" name="name${language}" placeholder="Название">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="metaTitle${language}">Мета заголовок</label>
                                    <input type="text" class="form-control" id="metaTitle${language}" name="metaTitle${language}"
                                           placeholder="Мета-заголовок">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="metaDescription${language}">Мета описание</label>
                                    <input type="text" class="form-control" id="metaDescription${language}" name="metaDescription${language}"
                                           placeholder="Мета описание">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="metaKeywords${language}">Мета ключевые слова</label>
                                    <input type="text" class="form-control" id="metaKeywords${language}" name="metaKeywords${language}"
                                           placeholder="Мета ключевые слова">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="introText${language}">Краткое описание</label>
                                    <textarea class="form-control" id="introText${language}" name="introText${language}"
                                              placeholder="Краткое описание"></textarea>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="fullText${language}">Cодержимое</label>
                                    <textarea rows="10" class="form-control" id="fullText${language}" name="fullText${language}"
                                              placeholder="Cодержимое"></textarea>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <hr>
                <button type="submit" class="btn btn-primary">Сохранить</button>
            </form>
        </div>
    </div>
</div>
