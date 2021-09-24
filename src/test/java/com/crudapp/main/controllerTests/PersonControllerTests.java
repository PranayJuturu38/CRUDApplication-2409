package com.crudapp.main.controllerTests;

import java.util.ArrayList;
import java.util.List;

import com.crudapp.main.controller.PersonController;
import com.crudapp.main.model.Person;
import com.crudapp.main.service.PersonService;

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

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    List<Person> personList = new ArrayList<Person>();

    @BeforeEach
    void setUp(){
   this.personList = new ArrayList<Person>();
   this.personList.add(new Person(1,"Pranay","password","email"));
   this.personList.add(new Person(2,"Reddy","password","email"));
   this.personList.add(new Person(3,"Juturu","password","email"));
  }

    @Test
    void getAllPersons() throws Exception {
        given(personService.listAll()).willReturn(new ArrayList<Person>());
        this.mockMvc.perform(get("/persons"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()",is(personList.size())));

    }

    @Test
    void getPersonById() throws Exception {
        Person person = new Person(1,"Pranay","password","email");
        given(personService.getPersonById(person.getId())).willReturn(person);

        this.mockMvc.perform(get("/persons/{id}",person.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name",is(person.getPersonName())))
                    .andExpect(jsonPath("$.password",is(person.getPassword())))
                    .andExpect(jsonPath("$.email",is(person.getEmail())));

    }

    @Test
    void getByName() throws Exception {
        Person person = new Person(1,"Pranay","password","email");
        given(personService.getByName(person.getPersonName())).willReturn(person);

        this.mockMvc.perform(get("/persons/name/{name}",person.getPersonName()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name",is(person.getPersonName())))
                    .andExpect(jsonPath("$.password",is(person.getPassword())))
                    .andExpect(jsonPath("$.email",is(person.getEmail())));

    }
}
