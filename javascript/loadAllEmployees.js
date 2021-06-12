//Session Handling
var isLoggedIn = sessionStorage.getItem('isLoggedIn');
var servererrorId = document.getElementById('servererrorId');
var empstatusId = document.getElementById('empstatusId');
var searchEmpForm = document.getElementById('searchEmpForm');
var isSearched = false;

function searchEmpByName() {
    var searchEmpName = document.getElementById('searchEmpId').value;
    var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }
    // console.log(isLoggedIn);
    if (!(isLoggedIn == "true")) {
        window.location = frontEndUrl+"/sessionExpired.html";
    }
    else {
        //To load the loading spinner
        var loadebeforedata = document.getElementById('loadbeforedata');
        var loader = document.getElementById('loader');

        //To fetch the all employee details
        const managerId = sessionStorage.getItem('managerId');
        var managerImage = document.getElementById('managerImage');
        managerImage.src = backEndUrl+"/manager/getManagerImageById?managerId=" + managerId;
        fetch(backEndUrl+"/employee/getAllEmployeesByManagerId/" + managerId, {
            method: 'GET',
        })
            .then(function (response) {
                return response.json()
            })
            .then(function (data) {
                //to remove the loading spinner after data is loaded
                loadebeforedata.classList.add('displayed');
                loader.classList.add('displayed');

                var res = data; // storing data in res variable
                if( res.length == 0) {
                    empstatusId.classList.add("empstatus");
                    empstatusId.innerHTML = "No Employees Found";
                }
                else{
                var container = document.getElementById('contain'); //To get the container and store the data
                
                var rowf = document.createElement("div"); // to create row header
                rowf.className = "row first";

                var colf = document.createElement("div"); //to create coloumn
                colf.className = "col";
                var labelf = document.createElement("label");
                labelf.innerHTML = "<b>EmployeeId<b>";
                colf.appendChild(labelf);
                rowf.appendChild(colf);

                var colf1 = document.createElement("div");
                colf1.className = "col";
                var labelf1 = document.createElement("label");
                labelf1.innerHTML = "<b>Employee Name<b>";
                colf1.appendChild(labelf1);
                rowf.appendChild(colf1);

                var colf2 = document.createElement("div");
                colf2.className = "col";
                var labelf2 = document.createElement("label");
                labelf2.innerHTML = "<b>Phone Number<b>";
                colf2.appendChild(labelf2);
                rowf.appendChild(colf2);


                var colf4 = document.createElement("div");
                colf4.className = "col";
                var labelf4 = document.createElement("label");
                labelf4.innerHTML = "<b>Project Name<b>";
                colf4.appendChild(labelf4);
                rowf.appendChild(colf4);

                var colf5 = document.createElement("div");
                colf5.className = "col";
                var labelf5 = document.createElement("label");
                labelf5.innerHTML = "<b>Skill Level<b>";
                colf5.appendChild(labelf5);
                rowf.appendChild(colf5);
                if(isSearched){
                    container.innerHTML = "";
                }
                if(searchEmpName == ""){
                container.appendChild(rowf); // appending the row header to container
                } 
                else{
                    container.innerHTML = "";
                    container.appendChild(rowf);
                } 
                for (let i = 0; i < res.length; i++) { //iterating throw all the employee objects obtained from API.
                    // console.log(res[i]); // logging all the objects for testing

                    var row = document.createElement("div"); // row creation
                    row.setAttribute('class', 'row');

                    var col = document.createElement("div"); // coloumn creation
                    col.setAttribute('class', 'col');


                    //For Dropdown options
                    var dropdown = document.createElement("div");
                    dropdown.setAttribute('class', 'dropdown1');

                    //For three dots image
                    var image = document.createElement("img");
                    image.src = "./icons/threedots1.png";
                    image.setAttribute('class', 'image3');
                    dropdown.appendChild(image);



                    var dropdowncontent = document.createElement("div");
                    dropdowncontent.setAttribute('class', 'dropdowncontent1');

                    // var dropdownlabel = document.createElement("label");
                    // dropdownlabel.setAttribute('class', 'dropdown');

                    var ul = document.createElement("ul");
                    ul.setAttribute('class', 'dropdownul');

                    //View details
                    var li1 = document.createElement("li");
                    li1.setAttribute('class', 'dropdownli');
                    var a1 = document.createElement("a");
                    a1.setAttribute('class', 'ddlink');
                    a1.innerHTML = "View Details";
                    var post_url = frontEndUrl+'/viewASingleEmployeeDetails.html?employeeId=' + res[i].employeeId;
                    a1.href = post_url;
                    li1.appendChild(a1);
                    ul.appendChild(li1);

                    //Edit
                    var li2 = document.createElement("li");
                    li2.setAttribute('class', 'dropdownli');
                    var a2 = document.createElement("a");
                    a2.setAttribute('class', 'ddlink');
                    a2.innerHTML = "Edit";
                    var edit_url = frontEndUrl+'/EditEmployee.html?employeeId=' + res[i].employeeId;
                    a2.href = edit_url;
                    li2.appendChild(a2);
                    ul.appendChild(li2);

                    //Delete
                    var li3 = document.createElement("li");
                    li3.setAttribute('class', 'dropdownli');
                    var a3 = document.createElement("label");
                    a3.setAttribute('class', 'ddlink');
                    a3.innerHTML = "Delete";
                    a3.setAttribute('id', 'deleteemp');
                    li3.appendChild(a3);
                    ul.appendChild(li3);

                    //Hitting the delete api to delete the employee by id
                    a3.addEventListener('click', (e) => {
                        e.preventDefault();
                        fetch(backEndUrl+"/employee/deleteEmployeeById/" + res[i].employeeId, {
                            method: 'DELETE'
                        })
                            .then((response) => {
                                return response.text();
                            })
                            .then((data) => {

                                deleteEmpById(res[i].employeeId);

                                setTimeout(function () {
                                    window.location.reload(1);
                                }, 3000);
                                // var delStatus = document.getElementById('delSuccess');
                                // delStatus.classList.add("show");
                                // var deltext = document.getElementById('deltext');
                                // deltext.innerHTML = "Employee Deleted Successfully with employee Id:" + res[i].employeeId;

                                // setTimeout(() => delStatus.classList.remove("show"), 4500);
                            })
                    })

                    //View Tasks
                    var li4 = document.createElement("li");
                    li4.setAttribute('class', 'dropdownli');
                    var a4 = document.createElement("a");
                    a4.setAttribute('class', 'ddlink');
                    a4.innerHTML = "View Tasks";
                    a4.href = frontEndUrl+'/ViewTasks.html?employeeId=' + res[i].employeeId;
                    li4.appendChild(a4);
                    ul.appendChild(li4);

                    //Appending all the drop down options
                    dropdowncontent.appendChild(ul);
                    dropdown.appendChild(dropdowncontent);
                    col.append(dropdown);
                    //Drop down end


                    //For empId data
                    var anchor = document.createElement("label");
                    anchor.innerHTML = res[i].employeeId;
                    anchor.setAttribute('id', "employeeId");
                    // anchor.href = post_url;
                    col.appendChild(anchor);
                    row.appendChild(col);

                    //For employee Name data
                    var col1 = document.createElement("div");
                    col1.className = "col";
                    var label1 = document.createElement("label");
                    label1.innerHTML = res[i].employeeFirstName + " " + res[i].employeeLastName;
                    col1.appendChild(label1);
                    row.appendChild(col1);

                    //For employee phone number data
                    var col2 = document.createElement("div");
                    col2.className = "col";
                    var label2 = document.createElement("label");
                    label2.innerHTML = res[i].phoneNumber;
                    col2.appendChild(label2);
                    row.appendChild(col2);

                    // var col3 = document.createElement("div");
                    // col3.className = "col";
                    // var label3 = document.createElement("label");
                    // label3.setAttribute('class', 'email');
                    // label3.innerHTML = res[i].emailId;
                    // col3.appendChild(label3);
                    // row.appendChild(col3);

                    //For employee project name data
                    var col4 = document.createElement("div");
                    col4.className = "col";
                    var label4 = document.createElement("label");
                    label4.innerHTML = res[i].projectName;
                    col4.appendChild(label4);
                    row.appendChild(col4);

                    //For emmployee skill level data
                    var col5 = document.createElement("div");
                    col5.className = "col";
                    var label5 = document.createElement("label");
                    label5.innerHTML = res[i].skillLevel;
                    col5.appendChild(label5);
                    row.appendChild(col5);

                    // var svg = document.createElement('svg');
                    // svg.xmlns = "http://www.w3.org/2000/svg";
                    // svg.width  = "16"; svg.height = "16";


                    var empName = res[i].employeeFirstName + " " + res[i].employeeLastName;
                    empName = empName.toLowerCase();
                    searchEmpName = searchEmpName.toLowerCase();
                    if(searchEmpName == ""){
                        container.appendChild(row);
                    }
                    else{
                        isSearched = true;
                        if(empName.includes(searchEmpName)){
                            container.appendChild(row);
                        }
                    }
                }
            }

                //This is to show the pop up message after deleting the employee by emploeeId 
                function deleteEmpById(employeeId) {
                    var rowstatus = document.createElement('div');
                    rowstatus.setAttribute('class', 'row');

                    var colstatus = document.createElement('div');
                    colstatus.setAttribute('class', 'col');

                    var delStatus = document.createElement('div');
                    delStatus.setAttribute('class', 'delStatus');
                    delStatus.setAttribute('id', 'delSuccess');

                    var text = document.createElement('div');
                    text.setAttribute('id', 'text');

                    delStatus.classList.add("show");
                    text.innerHTML = "Employee Deleted Successfully with employee Id:" + employeeId;
                    // console.log("In console Employee Deleted Successfully with employee Id:" + employeeId);
                    setTimeout(() => delStatus.classList.remove("show"), 2800);

                    delStatus.appendChild(text);
                    colstatus.appendChild(delStatus);
                    rowstatus.appendChild(colstatus);
                    container.appendChild(rowstatus);
                }

            })
            .catch((error) => { //to catch the errors if any occured while fetching the api
                 //console.log(error);
                servererrorId.classList.add('servererror');
                servererrorId.innerHTML = "404 NOT FOUND Server error please try again!"; // To show the custom error msg

                //to remove the loading spinner to display the error msg
                loadebeforedata.classList.add('displayed');
                loader.classList.add('displayed');
            })
        
    }
}
searchEmpByName();