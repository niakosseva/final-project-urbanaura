document.addEventListener("DOMContentLoaded", function () {
    const toggleButton = document.getElementById("toggleButton");
    const dropdown = document.getElementById("userDropdownMenu");
    const dropdownWrapper = document.getElementById("userDropdown");

    if (toggleButton && dropdown && dropdownWrapper) {
        // Отваря и затваря dropdown менюто
        toggleButton.addEventListener("click", function (event) {
            event.stopPropagation();  // Спира отиването на събитието до родителите
            dropdown.classList.toggle("show");  // Превключва видимостта
        });

        // Затваря dropdown-а, ако е кликнато извън него
        window.addEventListener("click", function (event) {
            if (!dropdownWrapper.contains(event.target)) {
                dropdown.classList.remove("show");
            }
        });

        dropdown.addEventListener("click", function(event) {
            event.stopPropagation();
        });
    }
});
