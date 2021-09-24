package com.crudapp.main.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.Json;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Department;
import com.crudapp.main.model.Person;
import com.crudapp.main.model.Project;
import com.crudapp.main.repository.PersonRepository;
import com.crudapp.main.service.PersonService;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class PersonTests {

    @Autowired
    private PersonService personService;
    @Mock
    PersonRepository personMockRepo;

    @Test // POST Mapping "/persons" Happy TestCase //mockito
    void personSaved() {
        Person expectedPerson = new Person();

        expectedPerson.setid(3);
        expectedPerson.setPersonName("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.save(expectedPerson)).thenReturn(expectedPerson);

        Person actualPerson = personService.savePerson(expectedPerson);

        assertEquals(expectedPerson.getId(), actualPerson.getId());

    }

    @Test // Person SAve Unhappy TestCases //mockito //
    void personNotSaved() throws Exception {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setPersonName("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.save(expectedPerson)).thenReturn(expectedPerson);
        Exception exception = assertThrows(CustomException.class, () -> {
            personService.savePerson(expectedPerson);
        });
        String message = "Person already exists";
        String expectedMessage = exception.getMessage();
        assertTrue(message.contains(expectedMessage));
    }

    @Test // Person get by id "/Persons/{id}" //Parse through ResponseEntity
    void personID() throws IOException {

        Person expectedPerson = new Person();
        expectedPerson.setid(6);
        expectedPerson.setPersonName("name");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.findById(expectedPerson.getId())).thenReturn(Optional.of(expectedPerson));

        personService.savePerson(expectedPerson);

        Person actualPerson = personService.getPersonById(expectedPerson.getId());
        assertEquals(expectedPerson.getId(), actualPerson.getId());

    }

    @Test // Person get by id "/Persons/{id}" unhappy
    void personNoID() {

         Person expectedPerson = new Person();
        expectedPerson.setid(10);
        expectedPerson.setPersonName("noid");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");
        when(personMockRepo.findById(expectedPerson.getId())).thenReturn(Optional.of(expectedPerson));

        Exception exception = assertThrows(CustomException.class, () -> {
            personService.getPersonById(expectedPerson.getId());
        });
        String message = "No person found with id "+expectedPerson.getId();
        String expectedMessage = exception.getMessage();
        assertTrue(message.contains(expectedMessage));
    }

    @Test //Delete Person happy //mockito //
    void deletePerson(){
        Person expectedPerson = new Person();
        expectedPerson.setid(2);
        expectedPerson.setPersonName("tobedeleted");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        personService.savePerson(expectedPerson);

        doNothing().when(personMockRepo).deleteById(expectedPerson.getId());
        boolean actualPerson = personService.delete(expectedPerson.getId());
        assertEquals(actualPerson,true);
    }
    @Test
    void deleteFailure(){
        Person expectedPerson = new Person();
        expectedPerson.setid(2);
        expectedPerson.setPersonName("deletefailure");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");


        doNothing().when(personMockRepo).deleteById(expectedPerson.getId());
        Exception exception = assertThrows(CustomException.class, () -> {
            personService.delete(expectedPerson.getId());
        });
        String message = "No person with id "+expectedPerson.getId();
        String expectedMessage = exception.getMessage();
        assertTrue(message.contains(expectedMessage));
    }

    @Test // GetAll Persons //mockito //
    void getAllPersons() {

       Person p1 = new Person();
       p1.setid(4);
       p1.setPersonName("reddy");
       p1.setPassword("password");
       p1.setEmail("email");
       
       Person p2 = new Person();
       p2.setid(5);
       p2.setPersonName("juturu");
       p2.setPassword("password");
       p2.setEmail("email");
       
       List<Person> expectedPersonList = new ArrayList<Person>();

        expectedPersonList.add(p1);
        expectedPersonList.add(p2);
        
        when(personMockRepo.findAll()).thenReturn(expectedPersonList);

        personService.savePerson(p1);
        personService.savePerson(p2);
        List<Person> actualPersonList = personService.listAll();
        assertEquals(4, actualPersonList.size());
    }

    @Test // Person get by name "/persons/names/{name}" //mockito
    void personName() {
        Person expectedPerson = new Person();
        expectedPerson.setid(7);
        expectedPerson.setPersonName("person");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.getByName(expectedPerson.getPersonName())).thenReturn(expectedPerson);

        personService.savePerson(expectedPerson);
        Person actualPerson = personService.getByName(expectedPerson.getPersonName());
        assertEquals(expectedPerson.getPersonName(), actualPerson.getPersonName());
    }

    @Test // Unhappy get person by name "/persons/names/{name}"
    void personNameFailure() throws CustomException {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setPersonName("pranay");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        when(personMockRepo.getByName(expectedPerson.getPersonName())).thenThrow(new CustomException("Not Found"));


        Exception exception = assertThrows(CustomException.class, () -> {
            personService.getByName(expectedPerson.getPersonName());
        });
        String message = "No person with name "+expectedPerson.getPersonName();
        String expectedMessage = exception.getMessage();
        assertTrue(message.contains(expectedMessage));

    }

    @Test // person updated //Mockito
    void updatePerson() {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setPersonName("tobeupdated");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");

        personService.savePerson(expectedPerson);

        when(personMockRepo.findById(expectedPerson.getId())).thenReturn(Optional.of(expectedPerson));
        Person actualPerson = (Person) personService.getPersonById(expectedPerson.getId());
        assertEquals(expectedPerson.getId(), actualPerson.getId());
        Person dummyPerson = new Person(1,"updated","password","email");
        Person updatedPerson = personService.updatePerson(dummyPerson);
        assertNotEquals("tobeupdated",updatedPerson.getPersonName());
    }
    @Test // person updated //Mockito
    void updatePersonFailure() {
        Person expectedPerson = new Person();
        expectedPerson.setid(1);
        expectedPerson.setPersonName("tobeupdatedfailed");
        expectedPerson.setPassword("password");
        expectedPerson.setEmail("email");


        when(personMockRepo.findById(expectedPerson.getId())).thenReturn(Optional.of(expectedPerson));
        Person dummyPerson = new Person(10,"updatedfailure","password","email");
        Exception exception = assertThrows(CustomException.class,()->{
            personService.updatePerson(dummyPerson);
        });
        String message ="Person does not exist";
        String expectedMessage = exception.getMessage();
        assertTrue(message.contains(expectedMessage));  }
}
