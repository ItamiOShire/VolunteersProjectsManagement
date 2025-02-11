const urlArray = window.location.href.split("/");
const projectId = urlArray.at(-1);

const tasks = [];
const tasksToDelete = [];

const priorityArray = [
        "Niski",
        "Umiarkowany",
        "Średni",
        "Wysoki"]

async function getTasksToVolunteersContainer() {

    const root = document.getElementById("task");
    root.innerHTML = "";

    try {
        const response = await fetch('http://localhost:8080/api/task/project/' + projectId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const result = await response.json();
            result.forEach(task => {
                
                const option = document.createElement("option");
                option.value = task.id;
                option.innerHTML = task["task-title"];

                root.appendChild(option);

            });

            getVolunteersByProject();
            getVolunteersByProjectToDelete();
        }

    } catch (error) {
        console.log(error);
    }

}

function printVolunteersInAddTask(volunteer, root, isSuggested) {
                const div = document.createElement("div");
                div.classList.add("volunteer");
                if (!isSuggested){
                    div.innerHTML = "<span>" + volunteer.fnameAndLname[0] + "</span> " + volunteer.fnameAndLname + 
                    '<button type = "button" class="add-volunteer-to-task-button" onclick = "addVolunteerToTask(' + volunteer.id + ', this)"> Dodaj </button>';
                } else {
                    div.innerHTML = "<span>" + volunteer.fnameAndLname[0] + "</span> " + volunteer.fnameAndLname + 
                    '<button type = "button" class="add-volunteer-to-task-button" onclick = "addVolunteerToTask(' + volunteer.id + ', this)"> Dodaj </button> <output> Chęć uczestwnictwa </output>';
                }

                root.appendChild(div);
}

