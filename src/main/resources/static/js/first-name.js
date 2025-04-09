fetch("http://localhost:8081/api/v1/auth/user/token")
    .then(response => response.json())
    .then(data => {
        if (data.authenticated) {
            // Потребителят е логнат, извличаме firstName
            fetch("http://localhost:8081/api/v1/users/me/firstName")
                .then(response => response.json())
                .then(firstNameData => {
                    const firstName = firstNameData.data;
                    if (firstName && typeof firstName === "string") {
                        document.querySelector("#signInLink").style.display = "none";
                        document.querySelector("#userDropdown").style.display = "block";
                        document.querySelector("#userFirstName").textContent = firstName;
                    } else {
                        console.log("First name not available.");
                    }
                })
                .catch(error => {
                    console.error("Error fetching first name:", error);
                });
        } else {
            console.log("User not authenticated.");
            document.querySelector("#userDropdown").style.display = "none";
        }
    })
    .catch(error => {
        console.error("Error checking authentication:", error);
    });