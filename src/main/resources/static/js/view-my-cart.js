document.addEventListener("DOMContentLoaded", function () {
    console.log("JavaScript Loaded for viewing cart items!");

    const cartTableBody = document.getElementById("cartTableBody");
    const totalPriceElement = document.getElementById("totalPrice");

    function loadCartItems() {
        fetch("http://localhost:8081/api/v1/cartItems/my-cart-items", {
            method: "GET",
            credentials: "include"
        })
            .then(response => {
                if (!response.ok) throw new Error(`Cart API Error: ${response.status}`);
                return response.json();
            })
            .then(cartData => {
                console.log("Cart API Response:", cartData);

                if (cartData.data && cartData.data.length > 0) {
                    renderCartItems(cartData.data);
                } else {
                    console.warn("Cart is empty.");
                    cartTableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Your cart is empty.</td></tr>`;
                }
            })
            .catch(error => console.error("ERROR:", error));
    }

    function renderCartItems(items) {
        if (!cartTableBody) {
            console.error("cartTableBody не е намерен в HTML!");
            return;
        }

        cartTableBody.innerHTML = "";
        let totalPrice = 0;

        items.forEach(item => {
            console.log("Checking item:", item);

            const row = document.createElement("tr");
            row.innerHTML = `
                <td class="column-1">${item.item ? item.item.name : "Unknown Product"}</td> 
                <td class="column-2">${item.quantity}</td>
                <td class="column-3">$${item.unitPrice ? Number(item.unitPrice).toFixed(2) : "N/A"}</td>
                <td class="column-4">$${item.totalPrice ? Number(item.totalPrice).toFixed(2) : "N/A"}</td>
            `;
            cartTableBody.appendChild(row);
            totalPrice += item.totalPrice ? parseFloat(item.totalPrice) : 0;
        });

        if (totalPriceElement) {
            totalPriceElement.textContent = totalPrice.toFixed(2);
        }
    }

    if (window.location.pathname.includes("shopping-cart")) {
        loadCartItems();
    }
});