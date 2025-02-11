const root = document.getElementsByClassName("project-container")[0];
        const urlArray = window.location.href.split("/");
        const projectId = urlArray.at(-1);


        async function getProject() {
        
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

                    const joinButton = document.createElement("button");
                    joinButton.type = "button";
                    joinButton.classList.add("project-join-button");
                    joinButton.innerHTML = "Dołącz do projektu";
                    joinButton.addEventListener("click", showPopup);

                    imgContainer.appendChild(img);

                    textContainer.appendChild(projectTitle);
                    textContainer.appendChild(projectShortDesc);
                    textContainer.appendChild(projectLongDesc);

                    tagsContainer.appendChild(tags);

                    textContainer.appendChild(tagsContainer);

                    textContainer.appendChild(joinButton);

                    root.appendChild(imgContainer);
                    root.appendChild(textContainer);

                } else {
                    console.log("blad serwera");
                }

            } catch (error) {
                console.log(error);
            }
        }

        async function joinProject() {

            try {
                const response = await fetch('http://localhost:8080/api/project/join/' + projectId, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    console.log(await response.text());
                    location.href = "http://localhost:8080/volunteer/profile"
                } else {
                    console.log("cos poszlo nie tak - ", await response.text());
                }

            } catch (error) {
                console.log(error);
            }
        }

        function showPopup() {
            document.querySelector(".mask").style.display = "block";
        }

        document.getElementById("popup-confirm").addEventListener("change", function() {
            if(this.checked){
                document.getElementById("popup-yes-button").disabled = false;
            } else {
                document.getElementById("popup-yes-button").disabled = true;
            }
        })
        
        document.getElementById("popup-no-button").addEventListener("click", function() {
            document.querySelector(".mask").style.display = "none";
        })
