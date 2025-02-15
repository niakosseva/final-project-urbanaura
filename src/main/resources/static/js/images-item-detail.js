document.addEventListener("DOMContentLoaded", function () {
    const images = [];
    document.querySelectorAll(".thumbnail-image").forEach(img => images.push(img.src));

    let currentIndex = 0;
    const mainImage = document.getElementById("main-image");

    if (images.length > 0) {
        mainImage.src = images[0];
    }

    window.changeImage = function (step) {
        currentIndex += step;

        if (currentIndex < 0) {
            currentIndex = images.length - 1;
        } else if (currentIndex >= images.length) {
            currentIndex = 0;
        }

        mainImage.src = images[currentIndex];
    };

    window.setImage = function (thumbnail) {
        mainImage.src = thumbnail.src;
        currentIndex = images.indexOf(thumbnail.src);
    };

    // 🔹 Функция за скриване/показване на описанието
    window.toggleDescription = function () {
        let desc = document.querySelector(".product-description");
        let title = document.querySelector(".description-line");

        if (desc.style.display === "none" || desc.style.display === "") {
            desc.style.display = "block";
            title.innerHTML = "Description ▲"; // Стрелка нагоре
        } else {
            desc.style.display = "none";
            title.innerHTML = "Description ▼"; // Стрелка надолу
        }
    };
});


