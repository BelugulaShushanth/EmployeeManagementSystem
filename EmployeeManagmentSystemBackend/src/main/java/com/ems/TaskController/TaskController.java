package com.ems.TaskController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ems.TaskBean.TaskBean;
import com.ems.TaskService.TaskService;


@CrossOrigin
@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = "/task/addTask/{employeeId}",  method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addTask(@RequestBody TaskBean taskBean, @PathVariable("employeeId") long employeeId){
		try {
		long taskId = taskService.addTask(taskBean, employeeId);
		return new ResponseEntity<String>("Task added successfully with id "+taskId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to add task", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/task/getAllTasks",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TaskBean>> getAllTasks(){
		try {
		List<TaskBean> taskList = taskService.getAllTasks();
		return new ResponseEntity<List<TaskBean>>(taskList, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<List<TaskBean>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/task/getTaskById",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskBean> getTaskById(@RequestParam("taskId") long taskId){
		try {
		TaskBean taskBean = taskService.getTaskById(taskId);
		return new ResponseEntity<TaskBean>(taskBean, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<TaskBean>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/task/getAllTasksByEmployeeId/{employeeId}",  method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TaskBean>> getAllTasksByEmployeeId(@PathVariable("employeeId") long employeeId){
		try {
		List<TaskBean> taskList = taskService.getAllTasksByEmployeeId(employeeId);
		return new ResponseEntity<List<TaskBean>>(taskList, HttpStatus.FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<List<TaskBean>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/task/updateTaskById",  method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> updateTask(@RequestBody TaskBean taskBean){
		try {
		long taskId = taskService.updateTaskById(taskBean);
		return new ResponseEntity<String>("Task updated successfully with id "+taskId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to update task", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/task/updateTaskStatusById",  method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> updateTaskStatusById(@RequestBody TaskBean taskBean){
		try {
		long taskId = taskService.updateTaskStatusById(taskBean);
		return new ResponseEntity<String>("Task status updated successfully with id "+taskId, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Network error! unable to update task", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/task/deleteTaskById/{taskId}",  method = RequestMethod.DELETE,
			produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("taskId") long taskId){
		try {
		long taskId2 = taskService.deleteTaskById(taskId);
		return new ResponseEntity<String>("Task with task id:"+taskId2+" is deleted successfully", HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Unable to delete task with taskid"+taskId+" please check the task Id and try again",HttpStatus.NOT_FOUND);
		}
	}
	
}
