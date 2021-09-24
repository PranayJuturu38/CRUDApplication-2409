package com.crudapp.main.controllerTests;

import java.util.ArrayList;
import java.util.List;

import com.crudapp.main.controller.ProjectController;
import com.crudapp.main.model.Project;
import com.crudapp.main.service.ProjectService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers= ProjectController.class)
public class ProjectControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    List<Project> projectList = new ArrayList<Project>();

    @BeforeEach
    void setup() {
        this.projectList = new ArrayList<Project>();
        this.projectList.add(new Project(1,"MobileApplication","used by patients"));
        this.projectList.add(new Project(2,"WebApplication","used by patients"));
        this.projectList.add(new Project(3,"LocalApplication","used by patients"));
        
    }

    @Test
    void getAllProjects() throws Exception {
        given(projectService.getAllProj()).willReturn(projectList);

        this.mockMvc.perform(get("/projects"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()",is(projectList.size())));
    }

    @Test
    void getDeptById() throws Exception {
        Project project = new Project(1,"Mobile","used by patients");
        given(projectService.getProjectById(project.getId())).willReturn(project);
        
        this.mockMvc.perform(get("/projects/{id}",project.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(project.getName())))
                    .andExpect(jsonPath("$.description", is(project.getDescription())));
  
    }
}
