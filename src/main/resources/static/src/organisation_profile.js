const root = document.getElementById("profile-data");

const rootHeader = document.getElementById("profile-header");

async function getOrganisationData() {

        const profileData = [
            "name",
            "type",
            "fnameAndlname", 
            "email", 
            "phone", 
            "adress", 
            "krsNumber",
        ]

        const headlines = [
            "Imię i nazwisko właściciela",
            "Email",
            "Nr telefonu",
            "Adres",
            "Numer KRS"
        ]

        try {
            const response = await fetch('http://localhost:8080/api/organisation/', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const result = await response.json();
                console.log(result);
                let i = 0;
                let j = 0;
                
                for (const x in result) {

                    if (i > 6 ) {
                        break;
                    }

                    if (i === 0) {

                        const h2El = document.createElement("h2");
                        h2El.classList.add(profileData[i]);
                        h2El.innerHTML = result[profileData[i]];

                        rootHeader.appendChild(h2El);

                        i = i + 1;

                        if (i > 7)
                        {
                        break;
                        }

                        continue;
                    }

                    if (i === 1) {

                        const h4El = document.createElement("h4");
                        h4El.classList.add(profileData[i]);
                        h4El.innerHTML = result[profileData[i]];

                        rootHeader.appendChild(h4El);

                        i = i + 1;
                        continue;
                    }
                    
                    const divEl = document.createElement("div");
                    divEl.classList.add(profileData[i]);

                    const h2El = document.createElement("h2");
                    h2El.innerHTML = headlines[j];

                    const h4El = document.createElement("h4");
                    h4El.innerHTML = result[profileData[i]];

                    divEl.appendChild(h2El);
                    divEl.appendChild(h4El);
                    root.appendChild(divEl);

                    i = i + 1;
                    j = j + 1;
                }


            } else {
                console.log('Blad odpowiedzi serwera', response.body);
            }

        } catch (error) {
            console.error('Error', error);
        }

}

async function getOrganisationDesc() {

    const root = document.getElementById("profile-desc")

    try {
        const response = await fetch('http://localhost:8080/api/organisationProfile/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok && response.status === 200) {
            const result = await response.json();

                const descContainer = document.createElement("div");
                descContainer.classList.add("desc-container");

                const imgDiv = document.createElement("div");
                imgDiv.classList.add("img-container");

                const img = document.createElement("img");
                img.alt = "zdjecie";
                img.classList.add("project-img");
                img.src = result.imgPath;

                const textDiv = document.createElement("div");
                textDiv.classList.add("text-container");

                const descParagraph = document.createElement("p");
                descParagraph.classList.add("organisation-desc");
                if (response.status === 200) {
                    descParagraph.innerHTML = result.desc;
                } else {
                    descParagraph.innerHTML = "Nie masz jeszcze stworzonego opisu"
                }

                const button = document.createElement("button");
                button.type = "button";
                button.classList.add("profile-edit-button");
                button.src = "http://localhost:8080/organisation/profile/edit/desc";
                button.innerHTML = "Edytuj opis";

                imgDiv.appendChild(img);
                textDiv.appendChild(descParagraph);
                textDiv.appendChild(button);
                descContainer.appendChild(imgDiv);
                descContainer.appendChild(textDiv);

                root.appendChild(descContainer);

        } else if (response.ok && response.status === 204) {

                const descContainer = document.createElement("div");
                descContainer.classList.add("desc-container");

                const imgDiv = document.createElement("div");
                imgDiv.classList.add("img-container");

                const textDiv = document.createElement("div");
                textDiv.classList.add("text-container");

                const descParagraph = document.createElement("p");
                descParagraph.classList.add("organisation-desc");
                descParagraph.style.marginBottom = "5em";
                descParagraph.innerHTML = "Nie masz jeszcze stworzonego opisu"

                const button = document.createElement("button");
                button.type = "button";
                button.addEventListener("click", goToEditDesc);
                button.setAttribute("onclick","goToEditDesc()");
                button.classList.add("profile-edit-button");
                button.innerHTML = "Edytuj opis";


                textDiv.appendChild(descParagraph);
                textDiv.appendChild(button);
                descContainer.appendChild(imgDiv);
                descContainer.appendChild(textDiv);

                root.appendChild(descContainer);
                

        } else {
            console.log('Blad odpowiedzi serwera', response.body);
            
        }

    } catch (error) {
        console.error('Error', error);
    }

}

function goToEditDesc(event) {
    console.log("aaa");
    location.href = "http://localhost:8080/organisation/profile/edit/desc";
}

function loadContent() {
    getOrganisationData();
    getOrganisationDesc();
}