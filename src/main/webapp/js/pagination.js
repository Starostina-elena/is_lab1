document.addEventListener("DOMContentLoaded", function() {
    loadPersons(0);
    loadDragons(0);
    loadLocations(0);
    loadDragonCaves(0);
    loadDragonHeads(0);
    loadCoordinates(0);
});

function formatDate(timestamp) {
    const date = new Date(timestamp * 1000);
    return date.toISOString().replace('T', ' ').substring(0, 19);
}

function loadPersons(page) {
    fetch(`persons/get_page?page=${page}`)
        .then(res => res.json())
        .then(data => {
            renderPersonTable(data.persons);
            renderPersonPagination(data.pageCount, page);
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

function renderPersonTable(persons) {
    const table = document.getElementById("personTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    persons.forEach(p => {
        const row = table.insertRow();
        row.insertCell().textContent = p.id;
        row.insertCell().textContent = p.name;
        row.insertCell().textContent = p.eyeColor;
        row.insertCell().textContent = p.hairColor;
        row.insertCell().textContent = p.location.name;
        row.insertCell().textContent = p.weight;
        row.insertCell().textContent = p.nationality;
    });
}


function renderPersonPagination(totalPages, currentPage) {
    const container = document.getElementById("personPagination");
    renderPagination(container, totalPages, currentPage, loadPersons);
}

function renderDragonTable(dragons) {
    const table = document.getElementById("dragonTable");
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }
    dragons.forEach(d => {
        const row = table.insertRow();
        row.insertCell().textContent = d.id;
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
        row.insertCell().textContent = l.id;
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
