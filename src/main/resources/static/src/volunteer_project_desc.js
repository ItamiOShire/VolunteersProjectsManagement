const urlArray = window.location.href.split("/");
        const projectId = urlArray.at(-1);

        const priorityArray = [
                "Niski",
                "Umiarkowany",
                "Średni",
                "Wysoki"]

        function printTasks(task, root, isNotAttended, isSuggested) {

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

            if (isNotAttended) {

                const button = document.createElement("button");
                button.classList.add("join-task");
                button.type = "button";
                button.dataset.taskId = task.id;
                button.innerHTML = "Zgłoś chęć uczestniczenia";
                button.onclick = joinTask;

                div.appendChild(h2);
                div.appendChild(p);
                div.appendChild(span);
                div.appendChild(button);

                root.appendChild(div);
                

            } else  if(isSuggested) {
                span.innerHTML += " - Zgłoszono chęć uczestniczenia";

                div.appendChild(h2);
                div.appendChild(p);
                div.appendChild(span);

                root.appendChild(div);
            } else {
                span.innerHTML += " - uczestniczysz w tym zadaniu";

                div.appendChild(h2);
                div.appendChild(p);
                div.appendChild(span);

                root.appendChild(div);
            }

            

        } 

        async function getProject() {
                
                const root = document.getElementsByClassName("project-container")[0];
                

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

        async function getTasks() {

            const root = document.getElementById("tasks");

            

            try {
                    const response = await fetch('http://localhost:8080/api/task/volunteer/project/' + projectId, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });



                if (response.ok) {

                    const result = await response.json();

                    console.log(result);

                    result.forEach(task => {

                        printTasks(task, root, false);
                        
                    });

                

                const notAttendedTasks = await fetch('http://localhost:8080/api/task/notAttended/volunteer/project/' + projectId, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                const suggestedTasks = await fetch('http://localhost:8080/api/task/suggested/volunteer/project/' + projectId, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });


                if (notAttendedTasks.ok) {

                    const resultTasks = await notAttendedTasks.json();

                    const resultSuggestedTasks = await suggestedTasks.json();

                    console.log(resultTasks);

                    console.log(resultSuggestedTasks);

                    const taskToSkip = [];

                    resultTasks.forEach(task1 => {

                        resultSuggestedTasks.forEach(task2 => {

                            if (task1.id == task2.id) {
                                printTasks(task1, root, false, true);
                                taskToSkip.push(task1);
                            }

                        })

                    })

                    

                    resultTasks.forEach(task => {
                        if (!taskToSkip.includes(task))
                        printTasks(task, root, true, false);
                        
                    });

                }
            }

            } catch (error) {
                console.log(error);
            }

        }

        async function joinTask() {

            taskId = this.dataset.taskId;

            try {
                    const response = await fetch(`http://localhost:8080/api/task/${taskId}/suggested/volunteer`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const output = document.createElement("output");
                    output.innerHTML = await response.text();
                    this.replaceWith(output);
                }

            } catch (error) {
                console.log(error);
            }

        }


        function loadContent() {

            getProject();
            getTasks();

        }