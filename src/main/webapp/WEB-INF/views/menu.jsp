<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Меню</title>
</head>
<body>
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

<script src="${pageContext.request.contextPath}/js/top_panel.js"></script>
</body>
</html>
