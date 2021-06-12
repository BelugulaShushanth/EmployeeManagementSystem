package com.ems.UserDAO;

import org.springframework.data.repository.CrudRepository;

import com.ems.UserEntity.UserEntity;

public interface UserDAO extends CrudRepository<UserEntity, Long> {

}
