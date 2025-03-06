document.addEventListener("DOMContentLoaded", function () {
    // Fetch the user's authentication status
    fetch("http://localhost:8081/api/v1/auth/user/status", {
        method: "GET",
        credentials: "include"
    })
        .then(response => response.json())
        .then(data => {
            if (data.authenticated) {
                console.log("User is authenticated.");

                attachWishlistEventListeners();
            } else {
                console.error("User is not logged in.");
            }
        })
        .catch(error => console.error("Error fetching user status:", error));
});

function attachWishlistEventListeners() {
    document.querySelectorAll(".wishlist-btn").forEach(button => {
        button.addEventListener("click", function () {
            const itemId = this.dataset.itemId;

            // Retrieve the JWT from cookies
            const token = document.cookie.replace(/(?:(?:^|.*;\s*)jwt\s*\=\s*([^;]*).*$)|^.*$/, "$1");

            if (!itemId || !token) {
                console.error("Error: itemId or JWT token is missing!");
                return;
            }

            const requestData = {
                itemId: parseInt(itemId)
            };

            console.log("Sending request:", JSON.stringify(requestData));

            fetch("http://localhost:8082/api/v1/wishlist", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(requestData)
            })
                .then(response => {
                    if (response.ok) {
                        alert("Wishlist updated!");
                    } else {
                        return response.json().then(errorData => {
                            console.error("Server error:", errorData);
                        });
                    }
                })
                .catch(error => console.error("Error sending request:", error));
        });
    });
}
