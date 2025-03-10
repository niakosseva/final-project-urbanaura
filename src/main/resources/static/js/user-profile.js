document.addEventListener("DOMContentLoaded", function () {
    fetchUserProfile();
});

// 🔹 Функция за зареждане на потребителските данни
function fetchUserProfile() {
    fetch("/api/v1/users/me/firstName", { method: "GET", credentials: "include" })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById("username").innerText = data.data;
                document.getElementById("email").innerText = data.email;
                document.getElementById("input-username").value = data.data;
                document.getElementById("input-email").value = data.email;
            }
        })
        .catch(error => console.error("Error fetching user data:", error));
}

// 🔹 Функция за ъпдейт на потребителския профил
document.getElementById("update-profile-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const updatedUser = {
        username: document.getElementById("input-username").value,
        email: document.getElementById("input-email").value
    };

    fetch("/api/v1/users/update", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(updatedUser)
    })
        .then(response => response.json())
        .then(data => {
            alert("Profile updated successfully!");
            fetchUserProfile(); // 🔹 Обновяване на UI след промяна
        })
        .catch(error => console.error("Error updating profile:", error));
});

// 🔹 Функция за смяна на парола
document.getElementById("change-password-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const passwordData = {
        currentPassword: document.getElementById("current-password").value,
        newPassword: document.getElementById("new-password").value
    };

    fetch("/api/v1/users/change-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(passwordData)
    })
        .then(response => response.json())
        .then(data => {
            alert("Password changed successfully!");
        })
        .catch(error => console.error("Error changing password:", error));
});

// 🔹 Функция за изтриване на акаунт
document.getElementById("delete-account-form").addEventListener("submit", function (event) {
    event.preventDefault();

    if (!confirm("Are you sure you want to delete your account? This action cannot be undone!")) {
        return;
    }

    fetch("/api/v1/users/delete", {
        method: "DELETE",
        credentials: "include"
    })
        .then(response => {
            if (response.ok) {
                alert("Account deleted successfully!");
                window.location.href = "/logout";
            } else {
                alert("Error deleting account.");
            }
        })
        .catch(error => console.error("Error deleting account:", error));
});
