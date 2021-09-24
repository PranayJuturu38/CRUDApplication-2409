package com.crudapp.main.controllerTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crudapp.main.controller.DepartmentController;
import com.crudapp.main.model.Department;
import com.crudapp.main.repository.Departmentrepository;
import com.crudapp.main.service.DepartmentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@WebMvcTest(controllers = DepartmentController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class DepartmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private Departmentrepository deptRepo;

    List<Department> departmentList = new ArrayList<Department>();

    @BeforeEach
    void setUp(){
        this.departmentList = new ArrayList<Department>();
        this.departmentList.add(new Department(1,"HR","backgate"));
        this.departmentList.add(new Department(2,"R&D","main"));
        this.departmentList.add(new Department(3,"Admin","main"));
        deptRepo.saveAll(departmentList);
    }

    @Test
    void getAllDepts() throws Exception {
        
        given(deptRepo.findAll()).willReturn(departmentList);


       MvcResult result =  this.mockMvc.perform(get("/departments"))
                
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(departmentList.size())))
                .andDo(document("{methodName}",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint())))
               .andReturn();
        assertEquals(result.getResponse().getContentAsString(), objectMapper);
            }

    @Test
    void getDeptById() throws Exception {
        Department department = new Department(1,"HR","backgate");
        given(deptRepo.findById(department.getDeptId())).willReturn(Optional.of(department));

        MvcResult result =this.mockMvc.perform(get("/departments/{id}",department.getDeptId()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.name", is(department.getName())))
                    .andExpect(jsonPath("$.location", is(department.getLocation())))
                    .andDo(document("{methodName}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())))
                .andReturn();
     
    }
    @Test
    void getByName() throws Exception{
        Department department = new Department(1,"HR","backgate");
        given(deptRepo.getByName(department.getName())).willReturn(department);
   
        MvcResult result = mockMvc.perform(get("/departments/name/{name}",department.getName()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(department.getName())))
        .andExpect(jsonPath("$.location", is(department.getLocation())))
        .andDo(document("{methodName}",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint())))
        .andReturn();


    }


}
