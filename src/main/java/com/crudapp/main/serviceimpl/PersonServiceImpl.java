package com.crudapp.main.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crudapp.main.ApiResponse.UtilMethods;
import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Person;
import com.crudapp.main.repository.PersonRepository;
import com.crudapp.main.service.PersonService;
import com.google.gson.Gson;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repo;

	private UtilMethods util = new UtilMethods();

	@Override
	public List<Person> listAll() {

		if (repo.findAll() != null) {
			return repo.findAll();
		} else {

			throw new CustomException("No records found");
		}
	}

	@Override
	public Person savePerson(Person person) {

		Optional<Person> exisitngPerson = repo.findById(person.getId());

		if (!exisitngPerson.isPresent()) {
			Person newPerson = new Person();

			newPerson.setid(person.getId());
			newPerson.setPersonName(person.getPersonName());
			newPerson.setPassword(person.getPassword());
			newPerson.setEmail(person.getEmail());
			newPerson.setDepartment(person.getDepartment());
			newPerson.setProject(person.getProject());
			newPerson = repo.save(newPerson);

			return newPerson;

		} else {
			throw new CustomException("Person already exists");
		}
	}

	@Override
	public Person getPersonById(Integer id) throws CustomException {

		Optional<Person> person = repo.findById(id);

		if (person.isPresent()) {

			return person.get();

		} else {

			throw new CustomException("No person found with id " + id);
		}
	}

	public boolean delete(Integer id) throws CustomException {
		Optional<Person> person = repo.findById(id);
		if (person.isPresent()) {
			repo.deleteById(id);
			return true;
		} else {
			throw new CustomException("No person with id " + id);
		}
	}

	@Override
	public Person getByName(String personname) throws CustomException {
		Person person = repo.getByName(personname);
		if (person != null) {

			return repo.getByName(personname);
		} else {
			throw new CustomException("No person with name " + personname);

		}
	}

	@Override
	public Person updatePerson(Person person) {
		Optional<Person> existingperson = repo.findById(person.getId());
		if (existingperson.isPresent()) {
			Person updatedperson = existingperson.get();
			updatedperson.setPersonName(person.getPersonName());
			updatedperson.setPassword(person.getPassword());
			updatedperson.setEmail(person.getEmail());
			updatedperson.setDepartment(person.getDepartment());
			updatedperson.setProject(person.getProject());

			updatedperson = repo.save(updatedperson);
			return updatedperson;
		}

		else {
			throw new CustomException("Person does not exist");
		}
	}
}
