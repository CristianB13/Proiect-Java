let requests = document.getElementById('requests');
let teachers = document.getElementById('teachers');
let user = document.getElementById('user');
let main = document.getElementById('main');
let myTeacher = document.getElementById('teacher');

requests.addEventListener('click', async () => {
    let teacher = await fetch("http://localhost:8082/student/teacher", {
        method: "GET"
    });
    let res = await fetch("http://localhost:8082/student/requests", {
        method: "GET"
    });
    res = await res.json();
    main.replaceChildren();
    if(teacher.ok) {
        teacher = await teacher.json();
        console.log(teacher);
        for(let i = 0; i < res.length; i++) {
            if(teacher.id == res[i].teacher.id) continue;
            main.appendChild(createRequest(res[i]));
        }
    } else {
        for(let i = 0; i < res.length; i++) {
            main.appendChild(createRequest(res[i]));
        }
    }
})

teachers.addEventListener('click', async () => {
    let res = await fetch("http://localhost:8082/student/teachers", {
        method: "GET"
    });
    res = await res.json();
    main.replaceChildren();
    for(let i = 0; i < res.length; i++) {
        main.appendChild(createTeacher(res[i], true));
    }
});

myTeacher.addEventListener('click', async () => {
    let teacher = await fetch("http://localhost:8082/student/teacher", {
        method: "GET"
    });
    main.replaceChildren();
    if(teacher.ok) {
        teacher = await teacher.json();
        main.appendChild(createTeacher(teacher, false));
    }
});

window.addEventListener('load',async () => {
    let res = await fetch("http://localhost:8082/student", {
        method: "GET"
    });
    res = await res.json();
    user.innerText = `${res.firstName} ${res.lastName}`;
});

function createTeacher(teacher, req) {
    let element = document.createElement('div');
    element.classList = "main-item";
    let id = createId(teacher.id);
    element.appendChild(id);
    let send = createIcon("fa-solid fa-paper-plane");
    if(req) {
        send.addEventListener('click', () => {
            fetch(`http://localhost:8082/student/request/${id.innerText}`, {
                method: "POST",
                headers: {"Content-Type": "application/json"}
            }).then(() => {
                teachers.click();
            });
        })
        element.appendChild(send);
    }
    element.appendChild(createName(`${teacher.firstName} ${teacher.lastName}`));
    return element;
}

function createIcon(iconClass) {
    let icon = document.createElement('div');
    icon.classList = iconClass;
    return icon;
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

function createRequest(request) {
    let element = document.createElement('div');
    element.classList = "main-item";
    if(request.approved != null) {
        if(request.approved) {
            element.classList.add('approved');
        } else {
            element.classList.add('rejected')
        }
    }
    let id = createId(request.id);
    id.setAttribute('data-teacher-id', request.teacher.id);
    element.appendChild(id);
    element.appendChild(createName(`${request.teacher.firstName} ${request.teacher.lastName}`));
    if(request.approved) {
        let add = createIcon('fa-solid fa-plus');
        add.addEventListener('click', () => {
            console.log(id.getAttribute('data-teacher-id'));
            fetch(`http://localhost:8082/student/teacher/${id.getAttribute('data-teacher-id')}`, {
                method: "PUT"
            }).then(() => {
                requests.click();
            });
        });
        element.appendChild(add);
    }
    return element;
}