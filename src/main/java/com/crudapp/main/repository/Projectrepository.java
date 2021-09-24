package com.crudapp.main.repository;

import com.crudapp.main.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Projectrepository extends JpaRepository<Project, Integer> {
 
    @Query("SELECT p FROM Project p WHERE p.name = :name")
	public Project getByName(@Param("name") String name);

}
