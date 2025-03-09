console.log("✅ Wishlist JavaScript Loaded!");

// 🔹 Функция за извличане на JWT токена
function fetchJwtToken() {
    return fetch("http://localhost:8081/api/v1/auth/user/token", {
        method: "GET",
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("❌ Failed to fetch JWT token!");
            }
            return response.json();
        })
        .then(data => {
            if (!data.jwt) {
                throw new Error("❌ JWT token is missing in response!");
            }
            return data.jwt;
        })
        .catch(error => {
            console.error("⚠️ Error fetching JWT token:", error);
            return null;
        });
}

// 🔹 Функция за добавяне на продукт в Wishlist
function addToWishlist(itemId, unitPrice, name, token) {
    if (!itemId || !name || !token) {
        console.error("❌ Missing itemId, name, or JWT token!");
        alert("⚠️ You need to be logged in to add items to the wishlist!");
        return;
    }

    const requestData = {
        itemId: parseInt(itemId),
        unitPrice: parseFloat(unitPrice),
        name: name  // ✅ Изпращаме `name`
    };

    console.log("📢 Sending request to Wishlist:", JSON.stringify(requestData));

    fetch("http://localhost:8082/api/v1/wishlist/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(requestData),
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    console.error("❌ Server error:", errorData);
                    alert("⚠️ Error adding item to wishlist!");
                });
            } else {
                alert("✅ Item added to wishlist!");
            }
        })
        .catch(error => {
            console.error("⚠️ Error sending request:", error);
            alert("⚠️ Something went wrong!");
        });
}

// 🔹 Свързване на бутони със събитието за Wishlist
document.addEventListener("DOMContentLoaded", function () {
    fetchJwtToken().then(token => {
        if (token) {
            document.querySelectorAll(".wishlist-btn").forEach(button => {
                button.addEventListener("click", function () {
                    const itemId = this.dataset.itemId;
                    const unitPrice = this.dataset.unitPrice;
                    const itemName = this.dataset.itemName;

                    console.log("🔍 Adding item:", itemId, "Price:", unitPrice, "Name:", itemName);

                    addToWishlist(itemId, unitPrice, itemName, token);
                });
            });
        }
    });
});
