package com.ems.EmployeeController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

import com.ems.ChartBean.EmployeeChartBean;
import com.ems.EmployeeBean.EmployeeBean;
import com.ems.EmployeeService.EmployeeService;
import com.ems.ExceptionHandler.NoEmployeesFound;

@CrossOrigin
@RestController
public class EmployeeController {
	
	@Autowired
	public EmployeeService empService;
	
	@RequestMapping(value = "/employee/addEmployee/{mId}",  method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addEmployee(@RequestBody EmployeeBean employeeBean, @PathVariable("mId") long managerId){
		try {
		long empId = empService.addEmployee(employeeBean,managerId);
		return new ResponseEntity<String>("Employee added successfully with id "+empId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to add employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/employee/addEmployeeImageById/{empId}",  method = RequestMethod.POST,
			 produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addEmployeeImageById(@RequestParam("empImage") MultipartFile empImage, @PathVariable("empId") long employeeId){
		try {
		long empId = empService.addEmployeeImageById(employeeId, empImage);
		return new ResponseEntity<String>("Employee Image added successfully with id:"+empId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to add employee Image", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/employee/getAllEmployees",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeBean>> getAllEmployees(){
		try {
		List<EmployeeBean> empList = empService.getAllEmployees();
		return new ResponseEntity<List<EmployeeBean>>(empList, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<List<EmployeeBean>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/employee/getEmployeeById",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeBean> getEmployeeById(@RequestParam("employeeId") long employeeId){
		try {
		EmployeeBean emp = empService.getEmployeeById(employeeId);
		emp.setEmployeeImage(null);
		return new ResponseEntity<EmployeeBean>(emp, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<EmployeeBean>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/employee/getEmployeeImageById",  method = RequestMethod.GET)
	public ResponseEntity<Resource> getEmployeeImageById(@RequestParam("employeeId") long employeeId){
		try {
		EmployeeBean emp = empService.getEmployeeById(employeeId);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(emp.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + emp.getFileName() + "\"")
				.body(new ByteArrayResource(emp.getEmployeeImage()));
		}
		catch(Exception e){
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value = "/employee/updateEmployeeById/{employeeId}",  method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> updateEmployeeById(@RequestBody EmployeeBean employeeBean, @PathVariable("employeeId") long employeeId){
		try {
		long employeeId2 = empService.updateEmployeeById(employeeBean, employeeId);
		System.out.println("in update");
		return new ResponseEntity<String>("Employee with employee id:"+employeeId2+" is updated successfully", HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Unable to update employee with employee id:"+employeeId+" please check the employee Id and try again", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/employee/deleteEmployeeById/{employeeId}",  method = RequestMethod.DELETE,
			produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("employeeId") long employeeId){
		try {
		long empId = empService.deleteEmployeeById(employeeId);
		return new ResponseEntity<String>("Employee with employee id:"+empId+" is deleted successfully", HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Unable to delete employee with employee id:"+employeeId+" please check the employee Id and try again",HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/employee/deleteAllEmployees",  method = RequestMethod.DELETE,
			produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> deleteEmployeeById(){
		try {
			empService.deleteAllEmployees();
		return new ResponseEntity<String>("All employees are deleted successfully", HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Unable to delete employees please try again",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/employee/getEmployeeByUserId",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeBean> getEmployeeByUserId(@RequestParam("userId") long userId){
		try {
			EmployeeBean emp = empService.getEmployeeByUserId(userId);
			emp.setEmployeeImage(null);
			return new ResponseEntity<EmployeeBean>(emp, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<EmployeeBean>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/employee/getAllEmployeesByManagerId/{managerId}",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeBean>> getAllEmployeesByManagerId(@PathVariable("managerId") long managerId){
		try {
		List<EmployeeBean> empList = empService.getAllEmployeesByManagerId(managerId);
		return new ResponseEntity<List<EmployeeBean>>(empList, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<List<EmployeeBean>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/employee/getEmployeeChartDeatilsByEmployeeId/{employeeId}",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeChartBean> getEmployeeChartDeatilsByEmployeeId(@PathVariable("employeeId") long employeeId){
		try {
			EmployeeChartBean employeeChartBean = empService.getEmployeeChartDetails(employeeId);
			return new ResponseEntity<EmployeeChartBean>(employeeChartBean, HttpStatus.OK);
		}
		catch(Exception e){
			if(e.getClass().equals(new NoEmployeesFound())) {
				return new ResponseEntity<EmployeeChartBean>(HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<EmployeeChartBean>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	}
	
	
	
}