async function getVolunteersByProject() {

    const root = document.getElementById("volunteers");
    const taskId = document.getElementById("task").value;
    root.innerHTML = "";

    try {
            const response = await fetch('http://localhost:8080/api/volunteer/withTasks/' +taskId + '/project/' + projectId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

            const volunteersWithSuggestedTasksResponse = await fetch('http://localhost:8080/api/volunteer/withSuggestedTasks/' +taskId + '/project/' + projectId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        const volunteersToSkip = [];

        if (volunteersWithSuggestedTasksResponse.ok) {


            const suggestedVolunteers = await volunteersWithSuggestedTasksResponse.json();

            console.log(suggestedVolunteers)

            if (response.ok) {

                const result = await response.json();

                console.log(result);

                result.forEach(volunteer1 => {

                    suggestedVolunteers.forEach(volunteer2 => {

                        if (volunteer1.email == volunteer2.email) {
                            printVolunteersInAddTask(volunteer1, root, true);
                            volunteersToSkip.push(volunteer1);
                        }

                    })

                })

                console.log(volunteersToSkip);

                result.forEach(volunteer => {

                    if (!volunteersToSkip.includes(volunteer)) {
                        printVolunteersInAddTask(volunteer, root, false);
                    }

                });
            }

        }



    } catch (error) {
        console.log(error);
    }


}

async function getVolunteersByProjectToDelete() {

    const root = document.getElementById("volunteers-to-delete");
    const taskId = document.getElementById("task").value;
    root.innerHTML = "";

    try {
            const response = await fetch('http://localhost:8080/api/volunteer/withTasksToDelete/' +taskId + '/project/' + projectId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {

            const result = await response.json();

            result.forEach(volunteer => {

                console.log(volunteer);

                const div = document.createElement("div");
                div.classList.add("volunteer");
                div.innerHTML = "<span>" + volunteer.fnameAndLname[0] + "</span> " + volunteer.fnameAndLname + 
                '<button type = "button" class="add-volunteer-to-task-button" onclick = "deleteVolunteerFromTask(' + volunteer.id + ', this)"> Usuń </button>';

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

    root.innerHTML = "";
    

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

                const divTaskDesc = document.createElement("div");
                divTaskDesc.classList.add("task-desc");

                const h2 = document.createElement("h2");
                h2.classList.add("task-tittle");
                h2.innerHTML = task["task-title"];

                const p = document.createElement("p");
                p.classList.add("task-desc");
                p.innerHTML = task["task-desc"];

                const span = document.createElement("span");
                span.classList.add("priority");
                span.innerHTML = "Priorytet - " + priorityArray[task.priority - 1] + ", termin - " + task.deadline;

                divTaskDesc.append(h2);
                divTaskDesc.append(p);
                divTaskDesc.append(span);

                const taskControlls = document.createElement("div");
                taskControlls.classList.add("task-controlls");

                const select = document.createElement("select");
                select.name = "priority";
                select.id = "priority-taskId-"+task.id;
                select.dataset.taskId = task.id;
                select.onchange = updateTasks;

                priorityArray.forEach(priority => {
                    const option = document.createElement("option")
                    option.value = priorityArray.indexOf(priority) + 1;
                    option.innerHTML = priority;
                    if (priorityArray[task.priority - 1] == priority) {
                        option.selected = "selected";
                    }
                    select.appendChild(option);
                })

                const control =document.createElement("input");
                control.type="checkbox";
                control.name = "to_delete";
                control.id = "to_delete";
                control.dataset.taskId = task.id;
                control.onchange = addTasksToDelete;

                taskControlls.appendChild(select);
                taskControlls.appendChild(control);

                div.appendChild(divTaskDesc);
                div.appendChild(taskControlls);

                root.appendChild(div);
                
                });

            }

        } catch (error) {
            console.log(error);
        }

    }

    async function addTasksToList() {

        const form = document.forms["taskForm"];

        const formData = new FormData(form);

        const data = Object.fromEntries(formData.entries());

        data.projectId = projectId;

        console.log(data);

        tasks.push(data);

        const root = document.getElementById("tasks");

        try {

            const response = await fetch("http://localhost:8080/api/task/save", {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(tasks)
            });

            if (response.ok) {

                const result = await response.json();

                result.forEach(task => { 

                const div = document.createElement("div");
                div.classList.add("task");

                const divTaskDesc = document.createElement("div");
                divTaskDesc.classList.add("task-desc");

                const h2 = document.createElement("h2");
                h2.classList.add("task-tittle");
                h2.innerHTML = task["task-title"];

                const p = document.createElement("p");
                p.classList.add("task-desc");
                p.innerHTML = task["task-desc"];

                const span = document.createElement("span");
                span.classList.add("priority");
                span.innerHTML = "Priorytet - " + priorityArray[task.priority - 1] + ", termin - " + task.deadline;

                divTaskDesc.append(h2);
                divTaskDesc.append(p);
                divTaskDesc.append(span);

                const taskControlls = document.createElement("div");
                taskControlls.classList.add("task-controlls");

                const select = document.createElement("select");
                select.name = "priority";
                select.id = "priority-taskId-"+task.id;
                select.dataset.taskId = task.id;
                select.onchange = updateTasks;

                priorityArray.forEach(priority => {
                    const option = document.createElement("option")
                    option.value = priorityArray.indexOf(priority) + 1;
                    option.innerHTML = priority;
                    if (priorityArray[task.priority - 1] == priority) {
                        option.selected = "selected";
                    }
                    select.appendChild(option);
                })

                const control =document.createElement("input");
                control.type="checkbox";
                control.name = "to_delete";
                control.id = "to_delete";
                control.dataset.taskId = task.id;
                control.onchange = addTasksToDelete

                taskControlls.appendChild(select);
                taskControlls.appendChild(control);

                div.appendChild(divTaskDesc);
                div.appendChild(taskControlls);

                root.appendChild(div);

            });
                
            } else {
                console.log('Blad serwera: ', await response.text());
            }

        } catch (error) {
        console.error(error);
        }

    }

async function addVolunteerToTask(volunteerId, button) {

    const taskId = document.getElementById("task").value;

    try {
        const response = await fetch('http://localhost:8080/api/volunteer/' + volunteerId + '/task/' + taskId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const output = document.createElement("output");
            output.innerText = "Dodano";
            button.replaceWith(output);
        }                

    } catch (error) {
        console.log(error);
    }
    
}

async function deleteVolunteerFromTask(volunteerId, button) {

    const taskId = document.getElementById("task").value;

    try {
        const response = await fetch('http://localhost:8080/api/volunteer/' + volunteerId + '/task/' + taskId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const output = document.createElement("output");
            output.innerText = "Usunięto";
            button.replaceWith(output);
        }                

    } catch (error) {
        console.log(error);
    }

}

async function updateTasks() {

    const priorityId = this.value;
    const taskId = this.dataset.taskId;

    console.log(priorityId + " " + taskId);

    try {
            const response = await fetch(`http://localhost:8080/api/task/${taskId}/update/${priorityId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            alert(await response.text());
        }

    } catch (error) {
        console.log(error);
    }

}



// TODO: i dont have to add tasks to tasksToUpdate on every check
// i can do it every change on select, not checkbox - checkbox will be only
// to select tasks to delete

function addTasksToDelete(event) {

    const checkbox = event.target;

    if (checkbox.checked) {

        tasksToDelete.push(checkbox.dataset.taskId);
        console.log(tasksToDelete);

    } else if (!checkbox.checked) {

        let indexOfTask = tasksToDelete.indexOf(checkbox.dataset.taskId);

        if (indexOfTask >= 0) {
            tasksToDelete.splice(indexOfTask, 1);
        }
        console.log(tasksToDelete);

    } else {
        console.log("nie udało sie usunąć zadania")
    }

}

async function deleteTasks() {

    try {
            const response = await fetch(`http://localhost:8080/api/task/`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(tasksToDelete)
        });

        if (response.ok) {
            alert(await response.text());
            tasksToDelete.splice(0, tasksToDelete.length);
        } else {
            alert(await response.text());
            tasksToDelete.splice(0, tasksToDelete.length);
        }

    } catch (error) {
        console.log(error);
    }
    
}

function loadContent() {

    getTasks();
    getTasksToVolunteersContainer();

}

document.getElementById("task").addEventListener("change", getVolunteersByProject);

document.getElementById("task").addEventListener("change", getVolunteersByProjectToDelete);

function goToEditDesc() {

    location.href = "http://localhost:8080/organisation/projects/edit/desc/" + projectId;

}

function goToEditTasks() {

location.href = "http://localhost:8080/organisation/projects/edit/tasks/" + projectId;

}

