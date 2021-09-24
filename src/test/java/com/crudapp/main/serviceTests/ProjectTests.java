package com.crudapp.main.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Project;
import com.crudapp.main.repository.Projectrepository;
import com.crudapp.main.service.ProjectService;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectTests {

	@Autowired
	private ProjectService projectService;

	@Mock
	Projectrepository mockProjectRepository;

	@Test // Get all //mockito //
	void getAllProjects() {

		List<Project> expectedList = new ArrayList<Project>();
        Project p1 = new Project(3,"name","description");
		Project p2 = new Project(4,"local","description");

		expectedList.add(p1);
		expectedList.add(p2);
		when(mockProjectRepository.findAll()).thenReturn(expectedList);
         projectService.saveProject(p1);
		projectService.saveProject(p2);

		List<Project> actualList = projectService.getAllProj();

		assertEquals(3, actualList.size());

	}
	@Test // POST Mapping "/projects" Happy TestCase //Mockito
	void projectSaved() {
		Project expectedProject = new Project();
		expectedProject.setId(6);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");


		when(mockProjectRepository.save(expectedProject)).thenReturn(expectedProject);
		Project actualProject = projectService.saveProject(expectedProject);
		assertEquals(expectedProject.getId(), actualProject.getId());
	}

	@Test // Project Not saved //Mockito
	void projectNotSaved() throws Exception {
		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("MobileApplication");
		expectedProject.setDescription("used by patients");

		when(mockProjectRepository.save(expectedProject)).thenReturn(expectedProject);


		Exception exception = assertThrows(CustomException.class,()->{
			projectService.saveProject(expectedProject);
		});
        String message ="Project already exists";
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));
	}

	@Test // Project get by id "/projects/{id}"//Mockito
	void projectID() {

		Project expectedProject = new Project();
		expectedProject.setId(5);
		expectedProject.setName("web");
		expectedProject.setDescription("used by patients");

		// Checking the project in the repo
		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));

	    projectService.saveProject(expectedProject);// saving in the db

		Project actualProject = projectService.getProjectById(expectedProject.getId());

		assertEquals(expectedProject.getId(), actualProject.getId());

	}

	@Test // Project get by id unhappy //mockito //
	void projectNoID() throws Exception {

		Project expectedProject = new Project();
		expectedProject.setId(9);
		expectedProject.setName("local");
		expectedProject.setDescription("used by the staff");

		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.empty());
		Exception exception = assertThrows(CustomException.class,()->{
			projectService.getProjectById(expectedProject.getId());
		});
		String message ="No projects found with id:"+expectedProject.getId();
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));
	}

	@Test // project updated //Mockito
	void updateProject() {
		Project expectedProject = new Project();
		expectedProject.setId(1);
		expectedProject.setName("project1");
		expectedProject.setDescription("used by patients");

		projectService.saveProject(expectedProject);

		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));
		Project actualProject = projectService.getProjectById(expectedProject.getId());
		assertEquals(expectedProject.getId(), actualProject.getId());
		Project dummyProject = new Project(1,"update","description");
		Project updatedProject = projectService.updateProject(dummyProject);
		assertNotEquals("project1",updatedProject.getName());
	}

	@Test // project update failed //mockito //
	void updateProjectFails() throws Exception {
		Project expectedProject = new Project();
		expectedProject.setId(5);
		expectedProject.setName("project1");
		expectedProject.setDescription("used by patients");


		when(mockProjectRepository.findById(expectedProject.getId())).thenReturn(Optional.of(expectedProject));
     	Project dummyProject = new Project(6,"update","description");
		Exception exception = assertThrows(CustomException.class,()->{
			projectService.updateProject(dummyProject);
		});
		String message ="Project not found";
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));
	}


	@Test // project delete
	void projectDeleteFailure() {

		Project expectedProject = new Project();
		expectedProject.setId(10);
		expectedProject.setName("To be deleted");
		expectedProject.setDescription("used by patients");

		doNothing().when(mockProjectRepository).deleteById(expectedProject.getId());
		Exception exception = assertThrows(CustomException.class,()->{
			projectService.delete(expectedProject.getId());
		});
		String message ="No projects found with id:"+expectedProject.getId();
		String expectedMessage = exception.getMessage();
		assertTrue(message.contains(expectedMessage));
	}

	@Test // project delete
	void projectDelete() {

		Project expectedProject = new Project();
		expectedProject.setId(2);
		expectedProject.setName("To be deleted");
		expectedProject.setDescription("used by patients");

		projectService.saveProject(expectedProject);

		doNothing().when(mockProjectRepository).deleteById(expectedProject.getId());
		boolean actualProject = projectService.delete(expectedProject.getId());
		assertEquals(actualProject, true);
	}


}
