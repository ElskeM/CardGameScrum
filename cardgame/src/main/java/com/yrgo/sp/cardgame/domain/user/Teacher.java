package com.yrgo.sp.cardgame.domain.user;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Teacher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private Collection<User> students;

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private Collection<User> getStudents() {
		return students;
	}

	private void setStudents(Collection<User> students) {
		this.students = students;
	}
	
}
