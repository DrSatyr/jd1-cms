<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="row">
    <div class="col-md-12 bg-light border border-primary rounded mb-5 mt-5">
        <div class="blog-post">
            <h1 class="blog-post-title"><fmt:message key="category.update.title"/></h1>
            <form action="${pageContext.request.contextPath}/app/category?action=update" method="POST"
                  accept-charset="UTF-8">
                <div class="form-row">
                    <div class="form-group col-md-2">
                        <label for="created"><fmt:message key="category.form.created"/></label>
                        <input type="date" class="form-control" id="created" name="created" value="${requestScope.entity.created}">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="alias"><fmt:message key="category.form.alias"/></label>
                        <input type="text" class="form-control" id="alias" name="alias" placeholder="Alias" value="${requestScope.entity.alias}">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="parentId"><fmt:message key="category.form.parent"/></label>
                        <select id="parentId" name="parentId" class="form-control">
                            <option value="0"><fmt:message key="category.form.toplevel"/></option>
                            <c:forEach items="${requestScope.allCategories}" var="category">
                                <c:forEach items="${category.translation}" var="translation">
                                    <c:if test="${translation.key == sessionScope.lang}">
                                        <option <c:if test="${category.id == requestScope.entity.parentId}">selected</c:if>
                                                value="${category.id}">${translation.value.name}</option>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="id" >Id</label>
                        <input type="text" class="form-control" id="id" name="id" value="${requestScope.entity.id}" readonly>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="active"><fmt:message key="category.form.active"/></label>
                        <input type="checkbox" class="form-control-file" id="active" name="active" value="Опубликован"
                               <c:if test="${requestScope.entity.active eq true}">checked</c:if>>
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
                                    <label for="name${translation.key}"><fmt:message key="category.form.name"/></label>
                                    <input type="text" class="form-control" id="name${translation.key}" name="name${translation.key}"
                                           value="${translation.value.name}" placeholder="Заголовок">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="metaTitle${translation.key}"><fmt:message key="category.form.metatitle"/></label>
                                    <input type="text" class="form-control" id="metaTitle${translation.key}"
                                           value="${translation.value.metaTitle}"
                                           name="metaTitle${translation.key}"
                                           placeholder="">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="metaDescription${translation.key}"><fmt:message key="category.form.metadesc"/></label>
                                    <input type="text" class="form-control" id="metaDescription${translation.key}"
                                           value="${translation.value.metaDescription}"
                                           name="metaDescription${translation.key}"
                                           placeholder="">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="metaKeywords${translation.key}"><fmt:message key="category.form.metakeywords"/></label>
                                    <input type="text" class="form-control" id="metaKeywords${translation.key}"
                                           value="${translation.value.metaKeywords}"
                                           name="metaKeywords${translation.key}"
                                           placeholder="">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label for="introText${translation.key}"><fmt:message key="category.form.introtext"/></label>
                                    <textarea class="form-control" id="introText${translation.key}" name="introText${translation.key}"
                                              placeholder="">${translation.value.introText}</textarea>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <hr>
                <button type="submit" class="btn btn-primary"><fmt:message key="action.update"/></button>
            </form>
        </div>
    </div>
</div>
