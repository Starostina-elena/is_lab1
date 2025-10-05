function formatDate(timestamp) {
    const date = new Date(timestamp * 1000);
    return date.toISOString().replace('T', ' ').substring(0, 19);
}

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("avgAgeBtn").onclick = function () {
        fetch("dragons/average_age")
            .then(res => res.json())
            .then(data => {
                document.getElementById("avgAgeResult").textContent =
                    "Средний возраст драконов: " + data.averageAge.toFixed(2) + " лет";
            });
    };

    document.getElementById("countHeadIdBtn").onclick = function () {
        const maxHeadId = document.getElementById("maxHeadIdInput").value;
        fetch(`dragons/count_head_less_than?maxId=${encodeURIComponent(maxHeadId)}`)
            .then(res => res.json())
            .then(data => {
                document.getElementById("countHeadIdResult").textContent =
                    "Количество драконов с head_id меньше, чем " + maxHeadId + ": " + data.count;
            });
    };

    document.getElementById("searchAllBtn").onclick = function () {
        const search = document.getElementById("searchSubstringInput").value;
        fetch(`dragons/search_by_name?search=${encodeURIComponent(search)}`)
            .then(res => res.json())
            .then(data => data.dragons)
            .then(dragons => {
                const container = document.getElementById("searchResultsDragon");
                let html = `<h3>Драконы</h3>
                    <table><tr>
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
                </tr>`;
                if (dragons.length === 0) {
                    html = `<h3>Драконы</h3><p>Нет совпадений<p>`;
                } else {
                    dragons.forEach(d => {
                        html += `<tr>` +
                            `<td><a href='dragons/update/${d.id}' target='_blank'>${d.id}</a></td>` +
                            `<td>${d.name}</td>` +
                            `<td>${d.coordinates ? d.coordinates.x + ', ' + d.coordinates.y : '-'}</td>` +
                            `<td>${formatDate(d.creationDate)}</td>` +
                            `<td>${d.cave ? d.cave.depth : '-'}</td>` +
                            `<td>${d.killer ? d.killer.name : '-'}</td>` +
                            `<td>${d.age}</td>` +
                            `<td>${d.color}</td>` +
                            `<td>${d.type}</td>` +
                            `<td>${d.character}</td>` +
                            `<td>${d.head ? d.head.id : '-'}</td>` +
                        `</tr>`;
                    });
                    html += `</table>`;
                }
                container.innerHTML = html;
            });
        fetch(`persons/search_by_name?search=${encodeURIComponent(search)}`)
            .then(res => res.json())
            .then(data => data.persons)
            .then(persons => {
                const container = document.getElementById("searchResultsPerson");
                let html = `<h3>Персонажи</h3>
                    <table><tr>
                    <th>ID</th>
                    <th>Имя</th>
                    <th>Вес</th>
                    <th>Цвет глаз</th>
                    <th>Цвет волос</th>
                    <th>Национальность</th>
                    <th>Локация</th>
                </tr>`;
                if (persons.length === 0) {
                    html = `<h3>Персонажи</h3><p>Нет совпадений<p>`;
                } else {
                    persons.forEach(p => {
                        html += `<tr>` +
                            `<td><a href='persons/update/${p.id}' target='_blank'>${p.id}</a></td>` +
                            `<td>${p.name}</td>` +
                            `<td>${p.weight}</td>` +
                            `<td>${p.eyeColor}</td>` +
                            `<td>${p.hairColor}</td>` +
                            `<td>${p.nationality}</td>` +
                            `<td>${p.location ? p.location.name : '-'}</td>` +
                        `</tr>`;
                    });
                    html += `</table>`;
                }
                container.innerHTML = html;
            });
        fetch(`locations/search_by_name?search=${encodeURIComponent(search)}`)
            .then(res => res.json())
            .then(data => data.locations)
            .then(locations => {
                const container = document.getElementById("searchResultsLocation");
                let html = `<h3>Локации</h3>
                    <table><tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>X</th>
                    <th>Y</th>
                    <th>Z</th>
                </tr>`;
                if (locations.length === 0) {
                    html = `<h3>Локации</h3><p>Нет совпадений<p>`;
                } else {
                    locations.forEach(l => {
                        html += `<tr>` +
                            `<td><a href='locations/update/${l.id}' target='_blank'>${l.id}</a></td>` +
                            `<td>${l.name}</td>` +
                            `<td>${l.x}</td>` +
                            `<td>${l.y}</td>` +
                            `<td>${l.z}</td>` +
                        `</tr>`;
                    });
                    html += `</table>`;
                }
                container.innerHTML = html;
            });
    };
});
