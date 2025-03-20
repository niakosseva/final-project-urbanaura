document.addEventListener("DOMContentLoaded", function() {
    fetch("http://localhost:8081/api/v1/users/me/firstName", {
        method: "GET",
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch first name");
            }
            return response.json();
        })
        .then(data => {
            const firstName = data.data;
            if (firstName) {
                document.querySelector("#signInLink").style.display = "none";
                document.querySelector("#userDropdown").style.display = "block";
                document.querySelector("#userFirstName").textContent = firstName;
            }
        })
        .catch(console.error);
});