const root1 = document.getElementsByClassName("projects-container")[0];
async function getProjects() {
    try {
        const response = await fetch('http://localhost:8080/api/project/all', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const result = await response.json();

            showProjects(result);

        } else {
            console.log('Blad odpowiedzi serwera', await response.body);
        }

    } catch (error) {
        console.error('Error', error);
    }

}

async function searchProjects(word, event) {

    event.preventDefault();

    try {
        const response = await fetch('http://localhost:8080/api/project/search/' + word , {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {

            const result = await response.json();

            root1.innerHTML = "";

            showProjects(result);


        } else if (response.status == 404) {

            root1.innerHTML = "<h2> Nie znaleziono projektów </h2>";

        }
    } catch (error) {

        console.log(error);

    }

}

function showProjects(result) {

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

                let projectDesc;

                console.log(project['desc-long']);
                console.log(project.desc);

                if (project['desc-long'])
                {
                    projectDesc = project['desc-long'];
                } else if (project.desc){
                    projectDesc = project.desc;
                }

                textSection.innerHTML = ' <h2 class="project-tittle">' + project.title + 
                    ' </h2> <p class="project-short-desc">' +projectDesc + '</p>';

                const tags = document.createElement("h4");
                tags.innerHTML = "Tagi: "
                for (let tag in project.tags) {
                    console.log(tag);
                    tags.innerHTML += "<span class=tag> [" + project.tags[tag].desc + "] </span>";
                }

                textSection.appendChild(tags);
                    
                const projectButton = document.createElement("button");
                projectButton.type = "button";
                projectButton.addEventListener("click" ,goToProjectDesc);
                projectButton.dataset.projectId = project.id;
                projectButton.classList.add("project-desc-button");
                projectButton.innerHTML = "Szczegóły";
                

                imgSection.appendChild(img);
                textSection.appendChild(projectButton);
                projectDiv.appendChild(imgSection);
                projectDiv.appendChild(textSection);
                root1.appendChild(projectDiv);
            })

}

function goToProjectDesc(event) {

    location.href = "http://localhost:8080/volunteer/projects/" + this.dataset.projectId;

}

document.getElementById("searchButton").addEventListener("click", (event) => {
    
    const word = document.getElementById("searchbar").value;
    searchProjects(word, event);
});
