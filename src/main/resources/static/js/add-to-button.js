console.log("JavaScript Loaded!");

document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM is fully loaded! Running scripts...");

    // Обновяваме количката при зареждане
    updateCartIcon();

    // Добавяме event listener за Add to Cart бутона
    document.addEventListener("click", function (event) {
        if (event.target.classList.contains("add_to_bag_button")) {
            event.preventDefault();
            console.log("Add to Cart clicked!");

            const itemId = event.target.getAttribute("data-id");
            if (!itemId) {
                console.error("Missing data-id attribute!");
                return;
            }

            addItemToCart(itemId);
        }
    });
});

function addItemToCart(itemId) {
    const quantity = document.getElementById("quantity").value;
    const selectedSize = document.querySelector("input[name='size']:checked");

    if (!selectedSize) {
        alert("Please select a size.");
        return;
    }

    console.log("Checking login status...");

    fetch("http://localhost:8081/api/v1/auth/user/status", {
        method: "GET",
        credentials: "include"
    })
        .then(response => response.json())
        .then(data => {
            if (!data.authenticated) {
                alert("You need to be logged in to add items to the cart!");
                return;
            }

            console.log("User is logged in! Sending add-to-cart request...");

            return fetch("http://localhost:8081/api/v1/cartItems/item/add", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    itemId: itemId,
                    quantity: quantity,
                    size: selectedSize.value
                })
            });
        })
        .then(response => {
            if (!response.ok) throw new Error("Failed to add item");
            return response.json();
        })
        .then(data => {
            alert("Item added to cart!");
            updateCartIcon();
        })
        .catch(error => console.error("Error:", error));
}


function updateCartIcon() {
    console.log("Updating cart icon...");

    fetch("http://localhost:8081/api/v1/cartItems/count", {
        method: "GET",
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to fetch cart count");
            return response.json();
        })
        .then(cartCount => {
            console.log("Cart count from API:", cartCount);

            const cartIcon = document.getElementById("cart-count");
            if (cartIcon) {
                cartIcon.textContent = cartCount;
                cartIcon.style.visibility = "visible"; // Показваме количката само след като получим стойността
                console.log("Cart count updated to:", cartCount);
            } else {
                console.error("Cart count element not found!");
            }
        })
        .catch(error => {
            console.error("Error updating cart count:", error);
        });
}
