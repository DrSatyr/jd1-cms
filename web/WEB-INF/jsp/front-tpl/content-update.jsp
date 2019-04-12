<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12 bg-light border border-primary rounded mb-5 mt-5">
        <div class="blog-post">
            <h1 class="blog-post-title">Редактирование контента:</h1>
            <form action="${pageContext.request.contextPath}/app/content?action=update" method="POST" accept-charset="UTF-8" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="created">Дата создания</label>
                        <input type="date" class="form-control" id="created" name="created" value="${requestScope.entity.created}">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="alias">Алиас</label>
                        <input type="text" class="form-control" id="alias" name="alias" placeholder="Alias" value="${requestScope.entity.alias}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="user">Пользователь</label>
                        <input type="text" class="form-control" id="user" name="user" placeholder=""
                               value="${requestScope.entity.userName} ${requestScope.entity.userSurname}" readonly>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="categoryId">Категория</label>
                        <select id="CategoryId" name="CategoryId"  class="form-control">
                            <c:forEach items="${requestScope.allCategories}" var="category">
                                <c:forEach items="${category.translation}" var="translation">
                                    <c:if test="${translation.key == sessionScope.lang}">
                                        <option <c:if test="${category.id == requestScope.entity.category.id}">selected</c:if>
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
                                        <option <c:if test="${contentType.id == requestScope.entity.contentType.id}">selected</c:if>
                                                value="${contentType.id}">${contentType.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-row">

                        <input type="file" class="form-control-file" id="image" name="image" hidden>
                    <div class="form-group col-md-6">
                        <label for="active">Опубликован</label>
                        <input type="checkbox" class="form-control-file" id="active" name="active"
                               value="true" <c:if test="${requestScope.entity.active eq true}">checked</c:if>>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="hits">Посещения</label>
                        <input type="text" class="form-control" id="hits" name="hits" value="${requestScope.entity.hits}" readonly>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="id">Id</label>
                        <input type="text" class="form-control" id="id" name="id" value="${requestScope.entity.id}" readonly>
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
                    <c:forEach items="${requestScope.entity.translation}" var="translation">
                        <c:set var="tabpane" scope="request" value=""/>
                        <c:if test="${translation.key == sessionScope.lang}">
                            <c:set var="tabpane" scope="request" value="active"/>
                        </c:if>
                        <div class="tab-pane fade show ${tabpane}" id="${translation.key}" role="tabpanel"
                             aria-labelledby="${translation.key}">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="name${translation.key}">Заголовок</label>
                                    <input type="text" class="form-control" id="name${translation.key}" name="name${translation.key}"
                                           value="${translation.value.name}" placeholder="Название">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="metaTitle${translation.key}">Мета заголовок</label>
                                    <input type="text" class="form-control" id="metaTitle${translation.key}" name="metaTitle${translation.key}"
                                           value="${translation.value.metaTitle}" placeholder="Мета-заголовок">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="metaDescription${translation.key}">Мета описание</label>
                                    <input type="text" class="form-control" id="metaDescription${translation.key}" name="metaDescription${translation.key}"
                                           value="${translation.value.metaDescription}" placeholder="Мета описание">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="metaKeywords${translation.key}">Мета ключевые слова</label>
                                    <input type="text" class="form-control" id="metaKeywords${translation.key}" name="metaKeywords${translation.key}"
                                           value="${translation.value.metaKeywords}" placeholder="Мета ключевые слова">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="introText${translation.key}">Краткое описание</label>
                                    <textarea class="form-control" id="introText${translation.key}" name="introText${translation.key}"
                                              placeholder="Краткое описание">${translation.value.introText}</textarea>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="fullText${language}">Cодержимое</label>
                                    <textarea rows="10" class="form-control" id="fullText${translation.key}" name="fullText${translation.key}"
                                              placeholder="Cодержимое">${translation.value.fullText}</textarea>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <hr>
                <button type="submit" class="btn btn-primary">Сохранить</button>
            </form>

            <hr>
            <form action="${pageContext.request.contextPath}/app/content?action=update-image&id=${requestScope.entity.id}" method="post" enctype="multipart/form-data"
                  class="text-right">
                <div class="form-group offset-8 col-md-4">
                    <label for="image2">Обновить картинку</label>
                    <input type="file" class="form-control-file" id="image2" name="image">

                </div>
                <div class="form-group offset-8 col-md-4">
                    <button type="submit" class="btn btn-secondary">Обновить</button>
                </div>
            </form>
        </div>
    </div>
</div>
