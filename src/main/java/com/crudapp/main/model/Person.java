package com.crudapp.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "person")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "personname")
	private String personname;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department")
	private Department department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project")
	private Project project;


	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Person() {

	}

	@Override
	public String toString() {
		return "Person [email=" + email + ", id=" + id + ", password=" + password + ", personname=" + personname + "]";
	}

	public Person(Integer id, String personname, String password, String email){
		this.id = id;
		this.personname = personname;
		this.password = password;
		this.email = email;
	}

	
	public Integer getId() {
		return id;
	}

	public void setid(Integer id) {
		this.id = id;
	}

	public String getPersonName() {
		return personname;
	}

	public void setPersonName(String personname) {
		this.personname = personname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	
}
