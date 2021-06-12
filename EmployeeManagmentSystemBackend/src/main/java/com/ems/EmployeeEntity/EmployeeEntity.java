package com.ems.EmployeeEntity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import com.ems.ManagerEntity.ManagerEntity;
import com.ems.UserEntity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@SequenceGenerator(name="seq", initialValue=10000, allocationSize=0)
@Table(name = "Employee")
public class EmployeeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	//@DateTimeFormat(pattern = "mm-dd-yyyy")
	//@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String dateOfBirth;
	private String gender;
	private long phoneNumber;
	private String emailId;
	private String address;
	private String projectName;
	private int skillLevel;
	private double employeeSalary;
	@Lob
	private byte[] employeeImage;
	private String fileName;
	private String fileType;
	

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "managerId_fk")
	private ManagerEntity managerEntity;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId_fk")
	private UserEntity userEntity;
	
	
	
	
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	public ManagerEntity getManagerEntity() {
		return managerEntity;
	}
	public void setManagerEntity(ManagerEntity managerEntity) {
		this.managerEntity = managerEntity;
	}
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
}
