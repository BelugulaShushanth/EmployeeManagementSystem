var logout = document.getElementById('logout');
var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }
logout.addEventListener('click', (e) => {
    var isLoggedIn = sessionStorage.getItem('isLoggedIn');
    console.log(isLoggedIn);
    sessionStorage.clear();
    window.location = frontEndUrl+"/loginpage.html";
})