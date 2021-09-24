package com.crudapp.main.controller;

import java.util.List;

import com.crudapp.main.message.Message;

import com.crudapp.main.model.Project;
import com.crudapp.main.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projservice;

    @GetMapping
    public List<Project> allProjects() {

        return projservice.getAllProj();

    }

    @PostMapping
    public ResponseEntity<Message> createproject(@RequestBody Project project) throws Exception {
        String message = "";
        try {
            projservice.saveProject(project);
            message = "Project saved successfully with id: " + project.getId();
            return ResponseEntity.status(HttpStatus.OK).body(new Message(message));
        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(message));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable Integer id) throws Exception {
        try {
            Project proj = projservice.getProjectById(id);
            return new ResponseEntity<Project>(proj, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }


}
