<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Меню</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/" class="a-button">На главную</a>
</header>
<div>
    <button id="avgAgeBtn">Средний возраст</button>
    <span id="avgAgeResult"></span>
</div>
<div>
    <input type="number" id="maxHeadIdInput" placeholder="Наибольший head_id">
    <button id="countHeadIdBtn">Посчитать драконов с head, меньше заданного</button>
    <span id="countHeadIdResult"></span>
</div>
<div>
    <input type="text" id="searchSubstringInput" placeholder="Найти по имени">
    <button id="searchAllBtn">Найти объекты</button>
    <div id="searchResultsDragon"></div>
    <div id="searchResultsPerson"></div>
    <div id="searchResultsLocation"></div>
</div>
<div>
    <h3>Убить дракона</h3>
    <form id="killDragonForm">
        <label for="killDragonSelect">Дракон:</label>
        <select id="killDragonSelect"></select>
        <label for="killerSelect">Убийца:</label>
        <select id="killerSelect"></select>
        <button type="submit">Убить</button>
        <span id="killDragonResult"></span>
    </form>
</div>
<div>
    <h3>Отправить команду персонажей в локацию</h3>
    <form id="sendTeamForm">
        <label for="teamSelect">Выберите персонажей:</label>
        <select id="teamSelect" multiple></select>
        <label for="locationSelect">Выберите локацию:</label>
        <select id="locationSelect"></select>
        <button type="submit">Отправить</button>
        <span id="sendTeamResult"></span>
    </form>
</div>


<script src="${pageContext.request.contextPath}/js/top_panel.js"></script>
</body>
</html>
