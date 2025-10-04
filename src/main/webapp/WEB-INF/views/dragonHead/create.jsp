<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Голова дракона</title>
</head>
<body>
<h2><c:choose><c:when test="${editId != null}">Редактировать голову дракона</c:when><c:otherwise>Создать голову дракона</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/dragon_heads/create" />
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/dragon_heads/update/${editId}" />
</c:if>
<form:form modelAttribute="dragonHead" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true" />
        </div>
    </c:if>
    <div class="field">
        <label for="eyesCount">Количество глаз:</label>
        <form:input path="eyesCount" type="number" step="1" required="true"/>
        <form:errors path="eyesCount" cssClass="error" />
    </div>
    <button type="submit">Сохранить</button>
</form:form>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/dragon_heads/delete/${editId}" style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить голову дракона?');">Удалить</button>
    </form>
</c:if>
</body>
</html>
