package com.spring.boot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>{
	
	public List<Student> findByNameContaining(String name);
	
	public List<Student> findByAgeGreaterThan(Integer age);

}
