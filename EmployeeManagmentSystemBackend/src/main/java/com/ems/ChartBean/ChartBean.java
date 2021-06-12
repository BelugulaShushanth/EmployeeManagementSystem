package com.ems.ChartBean;

public class ChartBean {
	
	private int totalNoOfEmployees;
	private int totalNoOfTasks;
	private int noOfemployeesWithTask;
	private int noOfemployeesWithoutTask;
	private int	toDo;
	private int inProgress;
	private int done;
	
	public int getTotalNoOfEmployees() {
		return totalNoOfEmployees;
	}
	public void setTotalNoOfEmployees(int totalNoOfEmployees) {
		this.totalNoOfEmployees = totalNoOfEmployees;
	}
	public int getNoOfemployeesWithTask() {
		return noOfemployeesWithTask;
	}
	public void setNoOfemployeesWithTask(int noOfemployeesWithTask) {
		this.noOfemployeesWithTask = noOfemployeesWithTask;
	}
	public int getNoOfemployeesWithoutTask() {
		return noOfemployeesWithoutTask;
	}
	public void setNoOfemployeesWithoutTask(int noOfemployeesWithoutTask) {
		this.noOfemployeesWithoutTask = noOfemployeesWithoutTask;
	}
	public int getToDo() {
		return toDo;
	}
	public void setToDo(int toDo) {
		this.toDo = toDo;
	}
	public int getInProgress() {
		return inProgress;
	}
	public void setInProgress(int inProgress) {
		this.inProgress = inProgress;
	}
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
	public int getTotalNoOfTasks() {
		return totalNoOfTasks;
	}
	public void setTotalNoOfTasks(int totalNoOfTasks) {
		this.totalNoOfTasks = totalNoOfTasks;
	}
	
	
}
