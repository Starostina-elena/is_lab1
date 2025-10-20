let personTableState = {page: 0, sortKey: null, sortDir: null, filter: ''};
let dragonTableState = {page: 0, sortKey: null, sortDir: null, filter: ''};
let locationTableState = {page: 0, sortKey: null, sortDir: null, filter: ''};
let dragonCaveTableState = {page: 0};
let dragonHeadTableState = {page: 0};
let coordinatesTableState = {page: 0};
let stompClient = null;

function setupWebSocket() {
    try {
        const socket = new SockJS('ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function() {
            stompClient.subscribe('/topic/changes', function(message) {
                try {
                    const payload = JSON.parse(message.body);
                    if (payload) {
                        if (payload.reload === 'persons') {
                            loadPersons(personTableState.page, personTableState.sortKey, personTableState.sortDir, personTableState.filter);
                        } else if (payload.reload === 'dragons') {
                            loadDragons(dragonTableState.page, dragonTableState.sortKey, dragonTableState.sortDir, dragonTableState.filter);
                        } else if (payload.reload === 'locations') {
                            loadLocations(locationTableState.page, locationTableState.sortKey, locationTableState.sortDir, locationTableState.filter);
                        } else if (payload.reload === 'dragonCaves') {
                            loadDragonCaves(dragonCaveTableState.page);
                        } else if (payload.reload === 'dragonHeads') {
                            loadDragonHeads(dragonHeadTableState.page);
                        } else if (payload.reload === 'coordinates') {
                            loadCoordinates(coordinatesTableState.page);
                        }
                    }
                } catch (e) {
                    console.error('Invalid WS message', e, message.body);
                }
            });
        }, function(error) {
            console.warn('STOMP connect error, will not receive live updates', error);
        });
    } catch (e) {
        console.warn('WebSocket setup failed:', e);
    }
}

document.addEventListener("DOMContentLoaded", function() {
    loadPersons(0, null, null, "");
    loadDragons(0, null, null, "");
    loadLocations(0, null, null, "");
    loadDragonCaves(0);
    loadDragonHeads(0);
    loadCoordinates(0);
    setupPersonTableControls();
    setupDragonTableControls();
    setupLocationTableControls();
    setupWebSocket();
});

function formatDate(timestamp) {
    const date = new Date(timestamp * 1000);
    return date.toISOString().replace('T', ' ').substring(0, 19);
}

function loadPersons(page, sortKey, sortDir, filter) {
    personTableState = {page, sortKey, sortDir, filter};
    const sortParam = sortKey ? `&sort=${sortKey}` : '';
    const dirParam = sortKey && sortDir ? `&dir=${sortDir}` : '';
    const filterParam = filter ? `&filter=${encodeURIComponent(filter)}` : '';
    fetch(`persons/get/page?page=${page}${sortParam}${dirParam}${filterParam}`, {'credentials': 'omit'})
        .then(res => res.json())
        .then(data => {
            renderPersonTable(data.persons);
            renderPersonPagination(data.pageCount, page, sortKey, sortDir, filter);
        });
}

function loadDragons(page, sortKey, sortDir, filter) {
    dragonTableState = {page, sortKey, sortDir, filter};
    const sortParam = sortKey ? `&sort=${sortKey}` : '';
    const dirParam = sortKey && sortDir ? `&dir=${sortDir}` : '';
    const filterParam = filter ? `&filter=${encodeURIComponent(filter)}` : '';
    fetch(`dragons/get/page?page=${page}${sortParam}${dirParam}${filterParam}`, {'credentials': 'omit'})
        .then(res => res.json())
        .then(data => {
            renderDragonTable(data.dragons);
            renderDragonPagination(data.pageCount, page, sortKey, sortDir, filter);
        });
}

function loadLocations(page, sortKey, sortDir, filter) {
    locationTableState = {page, sortKey, sortDir, filter};
    const sortParam = sortKey ? `&sort=${sortKey}` : '';
    const dirParam = sortKey && sortDir ? `&dir=${sortDir}` : '';
    const filterParam = filter ? `&filter=${encodeURIComponent(filter)}` : '';
    fetch(`locations/get/page?page=${page}${sortParam}${dirParam}${filterParam}`, {'credentials': 'omit'})
        .then(res => res.json())
        .then(data => {
            renderLocationTable(data.locations);
            renderLocationPagination(data.pageCount, page, sortKey, sortDir, filter);
        });
}

function loadDragonCaves(page) {
    dragonCaveTableState.page = page;
    fetch(`dragon/caves/get/page?page=${page}`, {'credentials': 'omit'})
        .then(res => res.json())
        .then(data => {
            renderDragonCaveTable(data.dragonCaves);
            renderDragonCavePagination(data.pageCount, page);
        });
}

