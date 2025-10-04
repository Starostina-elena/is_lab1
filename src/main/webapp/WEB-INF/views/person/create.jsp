<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Персонаж</title>
</head>
<body>
<h2><c:choose><c:when test="${editId != null}">Редактировать персонажа</c:when><c:otherwise>Создать персонажа</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/persons/create" />
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/persons/update/${editId}" />
</c:if>
<form:form modelAttribute="person" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true" />
        </div>
    </c:if>
    <div class="field">
        <label for="name">Имя:</label>
        <form:input path="name" type="text" required="true" maxlength="256"/>
        <form:errors path="name" cssClass="error" />
    </div>
    <div class="field">
        <label for="eyeColor">Цвет глаз:</label>
        <form:select path="eyeColor">
            <option value="">-</option>
            <form:options items="${colorList}" />
        </form:select>
        <form:errors path="eyeColor" cssClass="error" />
    </div>
    <div class="field">
        <label for="hairColor">Цвет волос:</label>
        <form:select path="hairColor">
            <option value="">-</option>
            <form:options items="${colorList}" />
        </form:select>
        <form:errors path="hairColor" cssClass="error" />
    </div>
    <div class="field">
        <label for="weight">Вес:</label>
        <form:input path="weight" type="number" step="1"/>
        <form:errors path="weight" cssClass="error" />
    </div>
    <div class="field">
        <label for="nationality">Национальность:</label>
        <form:select path="nationality">
            <option value="">-</option>
            <form:options items="${nationalityList}" />
        </form:select>
        <form:errors path="nationality" cssClass="error" />
    </div>
    <div class="field">
        <label for="location">Локация:</label>
        <form:select path="location">
            <c:forEach var="loc" items="${locationList}">
                <option value="${loc.id}">${loc.id} - ${loc.name}</option>
            </c:forEach>
        </form:select>
        <form:errors path="location" cssClass="error" />
    </div>
    <button type="submit">Сохранить</button>
</form:form>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/persons/delete/${editId}" style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить персонажа?');">Удалить</button>
    </form>
</c:if>
</body>
</html>
