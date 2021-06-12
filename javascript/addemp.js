var isLoggedIn = sessionStorage.getItem('isLoggedIn');
var containerid = document.getElementById('containerid');
var loadingGifId = document.getElementById('loadingGifId');
var servererror = document.getElementById('servererror');
window.onload = (e) => {

    if (!(isLoggedIn == "true")) {
        window.location = "http://127.0.0.1:5500/sessionExpired.html";
    }
    else {
        var managerImage = document.getElementById('managerImage');
        var mIdTemp = sessionStorage.getItem('managerId');
        managerImage.src = "http://localhost:8080/manager/getManagerImageById?managerId=" + mIdTemp;
        var empFormSubmit = document.getElementById('empFormSubmit');
        empFormSubmit.addEventListener('submit', function (e) {
            e.preventDefault();
            containerid.classList.add('hide');
            loadingGifId.classList.add('display');
            var empFname = document.getElementById('empFname').value;
            var empLname = document.getElementById('empLname').value;
            var empDob = document.getElementById('empDob').value;
            var empGender = document.getElementById('empGender').value;
            var empPhno = document.getElementById('empPhno').value;
            var empEmailId = document.getElementById('empEmailId').value;
            var empAddress = document.getElementById('empAddress').value;
            var empProjectName = document.getElementById('empProjectName').value;
            var empSkillLevel = document.getElementById('empSkillLevel').value;
            var empSalary = document.getElementById('empSalary').value;
            var empImage = document.getElementById('empImage');
            const formdata = new FormData();
            formdata.append("empImage", empImage.files[0]);
            const managerId = sessionStorage.getItem('managerId');
            var status;
            addEmployee(managerId).then((data) => {
                var res = data;
                console.log(res);
                if (status == 500) {
                    containerid.classList.add('remove');
                    containerid.classList.remove('hide');
                    loadingGifId.classList.remove('display');
                    servererror.innerHTML = "Unable to add an employee, Server error please try again!"; // To show the custom error msg
                }
                else {
                    var temp = res.split(" ");
                    var employeeId = temp[temp.length - 1];
                    addEmployeeImage(employeeId).then((data) => {
                        console.log(data);
                        containerid.classList.remove('hide');
                        loadingGifId.classList.remove('display');
                        var saveStatus = document.getElementById('saveSuccess');
                        saveStatus.classList.add("show");
                        var text = document.getElementById('text');
                        text.innerHTML = "Employee Added Successfully with id:" + employeeId;

                        setTimeout(() => saveStatus.classList.remove("show"), 3500);

                        empFormSubmit.reset();

                    })
                        .catch((error) => {
                            containerid.classList.add('remove');
                            containerid.classList.remove('hide');
                            loadingGifId.classList.remove('display');
                            servererror.innerHTML = "Unable to add an employee, Server error please try again!"; // To show the custom error msg
                        })
                }
            })
                .catch((error) => {
                    containerid.classList.add('remove');
                    containerid.classList.remove('hide');
                    loadingGifId.classList.remove('display');
                    servererror.innerHTML = "Unable to add an employee, Server error please try again!"; // To show the custom error msg

                })

            async function addEmployee(managerId) {
                const response = await fetch("http://localhost:8080/employee/addEmployee/" + managerId, {
                    method: 'POST',
                    body: JSON.stringify(
                        {
                            "employeeFirstName": empFname,
                            "employeeLastName": empLname,
                            "dateOfBirth": empDob,
                            "gender": empGender,
                            "phoneNumber": empPhno,
                            "emailId": empEmailId,
                            "address": empAddress,
                            "projectName": empProjectName,
                            "skillLevel": empSkillLevel,
                            "employeeSalary": empSalary
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

            async function addEmployeeImage(employeeId) {
                const response = await fetch("http://localhost:8080/employee/addEmployeeImageById/" + employeeId, {
                    method: 'POST',
                    body: formdata
                })
                const result = response.text();
                return result;
            }

            // window.location = "http://127.0.0.1:5500/ViewEmployees.html" ;


        })
    }
}
