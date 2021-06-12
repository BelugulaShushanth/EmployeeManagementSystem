package com.ems.EmployeeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ems.ChartBean.EmployeeChartBean;
import com.ems.DAO.EmployeeDAOWrapper;
import com.ems.EmployeeBean.EmployeeBean;
import com.ems.EmployeeEntity.EmployeeEntity;
import com.ems.TaskBean.TaskBean;
import com.ems.TaskService.TaskService;

@Service
public class EmployeeService {
	
	@Lazy
	@Autowired
	public EmployeeDAOWrapper empd;
	
	@Autowired
	public TaskService taskService;
	
	public long addEmployee(EmployeeBean employeeBean, long managerId) throws Exception{
		return empd.addEmployee(employeeBean,managerId);
	}
	
	public long addEmployeeImageById(long employeeId, MultipartFile empImage) throws Exception{
		return empd.addEmployeeImageById(employeeId, empImage);
	}
	
	public List<EmployeeBean> getAllEmployees() throws Exception{
		List<EmployeeBean> empfinal = new ArrayList<EmployeeBean>();
		List<EmployeeBean> empList = empd.getAllEmployees();
		Collections.sort(empList);
		
		for(EmployeeBean emp: empList) {
			emp.setEmployeeImage(null);
			empfinal.add(emp);
		}
		return empfinal;
	}
	
	public EmployeeBean getEmployeeById(long employeeId) throws Exception{
		return empd.getEmployeeById(employeeId);
	}
	
	public long updateEmployeeById(EmployeeBean employeeBean, long employeeId) throws Exception{
		return empd.updateEmployeeById(employeeBean, employeeId);
	}
	
	public long deleteEmployeeById(long employeeId) throws Exception{
		return empd.deleteEmployeeById(employeeId);
	}
	
	public void deleteAllEmployees() throws Exception{
		empd.deleteAllEmployees();
	}
	
	public EmployeeBean getEmployeeByUserId(long userId) throws Exception {
		return empd.getEmployeeByUserId(userId);
	}
	
	public List<EmployeeBean> getAllEmployeesByManagerId(long managerId) throws Exception{
		return empd.getAllEmployeesByManagerId(managerId);
	}
	
	public EmployeeEntity convertBeanToEntity(EmployeeBean employeeBean) {
		return empd.convertBeanToEntity(employeeBean);
	}
	
	public EmployeeChartBean getEmployeeChartDetails(long employeeId) throws Exception {
		EmployeeChartBean employeeChartBean = new EmployeeChartBean();
		int totalNoOfTasks = 0;
		int	toDo = 0;
		int inProgress = 0;
		int done = 0;
		EmployeeBean employeeBean = empd.getEmployeeById(employeeId);
		if(employeeBean != null) {
			List<TaskBean> taskBeans = taskService.getAllTasksByEmployeeId(employeeBean.getEmployeeId());
			totalNoOfTasks = taskBeans.size();
			if(totalNoOfTasks > 0) {
				for(TaskBean taskBean: taskBeans) {
					if(taskBean.getStatus().equalsIgnoreCase("TO DO")) {
						toDo++;
					}
					else if(taskBean.getStatus().equalsIgnoreCase("IN PROGRESS")) {
						inProgress++;
					}
					else if(taskBean.getStatus().equalsIgnoreCase("DONE")) {
						done++;
					}
				}
				employeeChartBean.setToDo(toDo);
				employeeChartBean.setTotalNoOfTasks(totalNoOfTasks);
				employeeChartBean.setInProgress(inProgress);
				employeeChartBean.setDone(done);
			}
			return employeeChartBean;
		}
		return null;
	}
}
