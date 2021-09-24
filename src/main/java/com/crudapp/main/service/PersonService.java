package com.crudapp.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crudapp.main.model.Person;

@Service
public interface PersonService {

    List<Person> listAll();

    Person savePerson(Person person);

    Person getPersonById(Integer id);

    boolean delete(Integer id);

    Person getByName(String personname);

    Person updatePerson(Person person);
}
