package com.ems.ManagerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ems.ChartBean.ChartBean;
import com.ems.EmployeeBean.EmployeeBean;
import com.ems.EmployeeService.EmployeeService;
import com.ems.ManagerBean.ManagerBean;
import com.ems.ManagerDAO.ManagerDAOWrapper;
import com.ems.ManagerEntity.ManagerEntity;
import com.ems.TaskBean.TaskBean;
import com.ems.TaskService.TaskService;

@Service
public class ManagerService {
	
	@Autowired
	private ManagerDAOWrapper mdaow;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TaskService taskService;
	

	public long addManager(ManagerBean managerBean) throws Exception{
		return mdaow.addManager(managerBean);
	}
	
	public long addManagerImageById(long managerId, MultipartFile managerImage) throws Exception{
		return mdaow.addManagerImageById(managerId, managerImage);
	}
	
	public ManagerBean getManagerById(long managerId) throws Exception{
		return mdaow.getManagerById(managerId);
	}
	
	public ManagerEntity getManagerEntity(ManagerBean managerBean) {
		return mdaow.convertBeanToEntity(managerBean);
	}
	
	public ManagerBean getManagerByUserId(long userId) throws Exception {
		return mdaow.getManagerByUserId(userId);
	}
	
	public ChartBean getChartDetails(long managerId) throws Exception{
		
		ChartBean chartBean = new ChartBean();
		int totalNoOfEmployees;
		List<EmployeeBean> employees = employeeService.getAllEmployeesByManagerId(managerId);
		totalNoOfEmployees = employees.size();
		int noOfemployeesWithTask = 0;
		int noOfemployeesWithoutTask = 0;
		int totalNoOfTasks = 0;
		int toDo = 0;
		int inProgress = 0;
		int done = 0;
		
		if(totalNoOfEmployees != 0) {
		for(EmployeeBean employeeBean: employees) {
			List<TaskBean> tasks = taskService.getAllTasksByEmployeeId(employeeBean.getEmployeeId());
			totalNoOfTasks += tasks.size();
			if(tasks.size() != 0) {
				noOfemployeesWithTask++;
			}
			for(TaskBean taskBean: tasks) {
				if(taskBean.getStatus().equalsIgnoreCase("TO DO"))
					toDo++;
				else if(taskBean.getStatus().equalsIgnoreCase("IN PROGRESS"))
					inProgress++;
				else if(taskBean.getStatus().equalsIgnoreCase("Done"))
					done++;
			}
		}
		noOfemployeesWithoutTask = totalNoOfEmployees - noOfemployeesWithTask;
		}
		
		chartBean.setTotalNoOfEmployees(totalNoOfEmployees);
		chartBean.setTotalNoOfTasks(totalNoOfTasks);
		chartBean.setNoOfemployeesWithTask(noOfemployeesWithTask);
		chartBean.setNoOfemployeesWithoutTask(noOfemployeesWithoutTask);
		chartBean.setToDo(toDo);
		chartBean.setInProgress(inProgress);
		chartBean.setDone(done);
		
		return chartBean;
	}
}
