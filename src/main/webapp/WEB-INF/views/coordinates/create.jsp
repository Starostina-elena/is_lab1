<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Координаты</title>
    <style>
        .error { color: red; font-size: 0.9em; }
        .field { margin-bottom: 10px; }
    </style>
</head>
<body>
<h2><c:choose><c:when test="${editId != null}">Редактировать координаты</c:when><c:otherwise>Создать координаты</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/coordinates/create" />
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/coordinates/update/${editId}" />
</c:if>
<form:form modelAttribute="coordinates" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true" />
        </div>
    </c:if>
    <div class="field">
        <label for="x">X:</label>
        <form:input path="x" type="number" step="1" required="true"/>
        <form:errors path="x" cssClass="error" />
    </div>
    <div class="field">
        <label for="y">Y:</label>
        <form:input path="y" type="number" step="1" required="true"/>
        <form:errors path="y" cssClass="error" />
    </div>
    <button type="submit">Сохранить</button>
</form:form>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/coordinates/delete/${editId}" style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить координаты?');">Удалить</button>
    </form>
</c:if>
</body>
</html>
