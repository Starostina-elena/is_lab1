document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("avgAgeBtn").onclick = function() {
        fetch("dragons/average_age")
            .then(res => res.json())
            .then(data => {
                document.getElementById("avgAgeResult").textContent =
                    "Средний возраст драконов: " + data.averageAge.toFixed(2) + " лет";
            });
    };
});
