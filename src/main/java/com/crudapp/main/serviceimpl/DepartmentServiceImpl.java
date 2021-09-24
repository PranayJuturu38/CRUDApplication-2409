package com.crudapp.main.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
import com.crudapp.main.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private Departmentrepository deptrepo;// using autowired to use the repository in this class

	@Override //Shows all the departments in the db
	public List<Department> getAllDept() {
		List<Department> dept = deptrepo.findAll();
		if (dept.size() > 0) {
			return dept;
		} else {
			return new ArrayList<Department>();
		}
	}

	@Override
	public Department saveDept(Department dept) throws CustomException {

		Optional<Department> newDept = deptrepo.findById(dept.getDeptId());
		Department deptname = deptrepo.getByName(dept.getName());
		if (!newDept.isPresent() && deptname == null) {
			Department newDepartment = new Department();
			newDepartment.setDeptId(dept.getDeptId());
			newDepartment.setName(dept.getName());
			newDepartment.setLocation(dept.getLocation());
			newDepartment.setPersons(dept.getPersons());

			newDepartment = deptrepo.save(newDepartment);
			return newDepartment;
		} else {
			throw new CustomException("Department already exists");
		}
	}

	@Override
	public Department getByDeptId(Integer deptId) throws CustomException {
		Optional<Department> dept = deptrepo.findById(deptId);
		if (dept.isPresent()) {
			return dept.get();
		} else {
			throw new CustomException("No department found with id " + deptId);
		}

	}

	@Override
	public boolean delete(Integer deptId) throws CustomException {
		Optional<Department> dept = deptrepo.findById(deptId);
		if (dept.isPresent()) {

			deptrepo.deleteById(deptId);
			return true;
		} else {
			throw new CustomException("No department found with id: " + deptId);
		}
	}

	@Override
	public Department getByName(String name) throws CustomException {
		Department dept = deptrepo.getByName(name);
		if (dept != null) {
			return dept;
		} else {

			throw new CustomException("No department found with name: " + name);

		}
	}

	@Override
	public List<Department> getByLocation(String location) throws CustomException {
		List<Department> dept = deptrepo.getByLocation(location);
		if (dept.isEmpty()) {
			throw new CustomException("No departments at location " + location);

		} else {
			return dept;
		}
	}

	@Override
	public Department updateDept(Department dept) throws CustomException {
		Optional<Department> existingdept = deptrepo.findById(dept.getDeptId());
		if (existingdept.isPresent()) {
			//Department updateddept = existingdept.get();
			//updateddept.setName(dept.getName());
			existingdept.get().setName(dept.getName());
			existingdept.get().setLocation(dept.getLocation());
			existingdept.get().setPersons(dept.getPersons());
			existingdept.get().setName(dept.getName());
			return deptrepo.save(existingdept.get());
		}

		else {
			throw new CustomException("Department does not exist");
		}

	}

}