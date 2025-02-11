async function getOrganisations() {

    const root = document.querySelector(".organisation-container");

    try {
            const organisationData = await fetch('http://localhost:8080/api/organisation/all', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (organisationData.ok) {
        
                const result = await organisationData.json();

                result.forEach(org => {

                    const organisation = document.createElement("div");
                    organisation.classList.add("organisation");


                    const h1 = document.createElement("h1");
                    h1.innerHTML = org.name;

                    const h4 = document.createElement("h4");
                    h4.innerHTML = org.type;

                    const p = document.createElement("p");
                    p.classList.add("organisation-desc");
                    p.innerHTML = org.desc;

                    const textContainer = document.createElement("div");
                    textContainer.classList.add("text-container");

                    const button = document.createElement("button");
                    button.type = "button";
                    button.dataset.organisationId = org.id;
                    button.classList.add("organisation-button");
                    button.innerHTML = "Szczegóły";
                    button.addEventListener("click", goToOrganisationProfile);

                    const imgSection = document.createElement("div");
                    imgSection.classList.add("img-container");

                    const img = document.createElement("img");
                    img.src = org.imgPath;
                    img.alt = "Zdjęcie";
                    img.classList.add("organisation-img");

                    imgSection.appendChild(img);
                    organisation.append(imgSection);

                    textContainer.appendChild(h1);
                    textContainer.appendChild(h4);
                    textContainer.appendChild(p);
                    textContainer.appendChild(button);

                    organisation.appendChild(textContainer);

                    root.appendChild(organisation);

                });

                

            }

    } catch (error) {
        console.log(error);
    }

}

function goToOrganisationProfile() {
    const id = this.dataset.organisationId;
    location.href = `http://localhost:8080/organisation/${id}/profile`;
}
