async function getProjects() {

    var root1 = document.getElementsByClassName("projects-container")[0];
    
    try {
        const response = await fetch('http://localhost:8080/api/project/organisation/all', {
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
                    
                const projectButton = document.createElement("button");
                projectButton.type = "button";
                projectButton.dataset.projectId = project.id;
                projectButton.addEventListener("click", goToProjectDesc);
                projectButton.classList.add("project-desc-button");
                projectButton.innerHTML = "Szczegóły";
                

                imgSection.appendChild(img);
                textSection.appendChild(projectButton);
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

function goToProjectDesc() {

    location.href = "http://localhost:8080/organisation/projects/" + this.dataset.projectId;

}