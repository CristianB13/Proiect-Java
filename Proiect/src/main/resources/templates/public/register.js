let username = document.getElementById('username');
let fname = document.getElementById('fname');
let lname = document.getElementById('lname');
let password = document.getElementById('password');
let registerError = document.getElementById('error');

async function register(type) {
    let res = await fetch(`http://localhost:8082/register/${type}`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            firstName: fname.value,
            lastName: lname.value,
            username: username.value,
            password: password.value
        })
    });
    if(res.ok) {
        window.location = "http://localhost:8082/login";
    } else {
        res = await res.text();
        registerError.innerText = res;
    }
}