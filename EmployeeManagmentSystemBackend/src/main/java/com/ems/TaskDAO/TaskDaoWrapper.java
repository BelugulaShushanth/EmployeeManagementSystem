package com.ems.TaskDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ems.EmployeeBean.EmployeeBean;
import com.ems.EmployeeEntity.EmployeeEntity;
import com.ems.EmployeeService.EmployeeService;
import com.ems.ExceptionHandler.NoEmployeesFound;
import com.ems.ExceptionHandler.NoTasksFound;
import com.ems.TaskBean.TaskBean;
import com.ems.TaskEntity.TaskEntity;

@Repository
public class TaskDaoWrapper {
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	public long addTask(TaskBean taskBean, long employeeId) throws Exception {
		EmployeeBean employeeBean = employeeService.getEmployeeById(employeeId);
		TaskEntity taskEntity2 = null;
		if(employeeBean != null) {
			EmployeeEntity emEntity = employeeService.convertBeanToEntity(employeeBean);
			TaskEntity taskEntity = convertBeanToEntity(taskBean);
			taskEntity.setEmployeeEntity(emEntity);
			taskEntity2 = taskDAO.save(taskEntity);
		}
		else {
			throw new NoEmployeesFound();
		}
		return taskEntity2.getTaskId();
	}
	
	public TaskBean getTaskById(long taskId) throws Exception{
		TaskEntity taskEntity= null;
		TaskBean taskBean= null;
		try {
			Optional<TaskEntity> op = taskDAO.findById(taskId);
			taskEntity = op.get();
			if(taskEntity!=null){
				taskBean = convertEntityToBean(taskEntity);
			}
			else {
				throw new NoTasksFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return taskBean;
	}
	
	public List<TaskBean> getAllTasks() throws Exception{
		List<TaskBean> taskList = new ArrayList<TaskBean>();
		List<TaskEntity> taskEntityList = null;
		
		try {
			taskEntityList = (List<TaskEntity>) taskDAO.findAll();
			if(taskEntityList!=null) {
				for(TaskEntity taskEntity: taskEntityList) {
					taskList.add(convertEntityToBean(taskEntity));
				}
			}
			else {
				throw new NoTasksFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return taskList;
	}
	
	public List<TaskBean> getAllTasksByEmployeeId(long employeeId) throws Exception{
		List<TaskBean> taskBeans = new ArrayList<TaskBean>();
		List<TaskEntity> taskEntities = null;
		EmployeeBean employeeBean = employeeService.getEmployeeById(employeeId);
		if(employeeBean != null) {
			EmployeeEntity employeeEntity = employeeService.convertBeanToEntity(employeeBean);
			taskEntities = (List<TaskEntity>) taskDAO.findAllByEmployeeEntity(employeeEntity);
			if(taskEntities != null) {
				for(TaskEntity taskEntity: taskEntities) {
					taskBeans.add(convertEntityToBean(taskEntity));
				}
			}
			else {
				throw new NoTasksFound();
			}
		}
		else {
			throw new NoEmployeesFound();
		}
		
		return taskBeans;
		
	}
	
	public long updateTaskById(TaskBean taskBean) throws Exception {
		Optional<TaskEntity> taskEntities = taskDAO.findById(taskBean.getTaskId());
		TaskEntity taskEntity2 = null;
		TaskEntity taskEntity = taskEntities.get();
		if(taskEntity != null) {
			taskEntity.setTaskTitle(taskBean.getTaskTitle());
			taskEntity.setDescription(taskBean.getDescription());
			taskEntity.setComments(taskBean.getComments());
			taskEntity.setExpectedStartDate(taskBean.getExpectedStartDate());
			taskEntity.setExpectedEndDate(taskBean.getExpectedEndDate());
			taskEntity.setActualStartDate(taskBean.getActualStartDate());
			taskEntity.setActualEndDate(taskBean.getActualEndDate());
			taskEntity.setStatus(taskBean.getStatus());
			taskEntity.setClosureCriteria(taskBean.getClosureCriteria());
			taskEntity2 = taskDAO.save(taskEntity);
		}
		else {
			throw new NoTasksFound();
		}
		return taskEntity2.getTaskId();
	}
	
	public long updateTaskStatusById(TaskBean taskBean) throws Exception {
		Optional<TaskEntity> taskEntities = taskDAO.findById(taskBean.getTaskId());
		TaskEntity taskEntity2 = null;
		TaskEntity taskEntity = taskEntities.get();
		if(taskEntity != null) {
			taskEntity.setStatus(taskBean.getStatus());
			taskEntity2 = taskDAO.save(taskEntity);
		}
		else {
			throw new NoTasksFound();
		}
		return taskEntity2.getTaskId();
	}
	
	public long deleteTaskById(long taskId) throws Exception{
		TaskEntity taskEntity = null;
		try {
			Optional<TaskEntity> op = taskDAO.findById(taskId);
			taskEntity = op.get();
			if(taskEntity!=null){
				taskDAO.deleteById(taskId);
			}
			else {
				throw new NoTasksFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return taskEntity.getTaskId();
	}
	
	public TaskEntity convertBeanToEntity(TaskBean taskBean) {
		TaskEntity taskEntity = new TaskEntity();
		BeanUtils.copyProperties(taskBean, taskEntity);
		return taskEntity;
	}
	public TaskBean convertEntityToBean(TaskEntity taskEntity) {
		TaskBean taskBean = new TaskBean();
		BeanUtils.copyProperties(taskEntity, taskBean);
		return taskBean;
	}
}
