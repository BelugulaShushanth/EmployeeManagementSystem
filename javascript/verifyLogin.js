const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password');
togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye / eye slash icon
    this.classList.toggle('bi-eye');
});

var containerid = document.getElementById('containerid');
var loadingGifId = document.getElementById('loadingGifId');
const verifyLogin = document.getElementById('verifyLogin');
var servererror = document.getElementById('servererror');

verifyLogin.addEventListener('submit', (e) => {
    e.preventDefault();
    containerid.classList.add('hide');
    loadingGifId.classList.add('display');

    var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }

    //Getting the uname & pwd id's
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var role = document.getElementById('role').value;

    // console.log(username +" "+ password + " "+role); to test

    //To display a login status
    var verifyCredentialsId = document.getElementById('verifyCredentialsId');
    verifyCredentialsId.classList.remove('notValid');
    //To set the session storage to false by default
    sessionStorage.setItem('isLoggedIn', 'false');

    //to validate a user from the server
    fetch(backEndUrl+"/user/validateUser", {
        method: 'POST',
        body: JSON.stringify(
            {
                "username": username,
                "password": password,
                "role": role
            }
        ),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then((response) => {
            if (response.status == 404) {
                return "404";
            }
            else if(response.status == 500){
                return "500";
            }
            else {
                return response.text();
            }
        })
        .then((data) => {
            console.log(data);  //to test
            if (data == "404") {
                containerid.classList.remove('hide');
                loadingGifId.classList.remove('display');
                verifyCredentialsId.classList.add('notValid');
            }
            else if(data == "500"){
                containerid.classList.add('remove');
                containerid.classList.remove('hide');
                loadingGifId.classList.remove('display');
                servererror.innerHTML = "Unable to Login, Server error please try again!";
            }
            else {
                sessionStorage.setItem('isLoggedIn', 'true');
                sessionStorage.setItem('userId', data);
                verifyLogin.reset();
                containerid.classList.remove('hide');
                loadingGifId.classList.remove('display');
                if(role == "Employee"){
                    window.location = frontEndUrl+"/EmployeeDashboard.html";
                }
                else if(role == "Manager"){
                window.location = frontEndUrl+"/ManagerDashboard.html";
                }
            }
        })
        .catch((error) => {
            containerid.classList.add('remove');
            containerid.classList.remove('hide');
            loadingGifId.classList.remove('display');
            servererror.innerHTML = "Unable to Login, Server error please try again!";
        })
})
