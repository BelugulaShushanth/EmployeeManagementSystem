package com.ems.TaskDAO;


import org.springframework.data.repository.CrudRepository;

import com.ems.EmployeeEntity.EmployeeEntity;
import com.ems.TaskEntity.TaskEntity;

public interface TaskDAO extends CrudRepository<TaskEntity, Long>{
	
	public Iterable<TaskEntity> findAllByEmployeeEntity(EmployeeEntity employeeEntity);
}
