document.addEventListener("DOMContentLoaded", function () {
    console.log("Checkout Page Loaded!");
    console.log("Card Number:", document.getElementById("ccnum").value);
    console.log("Card Holder:", document.getElementById("cname").value);
    console.log("Expiration Month:", document.getElementById("expmonth").value);
    console.log("Expiration Year:", document.getElementById("expyear").value);
    console.log("CVV:", document.getElementById("cvv").value);

    // Зареждаме общата цена на количката
    loadCartTotal();

    const checkoutButton = document.getElementById("checkout-btn");

    if (checkoutButton) {
        checkoutButton.addEventListener("click", function () {
            const paymentData = {
                cardNumber: document.getElementById("ccnum").value,
                cardHolderName: document.getElementById("cname").value,
                expMonth: document.getElementById("expmonth").value,
                expYear: document.getElementById("expyear").value,
                cvv: document.getElementById("cvv").value
            };

            fetch("/api/v1/orders/order", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(paymentData)
            })
                .then(response => {
                    if (!response.ok) throw new Error(`Order API Error: ${response.status}`);
                    return response.json();
                })
                .then(data => {
                    console.log("Order created:", data);

                    return fetch(`/api/v1/carts/clear`, {
                        method: "DELETE",
                        credentials: "include"
                    });
                })
                .then(response => {
                    if (!response.ok) throw new Error(`Clear Cart API Error: ${response.status}`);
                    return response.json();
                })
                .then(() => {
                    showToast("Order placed successfully!");
                })
                .catch(error => console.error("Error processing checkout:", error));
        });
    } else {
        console.error("Checkout button not found!");
    }
});
function showToast(message) {
    const toast = document.getElementById("order-success-toast");
    toast.querySelector("p").textContent = message;
    toast.classList.add("show");

    setTimeout(() => {
        toast.classList.remove("show");
        window.location.href = "/index"; // След 3 секунди пренасочваме потребителя
    }, 3000);
}
function loadCartTotal() {
    fetch("/api/v1/auth/user/status", {
        method: "GET",
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) throw new Error(`User API Error: ${response.status}`);
            return response.json();
        })
        .then(userData => {
            const cartId = userData.cartId;
            if (!cartId) throw new Error("No cartId found for user!");

            return fetch(`/api/v1/carts/${cartId}/cart/total-price`, {
                method: "GET",
                credentials: "include"
            });
        })
        .then(response => {
            if (!response.ok) throw new Error(`Cart API Error: ${response.status}`);
            return response.json();
        })
        .then(cartData => {
            document.getElementById("cart-total-price").textContent = cartData.data.toFixed(2);
        })
        .catch(error => console.error("Error fetching cart total:", error));
}
