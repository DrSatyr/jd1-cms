<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<jsp:include page="chunks/head.jsp"/>
<body>
    <div class="container">
        <jsp:include page="chunks/header.jsp"/>
        <jsp:include page="chunks/top-nav.jsp"/>
    </div>
    <main role="main" class="container">
        <jsp:include page="${requestScope.viewPath}"/>
    </main>
    <jsp:include page="chunks/footer.jsp"/>
    <jsp:include page="chunks/scripts.jsp"/>
</body>
</html>