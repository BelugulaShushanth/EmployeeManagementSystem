package com.ems.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.TaskBean.TaskBean;
import com.ems.TaskDAO.TaskDaoWrapper;

@Service
public class TaskService {
	
	@Autowired
	private TaskDaoWrapper taskDaoWrapper;
	
	public long addTask(TaskBean taskBean, long employeeId) throws Exception {
		return taskDaoWrapper.addTask(taskBean, employeeId);
	}
	
	public TaskBean getTaskById(long taskId) throws Exception{
		return taskDaoWrapper.getTaskById(taskId);
	}
	
	public List<TaskBean> getAllTasks() throws Exception{
		return taskDaoWrapper.getAllTasks();
	}
	
	public List<TaskBean> getAllTasksByEmployeeId(long employeeId) throws Exception{
		return taskDaoWrapper.getAllTasksByEmployeeId(employeeId);
	}
	
	public long updateTaskById(TaskBean taskBean) throws Exception {
		return taskDaoWrapper.updateTaskById(taskBean);
	}
	
	public long deleteTaskById(long taskId) throws Exception{
		return taskDaoWrapper.deleteTaskById(taskId);
	}
	
	public long updateTaskStatusById(TaskBean taskBean) throws Exception {
		return taskDaoWrapper.updateTaskStatusById(taskBean);
	}
}
