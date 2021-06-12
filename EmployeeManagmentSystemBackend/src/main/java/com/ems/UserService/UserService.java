package com.ems.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.PasswordService.PasswordService;
import com.ems.UserBean.UserBean;
import com.ems.UserDAO.UserDAOWrapper;
import com.ems.UserEntity.UserEntity;

@Service
public class UserService {
	
	@Autowired
	private UserDAOWrapper udaow;
	
	@Autowired
	private PasswordService pService;

	public long addUser(UserBean userBean) throws Exception{
		return udaow.addUser(userBean);
	}
	
	public UserBean getUserById(long userId) throws Exception{
		return udaow.getUserById(userId);
	}
	
	public List<UserBean> getAllUsers() throws Exception{
		return udaow.getAllUsers();
	}
	
	public Long validateUser(UserBean userBean2) throws Exception {
		
		Long userId = null;
		
		boolean userValid = false;
		
		List<UserBean> listUsers = udaow.getAllUsers();
		
		if(listUsers != null) {
		for(UserBean userBeanD : listUsers) {
			String usernameD = userBeanD.getUsername();
			String passwordHashD = userBeanD.getPassword();
			String roleD = userBeanD.getRole();
			boolean verifyPassword = pService.verifyHash(userBean2.getPassword(), passwordHashD);
			boolean verifyUname = userBean2.getUsername().equalsIgnoreCase(usernameD);
			boolean verifyRole = userBean2.getRole().equals(roleD);
			if(verifyPassword && verifyUname && verifyRole) {
				userValid = true;
				userId = userBeanD.getUserId();
				break;
			}
		}
		}
		else {
			System.out.println("No users Found");
		}
		return userId;
	}
	
	public UserEntity convertUBeanToEntity(UserBean userBean) {
		return udaow.convertBeanToEntity(userBean);
	}
}
