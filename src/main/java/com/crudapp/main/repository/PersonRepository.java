package com.crudapp.main.repository;

import com.crudapp.main.model.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Query("SELECT p FROM Person p WHERE p.personname = :personname")
	public Person getByName(@Param("personname") String personname);

}
