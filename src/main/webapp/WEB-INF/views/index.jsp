<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lab1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/menu" class="a-button">Меню</a>
</header>
<h2>Персонажи <a href="${pageContext.request.contextPath}/persons/create" target="_blank" class="a-button">+</a></h2>
<div class="container">
    <div class="filter-sort-container">
        <label for="personFilter">Фильтр по имени:</label>
        <input type="text" id="personFilter" placeholder="Введите имя персонажа">
        <label for="personSort">Сортировка:</label>
        <select id="personSort">
            <option value="">Без сортировки</option>
            <option value="name">Имя</option>
        </select>
        <label for="personSortDir">Направление:</label>
        <select id="personSortDir">
            <option value="asc">Возрастание</option>
            <option value="desc">Убывание</option>
        </select>
    </div>
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
    <div id="personPagination" class="pagination"></div>
</div>

<h2>Драконы <a href="${pageContext.request.contextPath}/dragons/create" target="_blank" class="a-button">+</a></h2>
<div class="container">
    <div class="filter-sort-container">
        <label for="dragonFilter">Фильтр по имени:</label>
        <input type="text" id="dragonFilter" placeholder="Введите имя дракона">
        <label for="dragonSort">Сортировка:</label>
        <select id="dragonSort">
            <option value="">Без сортировки</option>
            <option value="name">Имя</option>
        </select>
        <label for="dragonSortDir">Направление:</label>
        <select id="dragonSortDir">
            <option value="asc">Возрастание</option>
            <option value="desc">Убывание</option>
        </select>
    </div>

    <table id="dragonTable">
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Координаты</th>
            <th>Дата создания</th>
            <th>Глубина пещеры</th>
            <th>Убийца</th>
            <th>Возраст</th>
            <th>Цвет</th>
            <th>Тип</th>
            <th>Характер</th>
            <th>Глаз на голове</th>
        </tr>
    </table>
    <div id="dragonPagination" class="pagination"></div>
</div>

<h2>Локации <a href="${pageContext.request.contextPath}/locations/create" target="_blank" class="a-button">+</a></h2>
<div class="container">
    <div class="filter-sort-container">
        <label for="locationFilter">Фильтр по названию:</label>
        <input type="text" id="locationFilter" placeholder="Введите название локации">
        <label for="locationSort">Сортировка:</label>
        <select id="locationSort">
            <option value="">Без сортировки</option>
            <option value="name">Название</option>
        </select>
    </div>
    <table id="locationTable">
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>X</th>
            <th>Y</th>
            <th>Z</th>
        </tr>
    </table>
    <div id="locationPagination" class="pagination"></div>
</div>

<h2>Пещеры драконов <a href="${pageContext.request.contextPath}/dragon/caves/create" target="_blank"
                       class="a-button">+</a></h2>
<div class="container">
    <table id="dragonCaveTable">
        <tr>
            <th>ID</th>
            <th>Глубина</th>
        </tr>
    </table>
    <div id="dragonCavePagination" class="pagination"></div>
</div>

<h2>Головы драконов <a href="${pageContext.request.contextPath}/dragon/heads/create" target="_blank"
                       class="a-button">+</a></h2>
<div class="container">
    <table id="dragonHeadTable">
        <tr>
            <th>ID</th>
            <th>Количество глаз</th>
        </tr>
    </table>
    <div id="dragonHeadPagination" class="pagination"></div>
</div>

<h2>Координаты <a href="${pageContext.request.contextPath}/coordinates/create" target="_blank" class="a-button">+</a>
</h2>
<div class="container">
    <table id="coordinatesTable">
        <tr>
            <th>ID</th>
            <th>X</th>
            <th>Y</th>
        </tr>
    </table>
    <div id="coordinatesPagination" class="pagination"></div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

</body>
</html>
