package com.crudapp.main.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.crudapp.main.exception.CustomException;
import com.crudapp.main.model.Project;
import com.crudapp.main.repository.Projectrepository;
import com.crudapp.main.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private Projectrepository projectrepo;

    @Override
    public List<Project> getAllProj() {
        List<Project> proj = projectrepo.findAll();
        if (proj.size() > 0) {
            return proj;
        } else {
            return new ArrayList<Project>();
        }
    }

    @Override
    public Project saveProject(Project project) {
        Optional<Project> proj = projectrepo.findById(project.getId());
        if (!proj.isPresent()) {//proj ==null
            Project newproj = new Project();
            newproj.setId(project.getId());
            newproj.setName(project.getName());
            newproj.setDescription(project.getDescription());
            newproj.setPerson(project.getPerson());

            newproj = projectrepo.save(newproj);
            return newproj;

        } else {
            throw new CustomException("Project already exists");
        }
    }

    @Override
    public Project getProjectById(Integer id) throws CustomException {
        Optional<Project> proj = projectrepo.findById(id);
        if (proj.isPresent()) {

            return proj.get();

        } else {
            throw new CustomException("No projects found with id:" + id);
        }
    }

    @Override
    public boolean delete(Integer project_id) throws CustomException {
        Optional<Project> proj = projectrepo.findById(project_id);
        if (proj.isPresent()) {
            projectrepo.deleteById(project_id);
            return true;
        } else {
            throw new CustomException("No projects found with id:" + project_id);
        }
    }

    @Override
    public Project updateProject(Project project) throws CustomException {
        Optional<Project> existingproj = projectrepo.findById(project.getId());
        if (existingproj.isPresent()) {
            Project updatedproj = existingproj.get();
            updatedproj.setId(project.getId());
            updatedproj.setName(project.getName());
            updatedproj.setDescription(project.getDescription());
            updatedproj.setPerson(project.getPerson());

            updatedproj = projectrepo.save(updatedproj);
            return updatedproj;
        } else {
            throw new CustomException("Project not found");
        }

    }


}