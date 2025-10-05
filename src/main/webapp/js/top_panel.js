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
});
