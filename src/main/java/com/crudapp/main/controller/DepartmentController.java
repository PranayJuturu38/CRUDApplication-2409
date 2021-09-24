package com.crudapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.message.Message;
import com.crudapp.main.model.Department;
import com.crudapp.main.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService deptservice;

	@GetMapping("/departments")
	public List<Department> listAll() {

		return deptservice.getAllDept();
	}

	@GetMapping("/departments/{deptId}")
	public ResponseEntity<Department> getById(@PathVariable Integer deptId) throws CustomException {
    
		Department dept = deptservice.getByDeptId(deptId);
		return new ResponseEntity<Department>(dept, HttpStatus.OK);

	}

	@GetMapping("/departments/name/{name}")
	public ResponseEntity<Object> getFromName(@PathVariable("name") String name) throws CustomException {
		try {
			Department dept = deptservice.getByName(name);
			return new ResponseEntity<>(dept, HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping("/departments/locations/{location}")
	public List<Department> getByLocation(@PathVariable("location") String location) throws Exception {
		try {
			return deptservice.getByLocation(location);

		} catch (Exception e) {
			throw e;

		}
	}

	@PostMapping("/departments")
	public ResponseEntity<Message> add(@RequestBody Department dept) throws Exception {
		String message = "";
		try {
			deptservice.saveDept(dept);
			message = "Department added with id: " + dept.getDeptId();
			return ResponseEntity.status(HttpStatus.OK).body(new Message(message));

		} catch (Exception e)

		{
			message = "Department could not be added:" +e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));

		}

	}

	@PutMapping("/departments/{deptId}")
	public ResponseEntity<Message> update(@RequestBody Department dept, @PathVariable Integer deptId)
			throws Exception {
		String message = "";
		try {
			deptservice.updateDept(dept);
			message = "Updated Department-" + deptId + "Successfully";
			return ResponseEntity.status(HttpStatus.OK).body(new Message(message));
		} catch (Exception e) {

			message = "Update failed:" +e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));

		}
	}

	@DeleteMapping("/departments/{deptId}")
	public ResponseEntity<Message> delete(@PathVariable Integer deptId) throws CustomException {
		String message = "";
		try {

			deptservice.delete(deptId);
			message = "Deleted Department-" + deptId + "Successfully";
			return ResponseEntity.status(HttpStatus.OK).body(new Message(message));
		} catch (Exception e) {
			message = "Couldn't delete the department"+e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));
		}
	}
}
