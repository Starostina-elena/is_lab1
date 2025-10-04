<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Локация</title>
</head>
<body>
<h2><c:choose><c:when test="${editId != null}">Редактировать локацию</c:when><c:otherwise>Создать локацию</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/locations/create" />
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/locations/update/${editId}" />
</c:if>
<form:form modelAttribute="location" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true" />
        </div>
    </c:if>
    <div class="field">
        <label for="x">X:</label>
        <form:input path="x" type="number" step="any" required="true"/>
        <form:errors path="x" cssClass="error" />
    </div>
    <div class="field">
        <label for="y">Y:</label>
        <form:input path="y" type="number" step="any"/>
        <form:errors path="y" cssClass="error" />
    </div>
    <div class="field">
        <label for="z">Z:</label>
        <form:input path="z" type="number" step="any" required="true"/>
        <form:errors path="z" cssClass="error" />
    </div>
    <div class="field">
        <label for="name">Название:</label>
        <form:input path="name" type="text" required="true" maxlength="256"/>
        <form:errors path="name" cssClass="error" />
    </div>
    <button type="submit">Сохранить</button>
</form:form>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/locations/delete/${editId}" style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить локацию?');">Удалить</button>
    </form>
</c:if>
</body>
</html>
