var isLoggedIn = sessionStorage.getItem('isLoggedIn');
var loadebeforedata = document.getElementById('loadbeforedata');
var loader = document.getElementById('loader');
var containerid = document.getElementById('containerid');
window.onload = (e) => {
    var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }
    containerid.classList.add('remove');
    if (!(isLoggedIn == "true")) {
        window.location = frontEndUrl+"/sessionExpired.html";
    }
    else {
        var managerImage = document.getElementById('managerImage');
        var managerId = sessionStorage.getItem('managerId');
        managerImage.src = backEndUrl+"/manager/getManagerImageById?managerId=" + managerId;

        var employeeId;
        const params = (new URL(document.location)).searchParams;
        employeeId = params.get('employeeId');

        var empId = document.getElementById('empId');
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
        var empImage = document.getElementById('employeeImage');
        var imageName = document.getElementById('imageName');



        fetch(backEndUrl+"/employee/getEmployeeById?employeeId=" + employeeId, {
            method: 'GET'
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                //to remove the loading spinner after data is loaded
                loadebeforedata.classList.add('displayed');
                loader.classList.add('displayed');
                containerid.classList.remove('remove');
                empId.innerHTML = "  " + data.employeeId;
                empFname.innerHTML = "  " + data.employeeFirstName;
                empLname.innerHTML = "  " + data.employeeLastName;
                empGender.innerHTML = "  " + data.gender;
                empDob.innerHTML = "  " + data.dateOfBirth;
                empPhno.innerHTML = "  " + data.phoneNumber;
                empEmailId.innerHTML = "  " + data.emailId;
                empAddress.innerHTML = "  " + data.address;
                empProjectName.innerHTML = "  " + data.projectName;
                empSkillLevel.innerHTML = "  " + data.skillLevel;
                empSalary.innerHTML = "  " + data.employeeSalary;
                imageName.innerHTML = "  " + data.fileName;

                var imageUrl = backEndUrl+"/employee/getEmployeeImageById?employeeId=" + employeeId;
                empImage.src = imageUrl;

            })
            .catch((error) => {
                var servererror = document.getElementById('servererror');
                servererror.innerHTML = "404 NOT FOUND Server error please try again!"; // To show the custom error msg
                //to remove the loading spinner to display the error msg
                loadebeforedata.classList.add('displayed');
                loader.classList.add('displayed');
            })
    }
}