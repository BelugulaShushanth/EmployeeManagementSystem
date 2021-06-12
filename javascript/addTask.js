var addTaskForm = document.getElementById('addTaskForm');
var containerid = document.getElementById('containerId');
var loadingGifId = document.getElementById('loadingGifId');
var servererror = document.getElementById('servererrorId');
addTaskForm.addEventListener('submit', (e) => {
    e.preventDefault();
    containerid.classList.add('hide');
    loadingGifId.classList.add('display');

    var taskTitle = document.getElementById('taskTitle').value;
    var expectedStartDate = document.getElementById('expectedStartDate').value;
    var expectedEndDate = document.getElementById('expectedEndDate').value;
    var description = document.getElementById('description').value;
    var comments = document.getElementById('comments').value;

    var status;
    var employeeId;
    const params = (new URL(document.location)).searchParams;
    employeeId = params.get('employeeId');
    addTask(employeeId).then((data) => {
        var res = data;
        var temp = res.split(" ");
        var taskId = temp[temp.length - 1];
        console.log(taskId);
        console.log(status);
        if (status == 500) {
            containerid.classList.add('remove');
            containerid.classList.remove('hide');
            loadingGifId.classList.remove('display');
            servererror.innerHTML = "Unable to Add Task Server error please try again!"; // To show the custom error msg
        }
        else {
            addTaskForm.reset();
            containerid.classList.remove('hide');
            loadingGifId.classList.remove('display');
            var alertStatus= document.getElementById('alertStatus');
            alertStatus.classList.add('alert');
            alertStatus.classList.add('alert-success')
            alertStatus.innerHTML = "Task Added successfully with TaskId: "+taskId;
            
            setTimeout(function () {
                alertStatus.innerHTML = "";
                window.location.reload(1);
            }, 2000);
        }
    })
        .catch((error) => {
            console.log(error);
            containerid.classList.add('remove');
            containerid.classList.remove('hide');
            loadingGifId.classList.remove('display');
            console.log("Here");
            servererror.innerHTML = "Unable to Add Task Server error please try again!"; // To show the custom error msg
        })


    async function addTask(employeeId) {
        const response = await fetch("http://localhost:8080/task/addTask/" + employeeId, {
            method: 'POST',
            body: JSON.stringify(
                {
                    "taskTitle": taskTitle,
                    "description" : description,
                    "status" : "TO DO",
                    "expectedStartDate" : expectedStartDate,
                    "expectedEndDate" : expectedEndDate,
                    "comments" : comments
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


})
