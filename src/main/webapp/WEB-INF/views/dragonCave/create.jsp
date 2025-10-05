<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Пещера дракона</title>
</head>
<body>
<h2><c:choose><c:when
        test="${editId != null}">Редактировать пещеру дракона</c:when><c:otherwise>Создать пещеру дракона</c:otherwise></c:choose></h2>

<c:set var="formAction" value="${pageContext.request.contextPath}/dragon_caves/create"/>
<c:if test="${editId != null}">
    <c:set var="formAction" value="${pageContext.request.contextPath}/dragon_caves/update/${editId}"/>
</c:if>
<form:form modelAttribute="dragonCave" method="post" action="${formAction}">
    <c:if test="${editId != null}">
        <div class="field">
            <label for="id">ID:</label>
            <input type="text" value="${editId}" readonly="true"/>
        </div>
    </c:if>
    <div class="field">
        <label for="depth">Глубина пещеры:</label>
        <form:input path="depth" type="number" step="1"/>
        <form:errors path="depth" cssClass="error"/>
    </div>
    <button type="submit">Сохранить</button>
</form:form>

<c:if test="${editId != null}">
    <form method="post" action="${pageContext.request.contextPath}/dragon_caves/delete/${editId}"
          style="margin-top:10px;">
        <button type="submit" onclick="return confirm('Удалить пещеру дракона?');">Удалить</button>
    </form>
</c:if>

<c:if test="${not empty deleteError}">
    <div class="error" style="margin-bottom:10px;">${deleteError}</div>
</c:if>

<c:if test="${editId != null}">
    <c:if test="${not empty dragonsWithCave}">
        <h3>Драконы, живущие в этой пещере:</h3>
        <table>
            <tr>
                <th>ID</th>
                <th>Имя</th>
                <th>Координаты</th>
                <th>Дата создания</th>
                <th>Пещера</th>
                <th>Убийца</th>
                <th>Возраст</th>
                <th>Цвет</th>
                <th>Тип</th>
                <th>Характер</th>
                <th>Голова</th>
            </tr>
            <c:forEach var="dragon" items="${dragonsWithCave}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/dragons/update/${dragon.id}"
                           target="_blank">${dragon.id}</a></td>
                    <td>${dragon.name}</td>
                    <td>${dragon.coordinates.id}</td>
                    <td id="dragon-date">${dragon.creationDate}</td>
                    <td>${dragon.cave.id}</td>
                    <td>${dragon.killer.name}</td>
                    <td>${dragon.age}</td>
                    <td>${dragon.color}</td>
                    <td>${dragon.type}</td>
                    <td>${dragon.character}</td>
                    <td>${dragon.head.id}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</c:if>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        span = document.getElementById('dragon-date');
        if (span) {
            var dateStr = span.textContent.trim();
            span.textContent = dateStr.replace('T', ' ').substring(0, 19);
        }
    });
</script>
</body>
</html>
