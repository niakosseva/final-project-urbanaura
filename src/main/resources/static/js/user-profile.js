document.addEventListener("DOMContentLoaded", function () {
    const updateButton = document.querySelector(".update-button");

    if (updateButton) {
        updateButton.addEventListener("click", updateProfile);
    }
});

// 🔹 Функция за update на username (firstName)
function updateProfile(event) {
    event.preventDefault();

    const newUsername = document.getElementById("input-username").value.trim();
    if (!newUsername) {
        showMessage("Username cannot be empty!", "error");
        return;
    }

    const updatedUser = {firstName: newUsername};

    fetch("/api/v1/users/update", {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        credentials: "include",
        body: JSON.stringify(updatedUser)
    })
        .then(response => response.json().catch(() => null))
        .then(data => {
            console.log("Server Response:", data);

            if (data && data.message) {

                showMessage(data.message, "success");
                document.getElementById("input-username").value = updatedUser.firstName;
            } else {
                showMessage("Username updated successfully!", "success");
            }
        })
        .catch(error => {
            console.error("Error updating username:", error);
            showMessage("Something went wrong. Please try again.", "error");
        });
}

// 🔹 Функция за показване на съобщения
function showMessage(message, type) {
    let messageContainer = document.getElementById("message-container");

    if (!messageContainer) {
        messageContainer = document.createElement("div");
        messageContainer.id = "message-container";
        document.body.prepend(messageContainer);
    }

    const messageBox = document.createElement("div");
    messageBox.className = `alert ${type === "success" ? "alert-success" : "alert-danger"}`;
    messageBox.textContent = message;

    messageContainer.appendChild(messageBox);

    // Автоматично премахване след 3 секунди
    setTimeout(() => {
        messageBox.remove();
    }, 3000);
}
