var managerForm = document.getElementById('managerFormSubmit');
var containerid = document.getElementById('containerid');
var loadingGifId = document.getElementById('loadingGifId');
var servererror = document.getElementById('servererror');

var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }

managerForm.addEventListener('submit', (e) => {
    e.preventDefault();
    containerid.classList.add('hide');
    loadingGifId.classList.add('display');
    var fName = document.getElementById('managerFname').value;
    var lName = document.getElementById('managerLname').value;
    var name = fName + " " + lName;
    var phNo = document.getElementById('managerPhno').value;
    var mailId = document.getElementById('managerEmailId').value;
    var managerImage = document.getElementById('managerImage');
    var formdata = new FormData();
    formdata.append("managerImage", managerImage.files[0]);

    var status;
    addManager().then((data) => {
        var res = data;
        var temp = res.split(" ");
        var managerId = temp[temp.length - 1];
        if (status == 500) {
            containerid.classList.add('remove');
            containerid.classList.remove('hide');
            loadingGifId.classList.remove('display');
            servererror.innerHTML = "Unable to create account Server error please try again!"; // To show the custom error msg
        }
        else {
            addManagerImage(managerId).then((data) => {
                managerForm.reset();
                containerid.classList.remove('hide');
                loadingGifId.classList.remove('display');
                window.location = frontEndUrl+"/AccCreated.html";
                var res = data;
            })
                .catch((error) => {
                    containerid.classList.add('remove');
                    containerid.classList.remove('hide');
                    loadingGifId.classList.remove('display');
                    servererror.innerHTML = "Unable to create account Server error please try again!"; // To show the custom error msg
                })
        }
    })
        .catch((error) => {
            containerid.classList.add('remove');
            containerid.classList.remove('hide');
            loadingGifId.classList.remove('display');
            servererror.innerHTML = "Unable to create account Server error please try again!"; // To show the custom error msg
        })


    async function addManager() {
        const response = await fetch(backEndUrl+"/manager/addManager", {
            method: 'POST',
            body: JSON.stringify(
                {
                    "managerName": name,
                    "managerEmailId": mailId,
                    "phoneNo": phNo
                }
            ),
            headers: {
                "Content-Type": "application/json"
            }
        })
        status = response.status;
        const result = await response.text();
        const result2 = result;
        return result2;
    }

    async function addManagerImage(managerId) {
        const response = await fetch(backEndUrl+"/manager/addManagerImageById/" + managerId, {
            method: 'POST',
            body: formdata
        })
        const result = response.text();
        return result;
    }
})