function loadDragonHeads(page) {
    dragonHeadTableState.page = page;
    fetch(`dragon/heads/get/page?page=${page}`, {'credentials': 'omit'})
        .then(res => res.json())
        .then(data => {
            renderDragonHeadTable(data.dragonHeads);
            renderDragonHeadPagination(data.pageCount, page);
        });
}

function loadCoordinates(page) {
    coordinatesTableState.page = page;
    fetch(`coordinates/get/page?page=${page}`, {'credentials': 'omit'})
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

function renderDragonPagination(totalPages, currentPage, sortKey, sortDir, filter) {
    const container = document.getElementById("dragonPagination");
    container.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i + 1;
        btn.disabled = i === currentPage;
        btn.onclick = () => loadDragons(i, sortKey, sortDir, filter);
        container.appendChild(btn);
    }
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

function renderLocationPagination(totalPages, currentPage, sortKey, sortDir, filter) {
    const container = document.getElementById("locationPagination");
    container.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i + 1;
        btn.disabled = i === currentPage;
        btn.onclick = () => loadLocations(i, sortKey, sortDir, filter);
        container.appendChild(btn);
    }
}

function renderDragonCaveTable(dragonCaves) {
    const table = document.getElementById("dragonCaveTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    dragonCaves.forEach(dc => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="dragon/caves/update/${dc.id}" target="_blank">${dc.id}</a>`;
        row.insertCell().textContent = dc.depth;
    });
}

function renderDragonCavePagination(totalPages, currentPage) {
    const container = document.getElementById("dragonCavePagination");
    renderPagination(container, totalPages, currentPage, (i) => loadDragonCaves(i));
}

function renderDragonHeadTable(dragonHeads) {
    const table = document.getElementById("dragonHeadTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    dragonHeads.forEach(dh => {
        const row = table.insertRow();
        row.insertCell().innerHTML = `<a href="dragon/heads/update/${dh.id}" target="_blank">${dh.id}</a>`;
        row.insertCell().textContent = dh.eyesCount;
    });
}

function renderDragonHeadPagination(totalPages, currentPage) {
    const container = document.getElementById("dragonHeadPagination");
    renderPagination(container, totalPages, currentPage, (i) => loadDragonHeads(i));
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
    renderPagination(container, totalPages, currentPage, (i) => loadCoordinates(i));
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

function setupPersonTableControls() {
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

function setupDragonTableControls() {
    const dragonFilter = document.getElementById('dragonFilter');
    const dragonSort = document.getElementById('dragonSort');
    const dragonSortDir = document.getElementById('dragonSortDir');

    if (dragonFilter) dragonFilter.addEventListener('input', () => {
        const filter = dragonFilter.value;
        loadDragons(0, dragonSort ? dragonSort.value : null, dragonSortDir ? dragonSortDir.value : null, filter);
    });

    if (dragonSort) dragonSort.addEventListener('change', () => {
        const sortKey = dragonSort.value;
        const sortDir = dragonSortDir ? dragonSortDir.value : 'asc';
        const filter = dragonFilter ? dragonFilter.value : '';
        loadDragons(0, sortKey, sortDir, filter);
    });

    if (dragonSortDir) dragonSortDir.addEventListener('change', () => {
        const sortKey = dragonSort ? dragonSort.value : null;
        const sortDir = dragonSortDir.value;
        const filter = dragonFilter ? dragonFilter.value : '';
        loadDragons(0, sortKey, sortDir, filter);
    });
}

function setupLocationTableControls() {
    const locationFilter = document.getElementById('locationFilter');
    const locationSort = document.getElementById('locationSort');
    const locationSortDir = document.getElementById('locationSortDir');

    if (locationFilter) locationFilter.addEventListener('input', () => {
        const filter = locationFilter.value;
        loadLocations(0, locationSort ? locationSort.value : null, locationSortDir ? locationSortDir.value : null, filter);
    });

    if (locationSort) locationSort.addEventListener('change', () => {
        const sortKey = locationSort.value;
        const sortDir = locationSortDir ? locationSortDir.value : 'asc';
        const filter = locationFilter ? locationFilter.value : '';
        loadLocations(0, sortKey, sortDir, filter);
    });

    if (locationSortDir) locationSortDir.addEventListener('change', () => {
        const sortKey = locationSort ? locationSort.value : null;
        const sortDir = locationSortDir.value;
        const filter = locationFilter ? locationFilter.value : '';
        loadLocations(0, sortKey, sortDir, filter);
    });
}
