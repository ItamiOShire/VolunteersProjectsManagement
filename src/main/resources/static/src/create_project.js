$(".chosen-select").chosen();
document.getElementById('tags_chosen').style.width = "";
document.getElementsByClassName('chosen-search-input')[0].setAttribute('value',"Wybierz tagi");
document.getElementsByClassName('chosen-search-input')[0].setAttribute('placeholder',"Wybierz tagi");

const tasks = [];

async function createProject(){

    const form = document.forms["projectForm"];

    const formData = new FormData(form);

    const data = Object.fromEntries(formData.entries());

    console.log(formData);

    try {
    const response = await fetch("http://localhost:8080/api/project/create" , {
        method: 'POST',
        body: formData
    });

    if (response.ok) {
        const result = await response.text();
        console.log(result);
        document.getElementById("tasks-container").style.display = "grid";
        document.getElementById("create-tasks-button").dataset.projectId = result;
        document.getElementById("form-container").style.display = "none";

    } else {
        console.log("Błąd odpowiedzi serwera ", (await response).status);
    }
} catch (error) {
    console.log(error);
}

}

function addTasksToList() {

    const form = document.forms["taskForm"];

    const formData = new FormData(form);

    const data = Object.fromEntries(formData.entries());

    data.projectId = document.getElementById("create-tasks-button").dataset['projectId'];

    console.log(data);

    tasks.push(data);

    const root = document.getElementById("tasks");

    const taskDiv = document.createElement("div");
    taskDiv.classList.add("task");

    const taskHeader = document.createElement("h2");
    taskHeader.classList.add("task-tittle");
    taskHeader.innerText = data["task-title"];

    const taskDesc = document.createElement("p");
    taskDesc.classList.add("task-desc");
    taskDesc.innerText = data["task-desc"];

    const taskPriority = document.createElement("span");
    taskPriority.classList.add("priority");
    taskPriority.innerText = "Priorytet - " + data["priority"] + ", termin - " + data["deadline"];

    taskDiv.appendChild(taskHeader);
    taskDiv.appendChild(taskDesc);
    taskDiv.appendChild(taskPriority);
    root.append(taskDiv);


    console.log(tasks);
}

async function saveTasks() {


    try {

        const response = await fetch("http://localhost:8080/api/task/save", {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(tasks)
        });

        if (response.ok) {
            console.log("Udało sie");
            location.href = "http://localhost:8080/organisation/projects";
        } else {
            console.log('Blad serwera: ', await response.text());
        }

    } catch (error) {
        console.error(error);
    }
}

async function getId(){
try {
    const response = await fetch("http://localhost:8080/api/login/getId", {
        method: "GET",
        'Content-Type': 'application/json',
    });

    if (response.ok){
        const result = await response.text();
        console.log(result);
    } else {
        console.log("cos nie poszlo");
    }
} catch (error){
    console.log(error)
}
}