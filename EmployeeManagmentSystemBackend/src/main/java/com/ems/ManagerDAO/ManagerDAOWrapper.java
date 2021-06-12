package com.ems.ManagerDAO;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ems.ExceptionHandler.NoEmployeesFound;
import com.ems.ExceptionHandler.NoManagersFound;
import com.ems.ExceptionHandler.NoUsersFound;
import com.ems.MailBean.MailBean;
import com.ems.MailService.MailService;
import com.ems.ManagerBean.ManagerBean;
import com.ems.ManagerEntity.ManagerEntity;
import com.ems.PasswordService.PasswordService;
import com.ems.UserBean.UserBean;
import com.ems.UserEntity.UserEntity;
import com.ems.UserService.UserService;


@Repository
public class ManagerDAOWrapper {
	
	@Autowired
	private ManagerDAO mdao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService pService;
	
	@Autowired
	private MailService mailService;
	
	public long addManager(ManagerBean managerBean) throws Exception {
		ManagerEntity managerEntity = convertBeanToEntity(managerBean);
		
		final String password = pService.generatePassoword();
		
		MailBean mailBean = new MailBean();
		mailBean.setReceiverMailId(managerEntity.getManagerEmailId());
		mailBean.setSubject("Employee Managment System");
		String message = "<h4>Welcome To Employee Management System "+managerEntity.getManagerName()+" </h4>"
		+" <br> <label>Please use the below login credential to login to your account</label>"
		+"<br> <label><b>Username</b></label> : "+ managerEntity.getManagerEmailId()
		+"<br> <label><b>Password</b></label> : "+ password;
		mailBean.setMessage(message);
		
		try {
			mailService.sendMail(mailBean);
		}
		catch(Exception e) {
			throw e;
		}
		
		UserBean userBean = new UserBean();
		userBean.setUsername(managerEntity.getManagerEmailId());
		userBean.setRole("Manager");
		userBean.setPassword(pService.hash(password));
		managerEntity.setUserEntity(userService.convertUBeanToEntity(userBean));
		
		
		
		try {
			mdao.save(managerEntity);
			
		}
		catch(Exception e) {
			throw e;
		}
		
		return managerEntity.getManagerId();
	}
	
	public long addManagerImageById(long managerId, MultipartFile managerImage) throws Exception {
		ManagerEntity managerEntity = null;
		try {
			Optional<ManagerEntity> op = mdao.findById(managerId);
			managerEntity = op.get();
			if(managerEntity!=null){
				String fileName = StringUtils.cleanPath(managerImage.getOriginalFilename());
				String fileType = managerImage.getContentType();
				managerEntity.setManagerImage(managerImage.getBytes());
				managerEntity.setFileName(fileName);
				managerEntity.setFileType(fileType);
				managerEntity = mdao.save(managerEntity);
			}
		}
		catch(Exception e) {
			throw e;
		}
		return managerEntity.getManagerId();
	}
	
	public ManagerBean getManagerById(long managerId) throws Exception{
		ManagerEntity managerEntity= null;
		ManagerBean managerBean= null;
		try {
			Optional<ManagerEntity> op = mdao.findById(managerId);
			managerEntity = op.get();
			if(managerEntity!=null){
				managerBean = convertEntityToBean(managerEntity);
			}
			else {
				throw new NoManagersFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return managerBean;
	}
	
	public long deleteManagerById(long managerId) throws Exception{
		ManagerEntity managerEntity = null;
		try {
			Optional<ManagerEntity> op = mdao.findById(managerId);
			managerEntity = op.get();
			if(managerEntity!=null){
				mdao.deleteById(managerId);
			}
			else {
				throw new NoManagersFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return managerEntity.getManagerId();
	}
	
	public ManagerBean getManagerByUserId(long userId) throws Exception {
		UserBean userBean = userService.getUserById(userId);
		ManagerBean managerBean = null;
		
		if(userBean != null) {
			UserEntity userEntity = userService.convertUBeanToEntity(userBean);
			ManagerEntity managerEntity = mdao.findByUserEntity(userEntity);
			if(managerEntity != null) {
				managerBean = convertEntityToBean(managerEntity);
			}
			else {
				throw new NoEmployeesFound();
			}
		}
		else {
			throw new NoUsersFound();
		}
		
		return managerBean;
	}
	
	
	public ManagerEntity convertBeanToEntity(ManagerBean managerBean) {
		ManagerEntity managerEntity = new ManagerEntity();
		BeanUtils.copyProperties(managerBean, managerEntity);
		return managerEntity;
	}
	public ManagerBean convertEntityToBean(ManagerEntity managerEntity) {
		ManagerBean managerBean = new ManagerBean();
		BeanUtils.copyProperties(managerEntity, managerBean);
		return managerBean;
	}
}
