package com.ems.TaskEntity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ems.EmployeeEntity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Tasks")
@SequenceGenerator(name="seqt", initialValue=300000, allocationSize=0)
public class TaskEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seqt" )
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
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "employeeId_fk")
	private EmployeeEntity employeeEntity;
	
	
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
	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}
	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}
	public String getClosureCriteria() {
		return closureCriteria;
	}
	public void setClosureCriteria(String closureCriteria) {
		this.closureCriteria = closureCriteria;
	}
	
}
