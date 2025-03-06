document.addEventListener("DOMContentLoaded", function () {
    console.log("Checkout Navigation Loaded!");

    const checkoutButton = document.getElementById("go-to-checkout");

    if (checkoutButton) {
        checkoutButton.addEventListener("click", function () {
            window.location.href = "/my-order/checkout";
        });
    } else {
        console.error("Checkout button not found!");
    }
});
