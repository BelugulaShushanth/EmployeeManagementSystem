package com.ems.ManagerBean;

public class ManagerBean {
	
	private long managerId;
	private String managerName;
	private String managerEmailId;
	private String phoneNo;
	private byte[] managerImage;
	private String fileName;
	private String fileType;
	
	public long getManagerId() {
		return managerId;
	}
	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerEmailId() {
		return managerEmailId;
	}
	public void setManagerEmailId(String managerEmailId) {
		this.managerEmailId = managerEmailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public byte[] getManagerImage() {
		return managerImage;
	}
	public void setManagerImage(byte[] managerImage) {
		this.managerImage = managerImage;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}
