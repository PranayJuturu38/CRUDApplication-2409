package com.crudapp.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crudapp.main.model.Department;
//import com.crudapp.main.requestDTOs.DepartmentDTO;

@Service
public interface DepartmentService {

	List<Department> getAllDept();

	Department saveDept(Department dept);

	Department updateDept(Department dept);

	Department getByDeptId(Integer deptId);

	boolean delete(Integer deptId);

	Department getByName(String name);

	List<Department> getByLocation(String location);
}
