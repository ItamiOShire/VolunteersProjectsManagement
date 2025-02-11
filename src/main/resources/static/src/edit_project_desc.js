const urlArray = window.location.href.split("/");
        const projectId = urlArray.at(-1);  

        async function getTags() {

            try {
                const response = await fetch('http://localhost:8080/api/tag/all' , {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                const responseTagsInProject = await fetch('http://localhost:8080/api/project/' + projectId , {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok && responseTagsInProject.ok) {

                    const tags = await response.json();
                    const project = await responseTagsInProject.json();

                    const tagsId = [];

                    project.tags.forEach(tag => {
                        tagsId.push(tag.id)
                    });

                    console.log(tagsId);

                    tags.forEach(tag => {

                        const option = document.createElement("option");

                        option.value = tag.id;
                        option.innerText = tag.desc;
                        option.id = "tagId" + tag.id;

                        if (tagsId.includes(tag.id)){

                            option.selected = "selected";

                        }
                        document.getElementById("tags").appendChild(option);

                    })

                }

                fillFormData();

            } catch (error) {
                console.log(error);
            }
            
        }

        async function fillFormData() {

            try {
                const response = await fetch('http://localhost:8080/api/project/' + projectId , {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {

                    const project = await response.json();

                    document.getElementById("tittle").value = project.title;

                    document.getElementById("desc").value = project.desc;

                }

            } catch (error) {

                console.log(error);

            }

        }

        async function getProject() {

            const root = document.querySelector("section.project-container");

            root.innerHTML = "";
        
            console.log(projectId);

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

    async function updateProject() {

        const form = document.forms["projectForm"];

        const formData = new FormData(form);

        console.log(formData);

        try {
        const response = await fetch("http://localhost:8080/api/project/" + projectId , {
            method: 'PATCH',
            body: formData
        });

        if (response.ok) {
            alert("udało sie zaktualizowac projekt")
            getProject();
        } else {
            alert("nie udalo sie");
        }

        } catch (error) {
            console.log(error);
        }
        
    }

    async function loadContent() {

        await getProject();
        await getTags();
        $(".chosen-select").chosen();

    }

    function goToEditDesc() {
        location.href = "http://localhost:8080/organisation/projects/edit/desc/" + projectId;
    }

    function goToEditTasks() {
        location.href = "http://localhost:8080/organisation/projects/edit/tasks/" + projectId;
    }