package com.ems.EmployeeBean;

import java.util.Arrays;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeBean implements Comparable<EmployeeBean>{
	
	private long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
//	@DateTimeFormat(pattern = "mm-dd-yyyy")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String dateOfBirth;
	private String gender;
	private long phoneNumber;
	private String emailId;
	private String address;
	private String projectName;
	private int skillLevel;
	private double employeeSalary;
	private byte[] employeeImage;
	private String fileName;
	private String fileType;
	
	
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	public double getEmployeeSalary() {
		return employeeSalary;
	}
	public void setEmployeeSalary(double employeeSalary) {
		this.employeeSalary = employeeSalary;
	}
	public byte[] getEmployeeImage() {
		return employeeImage;
	}
	public void setEmployeeImage(byte[] employeeImage) {
		this.employeeImage = employeeImage;
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
	@Override
	public int compareTo(EmployeeBean o) {
		if(this.employeeId > o.employeeId) {
			return 1;
		}
		else if(this.employeeId < o.employeeId) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "EmployeeBean [employeeId=" + employeeId + ", employeeFirstName=" + employeeFirstName
				+ ", employeeLastName=" + employeeLastName + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender
				+ ", phoneNumber=" + phoneNumber + ", emailId=" + emailId + ", address=" + address + ", projectName="
				+ projectName + ", skillLevel=" + skillLevel + ", employeeSalary=" + employeeSalary + ", employeeImage="
				+ Arrays.toString(employeeImage) + ", fileName=" + fileName + ", fileType=" + fileType + "]";
	}
	
}
