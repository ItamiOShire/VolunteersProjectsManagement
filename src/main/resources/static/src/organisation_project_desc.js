async function getProject() {
                
    const root = document.getElementsByClassName("project-container")[0];
    const urlArray = window.location.href.split("/");
    const projectId = urlArray.at(-1);

    try {
            const response = await fetch('http://localhost:8080/api/project/' + projectId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const project = await response.json();
            console.log(project);
            const imgContainer = document.createElement("div");
            imgContainer.classList.add("img-container");

            const img = document.createElement("img");
            img.src = project.imgPath;
            img.alt = "zdjecie";
            img.classList.add("project-img")

            const textContainer = document.createElement("div");
            textContainer.classList.add("text-container");

            const projectTitle = document.createElement("h2");
            projectTitle.classList.add("project-tittle");
            projectTitle.innerHTML = project.title;

            const projectShortDesc = document.createElement("h4");
            projectShortDesc.classList.add("project-short-desc");
            projectShortDesc.innerHTML = "Organizacja - " + project.orgName;

            const projectLongDesc = document.createElement("p");
            projectLongDesc.classList.add("project-long-desc");
            projectLongDesc.innerHTML = project.desc;

            const tagsContainer = document.createElement("div");
            tagsContainer.classList.add("tags-container");

            const tags = document.createElement("p");
            tags.classList.add("tags");

            for (let x in project.tags) {
                tags.innerHTML += "[" + project.tags[x].desc + "] ";
            }

            imgContainer.appendChild(img);

            textContainer.appendChild(projectTitle);
            textContainer.appendChild(projectShortDesc);
            textContainer.appendChild(projectLongDesc);

            tagsContainer.appendChild(tags);

            textContainer.appendChild(tagsContainer);

            root.appendChild(imgContainer);
            root.appendChild(textContainer);

        } else {
            console.log("blad serwera");
        }

    } catch (error) {

        console.log(error);

    }

}

async function getVolunteers() {

const root = document.getElementById("volunteers");
const urlArray = window.location.href.split("/");
const projectId = urlArray.at(-1);

try {
        const response = await fetch('http://localhost:8080/api/volunteer/project/' + projectId, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (response.ok) {

        const result = await response.json();

        result.forEach(volunteer => {

            const div = document.createElement("div");
            div.classList.add("volunteer");
            div.innerHTML = "<span>" + volunteer.fnameAndLname[0] + "</span> " + volunteer.fnameAndLname +
                            "<br> Email - " + volunteer.email;

            root.appendChild(div);
            
        });

    }

} catch (error) {
    console.log(error);
}


}

async function getTasks() {

const root = document.getElementById("tasks");
const urlArray = window.location.href.split("/");
const projectId = urlArray.at(-1);

const priorityArray = [
    "Niski",
    "Umiarkowany",
    "Średni",
    "Wysoki"]

try {
        const response = await fetch('http://localhost:8080/api/task/project/' + projectId, {
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

function goToProjectManagment() {

const urlArray = window.location.href.split("/");
const projectId = urlArray.at(-1);
location.href = "http://localhost:8080/organisation/projects/edit/desc/" + projectId;

}


document.body.onload = function () {
getProject();
getVolunteers();
getTasks();
}
