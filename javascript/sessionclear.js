var logout = document.getElementById('logout');
logout.addEventListener('click', (e) => {
    var isLoggedIn = sessionStorage.getItem('isLoggedIn');
    console.log(isLoggedIn);
    sessionStorage.clear();
    window.location = "http://127.0.0.1:5500/loginpage.html";
})