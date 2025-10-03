<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lab1</title>
</head>
<body>
<h2>Люди</h2>
<table id="personTable">
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Цвет глаз</th>
        <th>Цвет волос</th>
        <th>Локация</th>
        <th>Вес</th>
        <th>Национальность</th>
    </tr>
</table>
<div id="personPagination"></div>

<h2>Драконы</h2>
<table id="dragonTable">
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
</table>
<div id="dragonPagination"></div>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

</body>
</html>
