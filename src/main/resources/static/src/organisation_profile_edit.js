const root = document.getElementById("desc-container");
        async function getOrganisationDesc() {
            try {
                const response = await fetch('http://localhost:8080/api/organisationProfile/', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok && response.status === 204) {
                    const result = await response.json();

                        const descContainer = document.createElement("div");
                        descContainer.classList.add("desc-container");

                        const imgDiv = document.createElement("div");
                        imgSection.classList.add("img-container");

                        const img = document.createElement("img");
                        img.alt = "zdjecie";
                        img.classList.add("project-img");
                        img.src = result.imgPath;

                        const textDiv = document.createElement("div");
                        textSection.classList.add("text-container");
                        textDiv.innerHTML = result.desc;

                        const descParagraph = document.createElement("p");
                        descParagraph.classList.add("organisation-desc");
                        descParagraph.innerHTML = result.desc;

                        imgDiv.appendChild(img);
                        textDiv.appendChild(descParagraph);
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

        async function saveOrganisationDesc() {

            const form = document.forms["organisation-form"];

            const formData = new FormData(form);

            const data = Object.fromEntries(formData.entries());

            console.log(data);


            try {
                const response = await fetch('http://localhost:8080/api/organisationProfile/', {
                    method: 'POST',
                    body: formData
                });

                if (response.ok) {

                    console.log("udało się zapisać dane");

                }
                else {
                    console.log("nie udało się zapisać danych");
                }

                
            } catch (error) {
                console.log(error);
            }

        }