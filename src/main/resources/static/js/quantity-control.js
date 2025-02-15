document.addEventListener("DOMContentLoaded", function () {
    const qtyInputs = document.querySelectorAll(".qty-input");

    if (!qtyInputs.length) {
        return;
    }

    qtyInputs.forEach(function (qtyInput) {
        var input = qtyInput.querySelector(".product-qty");
        var minusBtn = qtyInput.querySelector(".qty-count--minus");
        var addBtn = qtyInput.querySelector(".qty-count--add");

        var min = parseInt(input.min);
        var max = parseInt(input.max);

        function updateButtons() {
            var value = parseInt(input.value);
            minusBtn.disabled = value <= min;
            addBtn.disabled = value >= max;
        }

        input.addEventListener("change", function () {
            var value = parseInt(input.value);
            if (isNaN(value) || value < min) {
                input.value = min;
            } else if (value > max) {
                input.value = max;
            }
            updateButtons();
        });

        addBtn.addEventListener("click", function () {
            var value = parseInt(input.value);
            if (value < max) {
                input.value = value + 1;
                updateButtons();
            }
        });

        minusBtn.addEventListener("click", function () {
            var value = parseInt(input.value);
            if (value > min) {
                input.value = value - 1;
                updateButtons();
            }
        });

        updateButtons(); // При първоначално зареждане
    });
});
