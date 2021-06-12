var viewEmp = document.getElementById("viewEmp");
viewEmp.addEventListener('submit',function(e){
    e.preventDefault();
    fetch("http://localhost:8080/employee/getAllEmployees",{
    method:'GET',
    })
    .then(function(response){
        return response.json()
    })
    .then(function(data){
        console.log(data)
    })
})