package com.ems.ManagerEntity;


import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ems.UserEntity.UserEntity;


@Entity
@SequenceGenerator(name="sequ", initialValue=200000, allocationSize=0)
@Table(name = "Manager")
public class ManagerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequ")
	private long managerId;
	private String managerName;
	private String managerEmailId;
	private String phoneNo;
	@Lob
	private byte[] managerImage;
	private String fileName;
	private String fileType;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId_fk")
	private UserEntity userEntity;
	
	
	
	
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
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
	@Override
	public String toString() {
		return "ManagerEntity [managerId=" + managerId + ", managerName=" + managerName + ", managerEmailId="
				+ managerEmailId + ", phoneNo=" + phoneNo + ", managerImage=" + Arrays.toString(managerImage)
				+ ", fileName=" + fileName + ", fileType=" + fileType + "]";
	}
	
	
}
