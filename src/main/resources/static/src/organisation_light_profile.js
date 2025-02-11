const root = document.getElementById("profile-data");

        const rootHeader = document.getElementById("profile-header");

        const urlArray = window.location.href.split("/");
        const organisationId = urlArray.at(-2);

        console.log(organisationId);

        
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
    const response = await fetch('http://localhost:8080/api/organisation/' + organisationId, {
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
                const response = await fetch('http://localhost:8080/api/organisationProfile/' + organisationId, {
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

                        imgDiv.appendChild(img);
                        textDiv.appendChild(descParagraph);
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
                        button.classList.add("profile-edit-button");
                        button.innerHTML = "Edytuj opis";
                        button.src = 'http://localhost:8080/organisation/profile/edit/desc';

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

        async function getProjects() {

            var root1 = document.getElementsByClassName("projects-container")[0];

            try {
                const response = await fetch(`http://localhost:8080/api/project/organisation${organisationId}/all`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const result = await response.json();

                    if (Object.keys(result).length == 0 ) {

                        const projectDiv = document.createElement("div");
                        projectDiv.classList.add("project");

                        const textSection = document.createElement("section");
                        textSection.classList.add("text-container");

                        textSection.innerHTML = "Nie masz jeszcze stworzonych projektów.";

                        projectDiv.appendChild(textSection);

                        root1.appendChild(projectDiv);

                    }

                    result.forEach(project => {
                        console.log(project);

                        const projectDiv = document.createElement("div");
                        projectDiv.classList.add("project");

                        const imgSection = document.createElement("section");
                        imgSection.classList.add("img-container");

                        const img = document.createElement("img");
                        img.src = project.imgPath;
                        img.alt = "zdjecie";
                        img.classList.add("project-img");

                        const textSection = document.createElement("section");
                        textSection.classList.add("text-container");

                        textSection.innerHTML = ' <h2 class="project-tittle">' + project.title + 
                            ' </h2> <p class="project-short-desc">' + project['desc-long'] + '</p>';

                        const tags = document.createElement("h4");
                        tags.innerHTML = "Tagi: "
                        for (let tag in project.tags) {
                            console.log(tag);
                            tags.innerHTML += "<span class=tag> [" + project.tags[tag].desc + "] </span>";
                        }

                        textSection.appendChild(tags);

                        imgSection.appendChild(img);
                        projectDiv.appendChild(imgSection);
                        projectDiv.appendChild(textSection);
                        root1.appendChild(projectDiv);
                    })

                } else {
                    console.log('Blad odpowiedzi serwera', response.body);
                }

            } catch (error) {
                console.error('Error', error);
            }

        }

        function goToEditDesc() {
            location.href = "http://localhost:8080/organisation/profile/edit/desc";
        }

        function loadContent() {
            getOrganisationData();
            getOrganisationDesc();
            getProjects();
        }