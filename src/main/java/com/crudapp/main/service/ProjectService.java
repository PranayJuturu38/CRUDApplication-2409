package com.crudapp.main.service;

import java.util.List;

import com.crudapp.main.model.Project;

import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    List<Project> getAllProj();

    Project saveProject(Project project);

    Project getProjectById(Integer id);
    
    boolean delete(Integer project_id);

    Project updateProject(Project project);


}