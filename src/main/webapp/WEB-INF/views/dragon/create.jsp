<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Дракон</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<h2><c:choose><c:when test="${editId != null}">Редактировать дракона</c:when><c:otherwise>Создать дракона</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/dragons/create" />
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/dragons/update/${editId}" />
</c:if>
<form:form modelAttribute="dragon" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true" />
        </div>
    </c:if>
    <div class="field">
        <label for="name">Имя:</label>
        <form:input path="name" type="text" required="true" maxlength="250"/>
        <form:errors path="name" cssClass="error" />
    </div>
    <div class="field">
        <label for="coordinates">Координаты:</label>
        <form:select path="coordinates">
            <c:forEach var="cor" items="${coordinatesList}">
                <option value="${cor.id}" <c:if test="${cor.id == dragon.coordinates.id}">selected</c:if>>${cor.id} - (${cor.x}, ${cor.y})</option>
            </c:forEach>
        </form:select>
        <form:errors path="coordinates" cssClass="error" />
    </div>
    <div class="field">
        <label for="cave">Пещера дракона:</label>
        <form:select path="cave">
            <c:forEach var="cv" items="${dragonCaveList}">
                <option value="${cv.id}" <c:if test="${cv.id == dragon.cave.id}">selected</c:if>>${cv.id} - ${cv.depth}</option>
            </c:forEach>
        </form:select>
        <form:errors path="cave" cssClass="error" />
    </div>
    <div class="field">
        <label for="killer">Убийца:</label>
        <form:select path="killer">
            <option value="">-</option>
            <c:forEach var="kil" items="${personList}">
                <option value="${kil.id}" <c:if test="${kil.id == dragon.killer.id}">selected</c:if>>${kil.id} - ${kil.name}</option>
            </c:forEach>
        </form:select>
        <form:errors path="killer" cssClass="error" />
    </div>
    <div class="field">
        <label for="age">Возраст:</label>
        <form:input path="age" type="number" step="1"/>
        <form:errors path="age" cssClass="error" />
    </div>
    <div class="field">
        <label for="color">Цвет:</label>
        <form:select path="color">
            <option value="">-</option>
            <form:options items="${colorList}" />
        </form:select>
        <form:errors path="color" cssClass="error" />
    </div>
    <div class="field">
        <label for="type">Тип:</label>
        <form:select path="type">
            <form:options items="${typeList}" />
        </form:select>
        <form:errors path="type" cssClass="error" />
    </div>
    <div class="field">
        <label for="character">Характер:</label>
        <form:select path="character">
            <form:options items="${characterList}" />
        </form:select>
        <form:errors path="character" cssClass="error" />
    </div>
    <div class="field">
        <label for="head">Голова:</label>
        <form:select path="head">
            <option value="">-</option>
            <c:forEach var="dh" items="${dragonHeadList}">
                <option value="${dh.id}" <c:if test="${dh.id == dragon.head.id}">selected</c:if>>${dh.id} - ${dh.eyesCount}</option>
            </c:forEach>
        </form:select>
        <form:errors path="head" cssClass="error" />
    </div>
    <c:if test="${editId != null}">
        <div class="field">
            <label for="creationDate">Дата создания:</label>
            <span id="creationDate">${dragon.creationDate}</span>
        </div>
    </c:if>
    <button type="submit">Сохранить</button>
</form:form>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var span = document.getElementById("creationDate");
        if (span) {
            var dateStr = span.textContent.trim();
            span.textContent = dateStr.replace('T', ' ').substring(0, 19);
        }
    });
</script>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/dragons/delete/${editId}" style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить дракона?');">Удалить</button>
    </form>
</c:if>
</body>
</html>
