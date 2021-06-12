package com.ems.ManagerDAO;

import org.springframework.data.repository.CrudRepository;

import com.ems.ManagerEntity.ManagerEntity;
import com.ems.UserEntity.UserEntity;


public interface ManagerDAO extends CrudRepository<ManagerEntity, Long>{
	
	public ManagerEntity findByUserEntity(UserEntity userEntity);
}
