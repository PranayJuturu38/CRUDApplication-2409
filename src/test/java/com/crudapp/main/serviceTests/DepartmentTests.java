package com.crudapp.main.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
import com.crudapp.main.service.DepartmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentTests {

	@Autowired
	private DepartmentService deptService;

	@Mock
	Departmentrepository mockDeptRepo;


	@Test
		// POST Mapping "/departments" Happy Test Case //Mockito
	void DeptSaved() {

		Department expectedDept = new Department();
		expectedDept.setDeptId(8);
		expectedDept.setName("sports");
		expectedDept.setLocation("backgate");

		when(mockDeptRepo.save(expectedDept)).thenReturn(expectedDept);
		Department actualDept = deptService.saveDept(expectedDept);
		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());
	}

	@Test
		// POST Mapping "/departments" Unhappy Test Case //mockito
	void DeptNotSaved() throws Exception {

		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.save(expectedDept)).thenReturn(expectedDept);
		Exception exception = assertThrows(CustomException.class, () -> {
			deptService.saveDept(expectedDept);
		});
		String message = "Department already exists";
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));
	}

	@Test
		// Get all Depts //mockito
	void getAllDepts() {
		Department d1 = new Department(3, "HR", "admin");
		Department d2 = new Department(4, "R&D", "admin");

		List<Department> expectedDeptList = new ArrayList<Department>();
		expectedDeptList.add(d1);
		expectedDeptList.add(d2);

		when(mockDeptRepo.findAll()).thenReturn(expectedDeptList);
		deptService.saveDept(d1);
		deptService.saveDept(d2);

		List<Department> actualDeptList = deptService.getAllDept();

		assertEquals(3, actualDeptList.size());

	}

	@Test
		// Department get by id //mockito
	void deptId() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("health");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));

		deptService.saveDept(expectedDept);

		Department actualDept = deptService.getByDeptId(expectedDept.getDeptId());

		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());
	}

	@Test
		// Mockito
	void deptnoid() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(10);
		expectedDept.setName("noiddept");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));
		Exception exception = assertThrows(CustomException.class, () -> {
			deptService.getByDeptId(expectedDept.getDeptId());
		});
		String message = "No department found with id " + expectedDept.getDeptId();
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));
	}

	@Test
		// Department get by name //mockito
	void deptName() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(6);
		expectedDept.setName("getbyname");
		expectedDept.setLocation("main");

		when(mockDeptRepo.getByName(expectedDept.getName())).thenReturn(expectedDept);

		deptService.saveDept(expectedDept);
		Department actualDept = deptService.getByName(expectedDept.getName());
		assertEquals(expectedDept.getName(), actualDept.getName());
	}

	@Test
		// mockito
	void deptnoname() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("main");

		when(mockDeptRepo.getByName(expectedDept.getName())).thenReturn(expectedDept);
		Exception exception = assertThrows(CustomException.class, () -> {
			deptService.getByName(expectedDept.getName());
		});
		String message = "No department found with name: " + expectedDept.getName();
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));

	}

	@Test	// Department get by location //mockito
	void deptLocation() {

		Department expectedDept = new Department();

		expectedDept.setDeptId(7);
		expectedDept.setName("getbylocation");
		expectedDept.setLocation("main");
		List<Department> deptList = new ArrayList<Department>();
		deptList.add(expectedDept);
		when(mockDeptRepo.getByLocation(expectedDept.getLocation())).thenReturn(deptList);

		deptService.saveDept(expectedDept);

		List<Department> actualDept = deptService.getByLocation(expectedDept.getLocation());

		assertEquals(deptList.size(), 1);
	}

	@Test
		// mockito
	void deptNoLocation() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("admin");
		expectedDept.setLocation("nolocation");
		List<Department> expectedList = new ArrayList<Department>();
		expectedList.add(expectedDept);
		when(mockDeptRepo.getByLocation(expectedDept.getLocation())).thenReturn(expectedList);

		List<Department> actualList = new ArrayList<Department>();
		Exception exception = assertThrows(CustomException.class, () -> {
			deptService.getByLocation(expectedDept.getLocation());
		});
		String message = "No departments at location " + expectedDept.getLocation();
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));

	}

	@Test // update department details //mockito
	void updateDepartment() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(5);
		expectedDept.setName("dept1");
		expectedDept.setLocation("main");

		deptService.saveDept(expectedDept);
		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));
		Department actualDept = deptService.getByDeptId(expectedDept.getDeptId());
		assertEquals(expectedDept.getDeptId(), actualDept.getDeptId());
		Department dummyDept = new Department(5,"updated","location");
		Department updatedDept = deptService.updateDept(dummyDept);
		assertNotEquals("dept1",updatedDept.getName());
	}

	@Test
	void updateFailedDept() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(1);
		expectedDept.setName("updatefailed");
		expectedDept.setLocation("main");

		when(mockDeptRepo.findById(expectedDept.getDeptId())).thenReturn(Optional.of(expectedDept));
        Department dummyDept = new Department(9,"fails","location");
		Exception exception = assertThrows(CustomException.class,()->{
			deptService.updateDept(dummyDept);
		});
		String message ="Department does not exist";
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));

	}

	@Test // Delete Department //mockito
	void deleteDepartment() {
		Department expectedDept = new Department();
		expectedDept.setDeptId(2);
		expectedDept.setName("tobedeleted");
		expectedDept.setLocation("main");

		deptService.saveDept(expectedDept);
		doNothing().when(mockDeptRepo).deleteById(expectedDept.getDeptId());

		boolean actualDepartment = deptService.delete(expectedDept.getDeptId());
		assertEquals(actualDepartment, true);
	}

    @Test
	void deleteFailure(){
		Department expectedDept = new Department();
		expectedDept.setDeptId(10);
		expectedDept.setName("tobedeleted");
		expectedDept.setLocation("main");
		doNothing().when(mockDeptRepo).deleteById(expectedDept.getDeptId());
		Exception exception = assertThrows(CustomException.class,()->{
			deptService.delete(expectedDept.getDeptId());
		});
		String message ="No department found with id: "+expectedDept.getDeptId();
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));

	}

}