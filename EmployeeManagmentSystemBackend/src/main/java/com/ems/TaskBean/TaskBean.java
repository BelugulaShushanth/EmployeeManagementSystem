package com.ems.TaskBean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskBean {
	
	private long taskId;
	private String taskTitle;
	private String description;
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expectedStartDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expectedEndDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date actualStartDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date actualEndDate;
	private String comments;
	private String closureCriteria;
	
	
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getExpectedStartDate() {
		return expectedStartDate;
	}
	public void setExpectedStartDate(Date expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}
	public Date getExpectedEndDate() {
		return expectedEndDate;
	}
	public void setExpectedEndDate(Date expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}
	public Date getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	public Date getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getClosureCriteria() {
		return closureCriteria;
	}
	public void setClosureCriteria(String closureCriteria) {
		this.closureCriteria = closureCriteria;
	}
	
	
	
}
