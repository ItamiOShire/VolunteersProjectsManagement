async function getVolunteerData() {

    const root = document.getElementById("profile-data");

    const profileData = [
        "fname-lname", 
        "email", 
        "phone-number", 
        "adress"
    ]

    const headlines = [
        "Imię i nazwisko",
        "Email", 
        "Nr telefonu",
        "Wiek"
    ]

    try {
        const response = await fetch('http://localhost:8080/api/volunteer/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        let i = 0;

        if (response.ok) {
            const result = await response.json();
            
            for (let x in result) {

                const divEl = document.createElement("div");
                divEl.classList.add(profileData[i]);

                const h2El = document.createElement("h2");
                h2El.innerHTML = headlines[i];

                const h4El = document.createElement("h4");
                h4El.innerHTML = result[x];

                divEl.appendChild(h2El);
                divEl.appendChild(h4El);
                root.appendChild(divEl);

                i = i + 1;

                if (i == 4) {
                    break;
                }

            }


        } else {
            console.log('Blad odpowiedzi serwera', response.body);
        }

    } catch (error) {
        console.error('Error ', error);
    }

}

async function getProjects() {

    const root = document.getElementById("projects");

    try {
        const response = await fetch('http://localhost:8080/api/project/volunteer/all', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {

            const result = await response.json();

            result.forEach(project => {

                const projectDiv = document.createElement("div");
                projectDiv.classList.add("project");

                const textContainer = document.createElement("section");
                textContainer.classList.add("text-container");

                const projectTitle = document.createElement("h2");
                projectTitle.classList.add("project-tittle");
                projectTitle.innerHTML = project.title;

                const projectTags = document.createElement("h4");
                projectTags.classList.add("project-tags");
                
                for (let tag in project.tags) {
                    projectTags.innerHTML += "[" + project.tags[tag].desc + "] ";
                }

                const projectButton = document.createElement("button");
                projectButton.type = "button";
                projectButton.addEventListener("click", goToProjectDesc);
                projectButton.dataset.projectId = project.id;
                projectButton.classList.add("project-desc-button");
                projectButton.innerHTML = "Szczegóły";

                textContainer.appendChild(projectTitle);
                textContainer.appendChild(projectTags);

                projectDiv.appendChild(textContainer);
                projectDiv.appendChild(projectButton);

                root.appendChild(projectDiv);
                
            });

        } else {
            console.log("Błąd odpowiedzi serwera ", await response.body);
        }

    } catch (error) {
        console.log(error);
    }

}

async function getTasks() {

    const root = document.getElementById("tasks");

    const priorityArray = [
        "Niski",
        "Umiarkowany",
        "Średni",
        "Wysoki"]

    try {
            const response = await fetch('http://localhost:8080/api/task/volunteer', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {

            const result = await response.json();

            console.log(result);

            result.forEach(task => {

                const div = document.createElement("div");
                div.classList.add("task");

                const h2 = document.createElement("h2");
                h2.classList.add("task-tittle");
                h2.innerHTML = task["task-title"];

                const p = document.createElement("p");
                p.classList.add("task-desc");
                p.innerHTML = task["task-desc"];

                const span = document.createElement("span");
                span.classList.add("priority");
                span.innerHTML = "Priorytet - " + priorityArray[task.priority - 1] + ", termin - " + task.deadline;

                div.appendChild(h2);
                div.appendChild(p);
                div.appendChild(span);

                root.appendChild(div);
                
            });

        }

    } catch (error) {
        console.log(error);
    }

}

function goToProjectDesc() {

    location.href = "http://localhost:8080/volunteer/profile/project/" + this.dataset.projectId;

}





document.body.onload = function () {
    getVolunteerData();
    getProjects();
    getTasks();
}
