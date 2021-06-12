package com.ems.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ems.EmployeeBean.EmployeeBean;
import com.ems.EmployeeEntity.EmployeeEntity;
import com.ems.ExceptionHandler.NoEmployeesFound;
import com.ems.ExceptionHandler.NoManagersFound;
import com.ems.ExceptionHandler.NoUsersFound;
import com.ems.MailBean.MailBean;
import com.ems.MailService.MailService;
import com.ems.ManagerBean.ManagerBean;
import com.ems.ManagerEntity.ManagerEntity;
import com.ems.ManagerService.ManagerService;
import com.ems.PasswordService.PasswordService;
import com.ems.UserBean.UserBean;
import com.ems.UserEntity.UserEntity;
import com.ems.UserService.UserService;

@Repository
public class EmployeeDAOWrapper {
	
	@Autowired 
	private EmployeeDAO empDao; 
	
	@Autowired
	private ManagerService mService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService pService;
	
	@Autowired
	private MailService mailService;
	
	
	public long addEmployee(EmployeeBean employeeBean, long managerId) throws Exception {
		EmployeeEntity employeeEntity = convertBeanToEntity(employeeBean);
		
		final String password = pService.generatePassoword();
		
		MailBean mailBean = new MailBean();
		mailBean.setReceiverMailId(employeeEntity.getEmailId());
		mailBean.setSubject("Employee Managment System");
		String empName = employeeEntity.getEmployeeFirstName()+" "+employeeEntity.getEmployeeLastName();
		String message = "<h4>Welcome To Employee Management System "+empName+" </h4>"
		+" <br> <label>Please use the below login credential to login to your account</label>"
		+"<br> <label><b>Username</b></label> : "+ employeeEntity.getEmailId()
		+"<br> <label><b>Password</b></label> : "+ password;
		mailBean.setMessage(message);
		
		try {
			mailService.sendMail(mailBean);
		}
		catch(Exception e) {
			throw e;
		}
		
		ManagerBean mbean = mService.getManagerById(managerId);
		ManagerEntity managerEntity = mService.getManagerEntity(mbean);
		employeeEntity.setManagerEntity(managerEntity);
		
		UserBean userBean = new UserBean();
		userBean.setUsername(employeeEntity.getEmailId());
		userBean.setRole("Employee");
		userBean.setPassword(pService.hash(password));
		employeeEntity.setUserEntity(userService.convertUBeanToEntity(userBean));
		
		
		try {
			empDao.save(employeeEntity);
		}
		catch(Exception e) {
			throw e;
		}
		
		return employeeEntity.getEmployeeId();
	}
	
	public long addEmployeeImageById(long employeeId, MultipartFile empImage) throws Exception {
		EmployeeEntity empEntity = null;
		try {
			Optional<EmployeeEntity> op = empDao.findById(employeeId);
			empEntity = op.get();
			if(empEntity!=null){
				String fileName = StringUtils.cleanPath(empImage.getOriginalFilename());
				String fileType = empImage.getContentType();
				empEntity.setEmployeeImage(empImage.getBytes());
				empEntity.setFileName(fileName);
				empEntity.setFileType(fileType);
				empEntity = empDao.save(empEntity);
			}
		}
		catch(Exception e) {
			throw e;
		}
		return empEntity.getEmployeeId();
	}
	
