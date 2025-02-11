
function otherOrganizacjionType(input) {

    const inputContainer = document.getElementById("other_o_type");

    console.log(input.value);
    if (input.value == "other"){
        const inputElem = document.createElement("input");
        const labelElem = document.createElement("label");

        inputElem.type = "text";
        inputElem.name = "other_o_type";
        inputElem.id = "other_o_type";

        labelElem.for = "other_o_type";
        labelElem.innerText = "Inny typ organizacji";

        inputContainer.appendChild(labelElem);
        inputContainer.appendChild(inputElem);
    } else {
        inputContainer.innerHTML = "";
    }

    
}

function showVolunteerForm(){
    
    let container = document.getElementById("form-container");
    container.innerHTML = '<form action="" class="form" name="volForm">' +

        '<label for="fname">Imię</label>' +
        '<input type="text" name="fname" id="fname">' + 
        
        '<label for="lname">Nazwisko</label>' + 
        '<input type="text" name="lname" id="lname">' +

        '<label for="bday">Data urodzenia</label>' +
        '<input type="date" name="birth_day" id="bday">' +

        '<label for="email">Email</label>' +
        '<input type="email" name="email" id="email">' +

        '<label for="password">Hasło</label>' +
        '<input type="password" name="password" id="password">' +

        '<label for="telnum">Numer telefonu</label>' +
        '<input type="tel" name="phone_number" id="telnum">' +

        '<button type="button" onclick = "registerVolunteer()">Zarejestruj się</button>' +

    '</form>'

    document.getElementsByClassName("volunteer-register")[0].classList.add("active");
    document.getElementsByClassName("organisation-register")[0].classList.remove("active");
}

function showOrganisationFrom(){
    console.log("aa");
    let container = document.getElementById("form-container");
    container.innerHTML = '<form action="" class="form" id="form" name="orgForm">' +

        '<label for="oname">Nazwa organizacji</label>' +
        '<input type="text" name="oname" id="oname" required>' + 

        '<label for="krs">Numer KRS</label>' + 
        '<input type="text" name="krs" id="krs" required>' +

        '<label for="street">Ulica</label>' +
        '<input type="text" name="street" id="street" required>' +

        '<label for="localnum">Nr lokalu</label>' +
        '<input type="text" name="localnum" id="localnum">' +

        '<label for="town">Miasto</label>' +
        '<input type="text" name="town" id="town" required>' +

        '<label for="zip_code">Kod pocztowy</label>' +
        '<input type="text" name="zip_code" id="zip_code" pattern="[0-9]{2}-[0-9]{3}">' +

        '<label for="otype">Typ organizacji</label>' +
        '<select name="otype" id="otype" onchange="otherOrganizacjionType(this)" required>' +
            '<option value="social">Społeczny</option>' +
            '<option value="eco">Ekologiczny</option>' +
            '<option value="nonprofit">Non-profit</option>' +
            '<option value="sport">Sportowy</option>' +
            '<option value="religion">Religijny</option>' +
            '<option value="other" id="otherType">Inny</option>' +
        '</select>' +

        '<div id="other_o_type"></div>' +

        '<label for="fname">Imię właściciela organizacji</label>' +
        '<input type="text" name="fname" id="fname" required>' +

        '<label for="lname">Nazwisko właściciela organizacji</label>' +
        '<input type="text" name="lname" id="lname" required>' +

        '<label for="email">Email</label>' +
        '<input type="email" name="email" id="email" required>' +

        '<label for="password">Hasło</label>' +
        '<input type="password" name="password" id="password" required>' +

        '<label for="telnum">Numer telefonu</label>' +
        '<input type="tel" name="phone_number" id="telnum" required>' +

        '<button type="button" onclick = "registerOrganisation()">Zarejestruj się</button>' +

    '</form>';

    document.getElementsByClassName("organisation-register")[0].classList.add("active");
    document.getElementsByClassName("volunteer-register")[0].classList.remove("active");

}


async function registerVolunteer() {

    const form = document.forms["volForm"];

    const volunteerData = new FormData(form);

    const volunteer = Object.fromEntries(volunteerData.entries());

    const user = {
        email: form.email.value,
        password: form.password.value,
        role: "volunteer"
    }

    const body = {volunteer, user};

    console.log(body);

    try {
        const response = await fetch('http://localhost:8080/api/register/volunteer', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
            } ,
            body: JSON.stringify(body),
        });

        if(response.ok){
            
            goToLogin();

        } else {
            console.log("Blad odpowiedzi serwera")
        }
    } catch (error) {
        console.log('Error: ', error)
    }
}

async function registerOrganisation() {

    const form = document.forms['orgForm'];

    const orgData = new FormData(form);

    const organisation = Object.fromEntries(orgData.entries());

    const user = {
        email: form.email.value,
        password: form.password.value,
        role: "organisation"
    }

    const body = {organisation, user};

    try {
        const response = await fetch('http://localhost:8080/api/register/organisation', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
            } ,
            body: JSON.stringify(body),
        });

        if(response.ok){
            
            goToLogin();

        } else {
            console.log("Blad odpowiedzi serwera")
        }
    } catch (error) {
        console.log('Error: ', error)
    }
}

function goToLogin() {
    location.href = "http://localhost:8080/login"
}