package com.ems.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ems.EmployeeBean.EmployeeBean;
import com.ems.EmployeeEntity.EmployeeEntity;
import com.ems.ManagerEntity.ManagerEntity;
import com.ems.UserEntity.UserEntity;


public interface EmployeeDAO extends CrudRepository<EmployeeEntity, Long> {
	
//	public List<EmployeeEntity> findAllAndOrderByEmployeeIdAsc();
	
	public EmployeeEntity findByUserEntity(UserEntity userEntity);
	public Iterable<EmployeeEntity> findAllByManagerEntity(ManagerEntity managerEntity);
}
