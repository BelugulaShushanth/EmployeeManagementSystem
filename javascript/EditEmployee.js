var isLoggedIn = sessionStorage.getItem('isLoggedIn');
var loadebeforedata = document.getElementById('loadbeforedata');
var loader = document.getElementById('loader');
var containerid = document.getElementById('containerid');
var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }
window.onload = (e) => {
    containerid.classList.add('remove');
    if (!(isLoggedIn == "true")) {
        window.location = frontEndUrl+"/sessionExpired.html";
    }
    else {
        var managerImage = document.getElementById('managerImage');
        var managerId = sessionStorage.getItem('managerId');
        managerImage.src = backEndUrl+"/manager/getManagerImageById?managerId="+managerId;
        var employeeId;
        const params = (new URL(document.location)).searchParams;
        employeeId = params.get('employeeId');

        // var empId = document.getElementById('empId');
        var empFname = document.getElementById('empFname');
        var empLname = document.getElementById('empLname');
        var empDob = document.getElementById('empDob');
        var empGender = document.getElementById('empGender');
        var empPhno = document.getElementById('empPhNo');
        var empEmailId = document.getElementById('empEmailId');
        var empAddress = document.getElementById('empAddress');
        var empProjectName = document.getElementById('empProjectName');
        var empSkillLevel = document.getElementById('empSkillLevel');
        var empSalary = document.getElementById('empSalary');

        fetch(backEndUrl+"/employee/getEmployeeById?employeeId=" + employeeId, {
            method: 'GET'
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                loadebeforedata.classList.add('displayed');
                loader.classList.add('displayed');
                containerid.classList.remove('remove');
                // empId.value = "  " + data.employeeId;
                empFname.value = data.employeeFirstName;
                empLname.value = data.employeeLastName;
                empGender.value = data.gender;
                empDob.value = data.dateOfBirth;
                empPhno.value = data.phoneNumber;
                empEmailId.value = data.emailId;
                empAddress.value = data.address;
                empProjectName.value = data.projectName;
                empSkillLevel.value = data.skillLevel;
                empSalary.value = data.employeeSalary;

            })
            .catch((error) => {
                var servererror = document.getElementById('servererror');
                servererror.innerHTML = "404 NOT FOUND Server error please try again!"; // To show the custom error msg
                //to remove the loading spinner to display the error msg
                loadebeforedata.classList.add('displayed');
                loader.classList.add('displayed');
            })

        var empFormSubmit = document.getElementById('empFormSubmit');
        empFormSubmit.addEventListener('submit', (e) => {
            e.preventDefault();
            var resText = updateEmployee(employeeId);
            var saveStatus = document.getElementById('saveSuccess');
            saveStatus.classList.add("show");
            var text = document.getElementById('text');
            text.innerHTML = "Employee Updated Successfully with id:" + employeeId;

            setTimeout(() => saveStatus.classList.remove("show"), 3500);
        })

    }
}


async function updateEmployee(employeeId) {
    const response = await fetch(backEndUrl+"/employee/updateEmployeeById/" + employeeId, {
        method: 'PUT',
        body: JSON.stringify(
            {
                "employeeId": employeeId,
                "employeeFirstName": empFname.value,
                "employeeLastName": empLname.value,
                "dateOfBirth": empDob.value,
                "gender": empGender.value,
                "phoneNumber": empPhNo.value,
                "emailId": empEmailId.value,
                "address": empAddress.value,
                "projectName": empProjectName.value,
                "skillLevel": empSkillLevel.value,
                "employeeSalary": empSalary.value
            }
        ),
        headers: {
            "Content-Type": "application/json"
        }
    })
    const result = await response.text();
    const result2 = result;
    return result2;
}