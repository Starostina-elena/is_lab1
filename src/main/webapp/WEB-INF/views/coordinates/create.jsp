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

<c:if test="${editId != null}">
    <c:if test="${not empty dragonsWithCoordinates}">
            <h3>Драконы с этими координатами:</h3>
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
                <c:forEach var="dragon" items="${dragonsWithCoordinates}">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/dragons/update/${dragon.id}" target="_blank">${dragon.id}</a></td>
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
    document.addEventListener("DOMContentLoaded", function() {
        span = document.getElementById('dragon-date');
        if (span) {
            var dateStr = span.textContent.trim();
            span.textContent = dateStr.replace('T', ' ').substring(0, 19);
        }
    });
</script>
</body>
</html>
