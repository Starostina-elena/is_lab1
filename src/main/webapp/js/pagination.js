document.addEventListener("DOMContentLoaded", function() {
    loadPersons(0, null, null, "");
    loadDragons(0);
    loadLocations(0);
    loadDragonCaves(0);
    loadDragonHeads(0);
    loadCoordinates(0);
    setupTableControls();
});

function formatDate(timestamp) {
    const date = new Date(timestamp * 1000);
    return date.toISOString().replace('T', ' ').substring(0, 19);
}

function loadPersons(page, sortKey, sortDir, filter) {
    const sortParam = sortKey ? `&sort=${sortKey}` : '';
    const dirParam = sortKey && sortDir ? `&dir=${sortDir}` : '';
    const filterParam = filter ? `&filter=${encodeURIComponent(filter)}` : '';
    fetch(`persons/get_page?page=${page}${sortParam}${dirParam}${filterParam}`)
        .then(res => res.json())
        .then(data => {
            renderPersonTable(data.persons);
            renderPersonPagination(data.pageCount, page, sortKey, sortDir, filter);
        });
}

function loadDragons(page) {
    fetch(`dragons/get_page?page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderDragonTable(data.dragons);
            renderDragonPagination(data.pageCount, page);
        });
}

function loadLocations(page) {
    fetch(`locations/get_page?page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderLocationTable(data.locations);
            renderLocationPagination(data.pageCount, page);
        });
}

function loadDragonCaves(page) {
    fetch(`dragon_caves/get_page?page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderDragonCaveTable(data.dragonCaves);
            renderDragonCavePagination(data.pageCount, page);
        });
}

function loadDragonHeads(page) {
    fetch(`dragon_heads/get_page?page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderDragonHeadTable(data.dragonHeads);
            renderDragonHeadPagination(data.pageCount, page);
        });
}

function loadCoordinates(page) {
    fetch(`coordinates/get_page?page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderCoordinatesTable(data.coordinates);
            renderCoordinatesPagination(data.pageCount, page);
        });
}

function renderPersonTable(persons) {
    const table = document.getElementById("personTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    persons.forEach(person => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="persons/update/${person.id}" target="_blank">${person.id}</a>`;
        row.insertCell().textContent = person.name;
        row.insertCell().textContent = person.eyeColor;
        row.insertCell().textContent = person.hairColor;
        row.insertCell().textContent = person.location ? person.location.name : '-';
        row.insertCell().textContent = person.weight;
        row.insertCell().textContent = person.nationality;
    });
}

function renderPersonPagination(totalPages, currentPage, sortKey, sortDir, filter) {
    const container = document.getElementById("personPagination");
    container.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i + 1;
        btn.disabled = i === currentPage;
        btn.onclick = () => loadPersons(i, sortKey, sortDir, filter);
        container.appendChild(btn);
    }
}

function renderDragonTable(dragons) {
    const table = document.getElementById("dragonTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    dragons.forEach(d => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="dragons/update/${d.id}" target="_blank">${d.id}</a>`;
        row.insertCell().textContent = d.name;
        row.insertCell().textContent = d.coordinates.x + ", " + d.coordinates.y;
        row.insertCell().textContent = formatDate(d.creationDate);
        row.insertCell().textContent = d.cave.depth;
        if (d.killer != null) {
            row.insertCell().textContent = d.killer.name;
        } else {
            row.insertCell().textContent = "-";
        }
        row.insertCell().textContent = d.age;
        row.insertCell().textContent = d.color;
        row.insertCell().textContent = d.type;
        row.insertCell().textContent = d.character;
        row.insertCell().textContent = d.head.eyesCount;
    });
}

function renderDragonPagination(totalPages, currentPage) {
    const container = document.getElementById("dragonPagination");
    renderPagination(container, totalPages, currentPage, loadDragons);
}

function renderLocationTable(locations) {
    const table = document.getElementById("locationTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    locations.forEach(l => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="locations/update/${l.id}" target="_blank">${l.id}</a>`;
        row.insertCell().textContent = l.name;
        row.insertCell().textContent = l.x;
        row.insertCell().textContent = l.y;
        row.insertCell().textContent = l.z;
    });
}

function renderLocationPagination(totalPages, currentPage) {
    const container = document.getElementById("locationPagination");
    renderPagination(container, totalPages, currentPage, loadLocations);
}

function renderDragonCaveTable(dragonCaves) {
    const table = document.getElementById("dragonCaveTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    dragonCaves.forEach(dc => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="dragon_caves/update/${dc.id}" target="_blank">${dc.id}</a>`;
        row.insertCell().textContent = dc.depth;
    });
}

function renderDragonCavePagination(totalPages, currentPage) {
    const container = document.getElementById("dragonCavePagination");
    renderPagination(container, totalPages, currentPage, loadDragonCaves);
}

function renderDragonHeadTable(dragonHeads) {
    const table = document.getElementById("dragonHeadTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    dragonHeads.forEach(dh => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="dragon_heads/update/${dh.id}" target="_blank">${dh.id}</a>`;
        row.insertCell().textContent = dh.eyesCount;
    });
}

function renderDragonHeadPagination(totalPages, currentPage) {
    const container = document.getElementById("dragonHeadPagination");
    renderPagination(container, totalPages, currentPage, loadDragonHeads);
}

function renderCoordinatesTable(coordinates) {
    const table = document.getElementById("coordinatesTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    coordinates.forEach(c => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="coordinates/update/${c.id}" target="_blank">${c.id}</a>`;
        row.insertCell().textContent = c.x;
        row.insertCell().textContent = c.y;
    });
}

function renderCoordinatesPagination(totalPages, currentPage) {
    const container = document.getElementById("coordinatesPagination");
    renderPagination(container, totalPages, currentPage, loadCoordinates);
}

function renderPagination(container, totalPages, currentPage, loadFunction) {
    container.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i + 1;
        btn.disabled = i === currentPage;
        btn.onclick = () => loadFunction(i);
        container.appendChild(btn);
    }
}

function setupTableControls() {
    const personFilter = document.getElementById('personFilter');
    const personSort = document.getElementById('personSort');
    const personSortDir = document.getElementById('personSortDir');

    if (personFilter) personFilter.addEventListener('input', () => {
        const filter = personFilter.value;
        loadPersons(0, personSort ? personSort.value : null, personSortDir ? personSortDir.value : null, filter);
    });

    if (personSort) personSort.addEventListener('change', () => {
        const sortKey = personSort.value;
        const sortDir = personSortDir ? personSortDir.value : 'asc';
        const filter = personFilter ? personFilter.value : '';
        loadPersons(0, sortKey, sortDir, filter);
    });

    if (personSortDir) personSortDir.addEventListener('change', () => {
        const sortKey = personSort ? personSort.value : null;
        const sortDir = personSortDir.value;
        const filter = personFilter ? personFilter.value : '';
        loadPersons(0, sortKey, sortDir, filter);
    });
}
