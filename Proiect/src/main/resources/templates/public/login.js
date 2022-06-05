let username = document.getElementById("username");
let password = document.getElementById("password");

async function login(type) {
    let form = document.createElement('form');
    let newUsername = document.createElement('input');
    let newPassword = document.createElement('input');
    newUsername.value = `${username.value}:${type}`;
    newPassword.value = password.value;
    newUsername.setAttribute('name', 'username');
    newPassword.setAttribute('name', 'password');
    form.setAttribute('method', 'post');
    form.setAttribute('action', "./login");
    form.appendChild(newUsername);
    form.appendChild(newPassword);
    document.body.appendChild(form);
    form.submit();
}