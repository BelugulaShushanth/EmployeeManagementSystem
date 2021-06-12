var isLoggedIn = sessionStorage.getItem('isLoggedIn');
window.onload = (e) => {
    var backEndUrl, frontEndUrl;
    if(serverDetails.enabled){
        backEndUrl = serverDetails.backEndUrl;
        frontEndUrl = serverDetails.frontEndUrl;
    }
    if (!(isLoggedIn == "true")) {
        window.location = frontEndUrl+"/sessionExpired.html";
    }
    else {
        var welcomeMessage = document.getElementById("welcomeTag");
        welcomeMessage.style.display = "none";
        var chartRow = document.getElementById('chartRow');
        chartRow.style.display = "none";
        var chartId = document.getElementById('piechart');
        chartId.style.display = "none";
        var chart1Id = document.getElementById('piechart1');
        chart1Id.style.display = "none";
        var loadebeforedata = document.getElementById('loadbeforedata');
        var loader = document.getElementById('loader');
        const userId = sessionStorage.getItem('userId');
        fetch(backEndUrl+"/manager/getManagerByUserId?userId=" + userId, {
            method: 'GET',
        })
            .then((response) => {
                return response.json()
            })
            .then((data) => {
                sessionStorage.setItem('managerId', data.managerId);
                var managerImage = document.getElementById('managerImage');
                managerImage.src = backEndUrl+"/manager/getManagerImageById?managerId=" + data.managerId;
                welcomeMessage.innerHTML = "Welcome " + data.managerName;
                var httpStatus;
                var managerId = sessionStorage.getItem('managerId');
                fetch(backEndUrl+"/manager/getChartDetails/" + managerId, {
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
                            var totalNoOfEmployees = data.totalNoOfEmployees;
                            var totalNoOfTasks = data.totalNoOfTasks;
                            var noOfemployeesWithTask = data.noOfemployeesWithTask;
                            var noOfemployeesWithoutTask = data.noOfemployeesWithoutTask;
                            var toDo = data.toDo;
                            var inProgress = data.inProgress;
                            var done = data.done;

                            //for testing
                            // console.log(totalNoOfEmployees);
                            // console.log(noOfemployeesWithTask);
                            // console.log(noOfemployeesWithoutTask);

                            noOfemployeesWithTask = (noOfemployeesWithTask / totalNoOfEmployees) * 24;
                            noOfemployeesWithoutTask = (noOfemployeesWithoutTask / totalNoOfEmployees) * 24;
                            toDo = (toDo / totalNoOfTasks) * 24;
                            inProgress = (inProgress / totalNoOfTasks) * 24;
                            done = (done / totalNoOfTasks) * 24;
                            //console.log(data);
                            // Load google charts
                            google.charts.load('current', { 'packages': ['corechart'] });
                            google.charts.setOnLoadCallback(drawChart);

                            // Draw the chart and set the chart values
                            function drawChart() {
                                var employeedata = google.visualization.arrayToDataTable([
                                    ['Employees', 'IsTaskThere'],
                                    ['Employees With Task', noOfemployeesWithTask],
                                    ['Employess Without Task', noOfemployeesWithoutTask],
                                ]);

                                var taskData = google.visualization.arrayToDataTable([
                                    ['Task', 'status'],
                                    ['To Do', toDo],
                                    ['In Progress', inProgress],
                                    ['Done', done],
                                ]);

                                // Optional; add a title and set the width and height of the chart
                                var options = {
                                    'title': 'Total No Of Employees: ' + totalNoOfEmployees, 'width': 450, 'height': 300,
                                    colors: ['mediumslateblue', 'orangered']
                                };

                                var options1 = {
                                    'title': 'Total No Of Tasks: ' + totalNoOfTasks, 'width': 450, 'height': 300,
                                    colors: ['firebrick', 'orange', 'green']
                                };

                                // Display the chart inside the <div> element with id="piechart"
                                var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                                var chart1 = new google.visualization.PieChart(document.getElementById('piechart1'));
                                //to remove the loading spinner after data is loaded
                                loadebeforedata.classList.add('displayed');
                                loader.classList.add('displayed');
                                welcomeMessage.style.display = "";
                                    chartRow.style.display = "";
                                    chartId.style.display = "";
                                    chart1Id.style.display = "";
                                chart.draw(employeedata, options);
                                chart1.draw(taskData, options1);
                            }
                        }
                    })
            })

    }
}