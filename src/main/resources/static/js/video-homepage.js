document.addEventListener("DOMContentLoaded", function () {
    const video = document.querySelector(".background-video");

    function loadVideo() {
        if (video && video.getAttribute("preload") === "none") {
            video.setAttribute("preload", "auto"); // Зареждаме видеото само когато е в изгледа
        }
    }

    // Следи скролването и зарежда видеото, когато влезе в изгледа
    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                loadVideo();
                observer.unobserve(video); // Спира да следи, след като е заредено
            }
        });
    }, { threshold: 0.5 });

    observer.observe(video);
});
