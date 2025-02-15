document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".carousel").forEach(carousel => {
        let index = 0;
        let images = carousel.querySelectorAll(".carousel-images img");

        if (images.length > 1) {
            images.forEach((img, i) => {
                if (i !== 0) img.style.display = "none";
            });

            carousel.querySelector(".prev").addEventListener("click", function () {
                images[index].style.display = "none";
                index = (index - 1 + images.length) % images.length;
                images[index].style.display = "block";
            });

            carousel.querySelector(".next").addEventListener("click", function () {
                images[index].style.display = "none";
                index = (index + 1) % images.length;
                images[index].style.display = "block";
            });
        }
    });
});
