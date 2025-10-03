document.addEventListener("DOMContentLoaded", function() {
    loadPersons(0);
    loadDragons(0);
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
        row.insertCell().textContent = p.location;
        row.insertCell().textContent = p.weight;
        row.insertCell().textContent = p.nationality;
    });
}


function renderPersonPagination(totalPages, currentPage) {
    const container = document.getElementById("personPagination");
    container.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i + 1;
        btn.disabled = i === currentPage;
        btn.onclick = () => loadPersons(i);
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
        row.insertCell().textContent = d.id;
        row.insertCell().textContent = d.name;
        row.insertCell().textContent = d.coordinates;
        row.insertCell().textContent = formatDate(d.creationDate);
        row.insertCell().textContent = d.cave;
        row.insertCell().textContent = d.killer;
        row.insertCell().textContent = d.age;
        row.insertCell().textContent = d.color;
        row.insertCell().textContent = d.type;
        row.insertCell().textContent = d.character;
        row.insertCell().textContent = d.head;
    });
}

function renderDragonPagination(totalPages, currentPage) {
    const container = document.getElementById("dragonPagination");
    container.innerHTML = '';
    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i + 1;
        btn.disabled = i === currentPage;
        btn.onclick = () => loadDragons(i);
        container.appendChild(btn);
    }
}
