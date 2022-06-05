let requests = document.getElementById('requests');
let students = document.getElementById('students')
let user = document.getElementById('user');
let main = document.getElementById('main');

requests.addEventListener('click', async () => {
    let res = await fetch("http://localhost:8082/teacher/requests", {
        method: "GET"
    });
    res = await res.json();
    main.replaceChildren();
    for(let i = 0; i < res.length; i++) {
        main.appendChild(createRequest(res[i]));
    }
})

students.addEventListener('click', async () => {
    let res = await fetch("http://localhost:8082/teacher/students", {
        method: "GET"
    });
    res = await res.json();
    main.replaceChildren();
    for(let i = 0; i < res.length; i++) {
        main.appendChild(createStudent(res[i]));
    }
});

window.addEventListener('load',async () => {
    let res = await fetch("http://localhost:8082/teacher", {
        method: "GET"
    });
    res = await res.json();
    console.log(res);
    user.innerText = `${res.firstName} ${res.lastName}`;
});

function createStudent(student) {
    let element = document.createElement('div');
    element.classList = "main-item";
    let id = createId(student.id);
    element.appendChild(id);
    element.appendChild(createName(`${student.firstName} ${student.lastName}`));
    return element;
}

function createRequest(request) {
    let element = document.createElement('div');
    element.classList = "main-item";
    let id = createId(request.id);
    element.appendChild(id);
    element.appendChild(createName(`${request.student.firstName} ${request.student.lastName}`));
    if(request.approved != null) {
        if(request.approved) {
            element.classList.add('approved');
        } else {
            element.classList.add('rejected')
        }
    } else {
        let iconContainer = document.createElement('div');
        iconContainer.classList = 'icon-container';
        let accept = createIcon('fa-solid fa-check');
        accept.addEventListener('click', () => {
            fetch(`http://localhost:8082/teacher/request/${id.innerText}`, {
                method: "PUT"
            }).then(() => {
                requests.click();
            });
        });
        let reject = createIcon('fa-solid fa-xmark');
        reject.addEventListener('click', () => {
            fetch(`http://localhost:8082/teacher/request/${id.innerText}`, {
                method: "DELETE"
            }).then(() => {
                requests.click();
            });
        });
        iconContainer.appendChild(reject);
        iconContainer.appendChild(accept);
        element.appendChild(iconContainer);
    }
    return element;
}

function createId(id) {
    let newId = document.createElement('div');
    newId.classList = "id";
    newId.innerHTML = id;
    return newId;
}

function createName(name) {
    let newName = document.createElement('div');
    newName.classList = "name";
    newName.innerHTML = name;
    return newName;
}

function createIcon(iconClass) {
    let icon = document.createElement('div');
    icon.classList = iconClass;
    return icon;
}