	public List<EmployeeBean> getAllEmployees() throws Exception{
		List<EmployeeBean> empList = new ArrayList<EmployeeBean>();
		List<EmployeeEntity> empEntityList = null;
		
		try {
			empEntityList = (List<EmployeeEntity>) empDao.findAll();
			if(empEntityList!=null) {
				for(EmployeeEntity employeeEntity: empEntityList) {
					empList.add(convertEntityToBean(employeeEntity));
				}
			}
			else {
				throw new NoEmployeesFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return empList;
	}
	
	public EmployeeBean getEmployeeById(long employeeId) throws Exception{
		EmployeeEntity empEntity = null;
		EmployeeBean employeeBean = null;
		try {
			Optional<EmployeeEntity> op = empDao.findById(employeeId);
			empEntity = op.get();
			if(empEntity!=null){
				employeeBean = convertEntityToBean(empEntity);
			}
			else {
				throw new NoEmployeesFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return employeeBean;
	}
	

	public long updateEmployeeById(EmployeeBean employeeBean, long employeeId) throws Exception{
		EmployeeEntity empEntity = null;
		EmployeeEntity emp2 = null;
		try {
			Optional<EmployeeEntity> op = empDao.findById(employeeId);
			empEntity = op.get();
			if(empEntity!=null){
				empEntity.setEmployeeFirstName(employeeBean.getEmployeeFirstName());
				empEntity.setEmployeeLastName(employeeBean.getEmployeeLastName());
				empEntity.setDateOfBirth(employeeBean.getDateOfBirth());
				empEntity.setAddress(employeeBean.getAddress());
				empEntity.setPhoneNumber(employeeBean.getPhoneNumber());
				empEntity.setEmailId(employeeBean.getEmailId());
				empEntity.setEmployeeSalary(employeeBean.getEmployeeSalary());
				empEntity.setGender(employeeBean.getGender());
				empEntity.setSkillLevel(employeeBean.getSkillLevel());
				empEntity.setProjectName(employeeBean.getProjectName());
				emp2 = empDao.save(empEntity);
			}
			else {
				throw new NoEmployeesFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return emp2.getEmployeeId();
	}
	
	public long deleteEmployeeById(long employeeId) throws Exception{
		EmployeeEntity empEntity = null;
		try {
			Optional<EmployeeEntity> op = empDao.findById(employeeId);
			empEntity = op.get();
			if(empEntity!=null){
				empDao.deleteById(employeeId);
			}
			else {
				throw new NoEmployeesFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return empEntity.getEmployeeId();
	}
	
	public void deleteAllEmployees() {
		try {
			empDao.deleteAll();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public EmployeeBean getEmployeeByUserId(long userId) throws Exception {
		
		UserBean userBean = userService.getUserById(userId);
		EmployeeBean employeeBean = null;
		if(userBean != null) {
			UserEntity userEntity = userService.convertUBeanToEntity(userBean);
			EmployeeEntity employeeEntity = empDao.findByUserEntity(userEntity);
			if(employeeEntity != null) {
				employeeBean = convertEntityToBean(employeeEntity);
			}
			else {
				throw new NoEmployeesFound();
			}
			
		}
		else {
			throw new NoUsersFound();
		}
		
		return employeeBean;
	}
	
	public List<EmployeeBean> getAllEmployeesByManagerId(long managerId) throws Exception{
		List<EmployeeBean> employeeBeans = new ArrayList<EmployeeBean>();
		List<EmployeeEntity> empEntities = null;
		ManagerBean managerBean = mService.getManagerById(managerId);
		if(managerBean != null) {
			ManagerEntity managerEntity = mService.getManagerEntity(managerBean);
			empEntities = (List<EmployeeEntity>) empDao.findAllByManagerEntity(managerEntity);
			if(empEntities != null) {
				for(EmployeeEntity employeeEntity: empEntities) {
					employeeBeans.add(convertEntityToBean(employeeEntity));
				}
			}
			else {
				throw new NoEmployeesFound();
			}
		}
		else {
			throw new NoManagersFound();
		}
		
		return employeeBeans;
	}
	
	

	
	public EmployeeEntity convertBeanToEntity(EmployeeBean employeeBean) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		BeanUtils.copyProperties(employeeBean, employeeEntity);
		return employeeEntity;
	}
	public EmployeeBean convertEntityToBean(EmployeeEntity employeeEntity) {
		EmployeeBean employeeBean = new EmployeeBean();
		BeanUtils.copyProperties(employeeEntity, employeeBean);
		return employeeBean;
	}
}
