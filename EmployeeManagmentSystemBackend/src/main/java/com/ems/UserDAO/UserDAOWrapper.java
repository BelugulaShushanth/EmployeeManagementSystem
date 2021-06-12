package com.ems.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ems.ExceptionHandler.NoUsersFound;
import com.ems.UserBean.UserBean;
import com.ems.UserEntity.UserEntity;

@Repository
public class UserDAOWrapper {
	
	@Autowired
	private UserDAO uDAO;
	
	public long addUser(UserBean userBean) throws Exception {
		UserEntity userEntity = convertBeanToEntity(userBean);
		try {
			uDAO.save(userEntity);
		}
		catch(Exception e) {
			throw e;
		}
		
		return userEntity.getUserId();
	}
	
	public UserBean getUserById(long userId) throws Exception{
		UserEntity userEntity = null;
		UserBean userBean = null;
		try {
			Optional<UserEntity> op = uDAO.findById(userId);
			userEntity = op.get();
			if(userEntity!=null){
				userBean = convertEntityToBean(userEntity);
			}
			else {
				System.out.println();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return userBean;
	}
	
	public List<UserBean> getAllUsers() throws Exception{
		List<UserBean> userList = new ArrayList<UserBean>();
		List<UserEntity> userEntityList = null;
		
		try {
			userEntityList = (List<UserEntity>) uDAO.findAll();
			if(userEntityList!=null) {
				for(UserEntity userEntity: userEntityList) {
					userList.add(convertEntityToBean(userEntity));
				}
			}
			else {
				throw new NoUsersFound();
			}
		}
		catch(Exception e) {
			throw e;
		}
		return userList;
	}
	
	public UserEntity convertBeanToEntity(UserBean userBean) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userBean, userEntity);
		return userEntity;
	}
	public UserBean convertEntityToBean(UserEntity userEntity) {
		UserBean userBean = new UserBean();
		BeanUtils.copyProperties(userEntity, userBean);
		return userBean;
	}
}
