document.addEventListener("DOMContentLoaded", function () {
    fetchUserProfile();

    const updateButton = document.querySelector(".update-button");

    if (updateButton) {
        updateButton.addEventListener("click", updateProfile);
    }
});

// 🔹 Функция за вземане на текущите данни на потребителя
function fetchUserProfile() {
    fetch("/api/v1/users/me", { method: "GET", credentials: "include" })
        .then(response => response.json())
        .then(data => {
            if (data.success && data.data) {
                document.getElementById("input-username").value = data.data.firstName;
            } else {
                console.error("Error fetching user data:", data.message);
            }
        })
        .catch(error => console.error("Error fetching user data:", error));
}

// 🔹 Функция за update на username (firstName)
function updateProfile(event) {
    event.preventDefault();

    const updatedUser = {
        firstName: document.getElementById("input-username").value
    };

    fetch("/api/v1/users/update", { // ✅ Изпращаме заявката без userId
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(updatedUser)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showSuccessMessage("✔ Username updated successfully!");
                fetchUserProfile(); // 🔹 Обновяване на UI след промяна
            } else {
                showErrorMessage("❌ Error updating username: " + data.message);
            }
        })
        .catch(error => {
            showErrorMessage("❌ Error updating username. Please try again.");
            console.error("Error updating username:", error);
        });
}

// 🔹 Функция за показване на успешно съобщение
function showSuccessMessage(message) {
    const messageBox = document.createElement("div");
    messageBox.className = "alert alert-success";
    messageBox.textContent = message;

    document.body.appendChild(messageBox);

    setTimeout(() => {
        messageBox.remove();
    }, 3000); // Автоматично изчезване след 3 секунди
}

// 🔹 Функция за показване на грешка
function showErrorMessage(message) {
    const messageBox = document.createElement("div");
    messageBox.className = "alert alert-danger";
    messageBox.textContent = message;

    document.body.appendChild(messageBox);

    setTimeout(() => {
        messageBox.remove();
    }, 3000);
}
