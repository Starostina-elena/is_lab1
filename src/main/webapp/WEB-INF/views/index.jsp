<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lab1</title>
</head>
<body>
<h2>Люди</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Цвет глаз</th>
        <th>Цвет волос</th>
        <th>Локация</th>
        <th>Вес</th>
        <th>Национальность</th>
    </tr>
    <c:forEach var="person" items="${personList}">
        <tr>
            <td>${person.id}</td>
            <td>${person.name}</td>
            <td>${person.eyeColor}</td>
            <td>${person.hairColor}</td>
            <td>${person.location}</td>
            <td>${person.weight}</td>
            <td>${person.nationality}</td>
        </tr>
    </c:forEach>
</table>
<div>
    <c:forEach begin="0" end="${peoplePages - 1}" var="i">
        <a href="?personPage=${i}&dragonPage=${dragonPage}">${i + 1}</a>
    </c:forEach>
</div>

<h2>Драконы</h2>
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
    <c:forEach var="dragon" items="${dragonList}">
        <tr>
            <td>${dragon.id}</td>
            <td>${dragon.name}</td>
            <td>${dragon.coordinates}</td>
            <td>${dragon.creationDate}</td>
            <td>${dragon.cave}</td>
            <td>${dragon.killer}</td>
            <td>${dragon.age}</td>
            <td>${dragon.color}</td>
            <td>${dragon.type}</td>
            <td>${dragon.character}</td>
            <td>${dragon.head}</td>
        </tr>
    </c:forEach>
</table>
<div>
    <c:forEach begin="0" end="${dragonsPages - 1}" var="i">
        <a href="?personPage=${personPage}&dragonPage=${i}">${i + 1}</a>
    </c:forEach>
</div>

</body>
</html>
