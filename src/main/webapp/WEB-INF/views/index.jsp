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
        <th>глубина пещеры</th>
        <th>Убийца</th>
        <th>Возраст</th>
        <th>Цвет</th>
        <th>Тип</th>
        <th>Характер</th>
        <th>Глаз на голове</th>
    </tr>
</table>
<div id="dragonPagination"></div>

<h2>Локации</h2>
<table id="locationTable">
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>X</th>
        <th>Y</th>
        <th>Z</th>
    </tr>
</table>
<div id="locationPagination"></div>

<h2>Пещеры драконов</h2>
<table id="dragonCaveTable">
    <tr>
        <th>ID</th>
        <th>Глубина</th>
    </tr>
</table>
<div id="dragonCavePagination"></div>

<h2>Головы драконов</h2>
<a href="${pageContext.request.contextPath}/dragon_heads/create" target="_blank">+</a>
<table id="dragonHeadTable">
    <tr>
        <th>ID</th>
        <th>Количество глаз</th>
    </tr>
</table>
<div id="dragonHeadPagination"></div>

<h2>Координаты</h2>
<a href="${pageContext.request.contextPath}/coordinates/create" target="_blank">+</a>
<table id="coordinatesTable">
    <tr>
        <th>ID</th>
        <th>X</th>
        <th>Y</th>
    </tr>
</table>
<div id="coordinatesPagination"></div>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

</body>
</html>
