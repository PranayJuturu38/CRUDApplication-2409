package com.crudapp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.crudapp.main.model.Department;

public interface Departmentrepository extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d WHERE d.name = :name")
    public Department getByName(@Param("name") String name);

    @Query("Select d FROM Department d WHERE d.location = :location")
    public List<Department> getByLocation(@Param("location") String location);

}
