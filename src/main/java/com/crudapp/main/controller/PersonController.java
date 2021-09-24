package com.crudapp.main.controller;

import java.util.List;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.message.Message;
import com.crudapp.main.model.Person;
import com.crudapp.main.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	private PersonService service;

	@GetMapping("/persons")
	public List<Person> list() {

		return service.listAll();

	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Object> get(@PathVariable Integer id) throws CustomException {
       try{
		return new ResponseEntity<Object>(service.getPersonById(id),HttpStatus.OK);

	}catch (Exception e) {
		return new ResponseEntity<Object>(service.getPersonById(id),HttpStatus.NOT_FOUND);
	}
}

	@GetMapping("/persons/name/{personname}")
	public ResponseEntity<Person> getfromname(@PathVariable("personname") String personname) throws Exception {
		try {
			Person person = service.getByName(personname);
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/persons")
	public ResponseEntity<Message> add(@Validated @RequestBody Person person) throws Exception {
        String message = "";
		try {

			service.savePerson(person);
			message = "Person added successfully with id: " + person.getId();
			return ResponseEntity.status(HttpStatus.OK).body(new Message(message));

		} catch (Exception e) {
			message = "Person could not be added:"+e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));
		}
	}
/* 
	@PostMapping("/persons/{id}/departments/{dept_id}")
	Person assignDept(@PathVariable Integer id, @PathVariable Integer dept_id) throws Exception {
		Person person = service.get(id);
		Department dept = deptservice.getBydeptid(dept_id);
		person.setDepartment(dept);
		return person;
	} */

	@PutMapping("/persons/{id}")
	public ResponseEntity<Message> update(@Validated @RequestBody Person person, @PathVariable Integer id) throws Exception {
		String message = "";
		try {
			service.updatePerson(person);
			message="Updated person:"+id+"successfully";
			return ResponseEntity.status(HttpStatus.OK).body(new Message(message));
		} catch (Exception e) {
			
			message = "Update failed"+e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));
		}
	}

	@DeleteMapping("/persons/{id}")
	public ResponseEntity<Message> delete(@PathVariable Integer id) throws Exception {
		String message = "";
		try {
			service.delete(id);
	         message="Deleted Person:"+id;
			 return ResponseEntity.status(HttpStatus.OK).body(new Message(message));
		} catch (Exception e) {
			message = "Couldn't delete the person"+e.getMessage();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));
		}
	}

}
