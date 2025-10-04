<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Пещера дракона</title>
</head>
<body>
<h2><c:choose><c:when test="${editId != null}">Редактировать пещеру дракона</c:when><c:otherwise>Создать пещеру дракона</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/dragon_caves/create" />
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/dragon_caves/update/${editId}" />
</c:if>
<form:form modelAttribute="dragonCave" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true" />
        </div>
    </c:if>
    <div class="field">
        <label for="depth">Глубина пещеры:</label>
        <form:input path="depth" type="number" step="1"/>
        <form:errors path="depth" cssClass="error" />
    </div>
    <button type="submit">Сохранить</button>
</form:form>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/dragon_caves/delete/${editId}" style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить пещеру дракона?');">Удалить</button>
    </form>
</c:if>
</body>
</html>
