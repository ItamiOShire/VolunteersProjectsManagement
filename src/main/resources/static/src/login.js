async function logIn() {

    const form = document.forms["loginForm"];

    const formData = new FormData(form);

    const data = Object.fromEntries(formData.entries());


    try {
        const response = await fetch("http://localhost:8080/api/login/auth" , {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
            } ,
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const result = await response.text();
            console.log(result);
            if (result === "VOLUNTEER") {
                location.href = "http://localhost:8080/volunteer/projects";
            } else {
                location.href = "http://localhost:8080/organisation/projects"
            }
        } else {
            console.log("Błąd odpowiedzi serwera ", (await response).status);
        }
    } catch (error) {
        console.log(error);
    }

}
