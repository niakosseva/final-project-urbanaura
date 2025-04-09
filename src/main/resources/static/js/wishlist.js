document.addEventListener("DOMContentLoaded", function () {
    fetchUserStatusAndWishlist();
    setupAddToWishlistButtons();
});

function fetchUserStatusAndWishlist() {
    fetch("http://localhost:8081/api/v1/auth/user/token", {
        method: "GET",
        credentials: "include"
    })

        .then(response => {
            if (!response.ok) {
                const errorMessage = response.status === 401 ? "You need to be logged in to add to your wishlist!" : "Something went wrong."
                alert(errorMessage);
                return;
            }
            return response.json();
        })
        .then(data => {
            if (!data) return;

            window.jwtToken = data.jwt;

            fetch("http://localhost:8082/api/v1/wishlist/user", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${data.jwt}`
                },
                credentials: "include"
            })
                .then(response => {
                    if (!response.ok) {
                        alert("Error loading wishlist.");
                        return;
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data) return;
                    renderWishlist(data.data);
                });
        })
        .catch(error => {
            console.error("Error fetching wishlist:", error);
        });
}


function setupAddToWishlistButtons() {
    document.querySelectorAll(".wishlist-btn").forEach(button => {
        button.addEventListener("click", function () {
            const itemId = this.dataset.itemId;
            const unitPrice = this.dataset.unitPrice;
            const itemName = this.dataset.itemName;

            console.log("\uD83D\uDD0D Adding item:", itemId, "Price:", unitPrice, "Name:", itemName);

            fetch("http://localhost:8081/api/v1/auth/user/token", {
                method: "GET",
                credentials: "include"
            })
                .then(response => response.json())
                .then(data => {
                    if (data.jwt) {
                        addToWishlist(itemId, unitPrice, itemName, data.jwt);
                    } else {
                        const errorMessage = data.status === 409 ? "User cannot be found!" : "Something went wrong."
                    }
                });
        });
    });
}

function addToWishlist(itemId, unitPrice, name, token) {



    const requestData = {
        itemId: parseInt(itemId),
        unitPrice: parseFloat(unitPrice),
        name: name
    };

    console.log(" Sending request to Wishlist:", JSON.stringify(requestData));

    fetch("http://localhost:8082/api/v1/wishlist/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(requestData),
        credentials: "include"
    })
        .then(response => response.json().then(data => {
            if (!response.ok) {
                const errorMessage = response.status === 409
                    ? "This item already exists in your wishlist!"
                    : "Something went wrong.";
                alert(errorMessage);
                return;
            }

            alert(data.message || "Item added to wishlist!");
            fetchUserStatusAndWishlist();
        }))
        .catch(error => console.error("Error sending request:", error));
}

function renderWishlist(items) {
    if (!Array.isArray(items)) {
        console.error("Expected array, got:", items);
        return;
    }

    const wishlistContainer = document.getElementById("wishlist-items");
    wishlistContainer.innerHTML = "";

    items.forEach(item => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${item.name}</td>
            <td>${item.unitPrice} $</td>
            <td>
                <button class="remove-from-wishlist btn btn-danger" data-item-id="${item.itemId}">Remove</button>
            </td>
        `;
        wishlistContainer.appendChild(row);
    });

    setupRemoveButtons(window.jwtToken);
}

function setupRemoveButtons(jwtToken) {
    document.querySelectorAll('.remove-from-wishlist').forEach(button => {
        button.addEventListener('click', function () {
            const itemId = this.dataset.itemId;

            fetch(`http://localhost:8082/api/v1/wishlist/remove?itemId=${itemId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`
                }
            })
                .then(response => {
                    if (response.ok) {
                        this.closest('tr').remove();
                    } else {
                        alert("Could not remove item from wishlist.");
                    }
                })
                .catch(error => {
                    console.error("Remove error:", error);
                });
        });
    });


}
