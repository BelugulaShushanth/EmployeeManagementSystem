var isLoggedIn = sessionStorage.getItem('isLoggedIn');
window.onload = (e) => {
    var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }
    var welcomeMessage = document.getElementById("welcomeTag");
    if (!(isLoggedIn == "true")) {
         window.location = frontEndUrl+"/sessionExpired.html";
    }
    else {
        const userId = sessionStorage.getItem('userId');
        fetch(backEndUrl+"/employee/getEmployeeByUserId?userId="+userId, {
            method: 'GET',
        })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            sessionStorage.setItem('employeeId',data.employeeId);
            var employeeImage = document.getElementById('employeeImage');
            employeeImage.src = backEndUrl+"/employee/getEmployeeImageById?employeeId="+data.employeeId;
            welcomeMessage.innerHTML = "Welcome "+data.employeeFirstName+" "+data.employeeLastName;
            var httpStatus;
            var employeeId = data.employeeId
            fetch(backEndUrl+"/employee/getEmployeeChartDeatilsByEmployeeId/" +employeeId , {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then((response) => {
                    httpStatus = response.status;
                    return response.json();
                })
                .then((data) => {
    
                    if (httpStatus == 200) {
                        var totalNoOfTasks = data.totalNoOfTasks;
                        var toDo = data.toDo;
                        var inProgress = data.inProgress;
                        var done = data.done;
    
                        toDo = (toDo / totalNoOfTasks) * 24;
                        inProgress = (inProgress / totalNoOfTasks) * 24;
                        done = (done / totalNoOfTasks) * 24;
                        //console.log(data);
                        // Load google charts
                        google.charts.load('current', { 'packages': ['corechart'] });
                        google.charts.setOnLoadCallback(drawChart);
    
                        // Draw the chart and set the chart values
                        function drawChart() {
    
                            var taskData = google.visualization.arrayToDataTable([
                                ['Task', 'status'],
                                ['To Do', toDo],
                                ['In Progress', inProgress],
                                ['Done', done],
                            ]);

    
                            var options1 = {
                                'title': 'Total No Of Tasks: ' + totalNoOfTasks, 'width': 450, 'height': 300,
                                colors: ['firebrick', 'orange', 'green']
                            };
    
                            // Display the chart inside the <div> element with id="piechart"
                            var chart1 = new google.visualization.PieChart(document.getElementById('piechart1'));
                            chart1.draw(taskData, options1);
                        }
                    }
                })
        })
                
    }
}