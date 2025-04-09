const logoutLink = document.querySelector('.logout-link');
if (logoutLink) {
    logoutLink.addEventListener('click', (event) => {
        event.preventDefault();

        fetch('/api/v1/auth/logout/user', {method: 'POST'})
            .then(response => {
                if (response.ok) {
                    console.log("Logout successful");
                    window.location.href = "/";
                } else {
                    console.error("Logout failed");
                }
            })
            .catch(error => {
                console.error("Error during logout:", error);
            });
    });
}