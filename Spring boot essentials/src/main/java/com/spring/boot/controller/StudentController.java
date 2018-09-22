package com.spring.boot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.error.ResourceNotFoundException;
import com.spring.boot.model.Student;
import com.spring.boot.repository.StudentRepository;

@RestController
@RequestMapping("students")
public class StudentController {
	
	@Autowired
	private StudentRepository repo;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save (@RequestBody Student student){
		return new ResponseEntity<>(repo.save(student), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id){
		verifyIfStudentExists(id);
		return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/name")
	public ResponseEntity<?> findByNameContaining(@RequestParam String name){
		return new ResponseEntity<>(repo.findByNameContaining(name), HttpStatus.OK);
	}
	
	@GetMapping("/age")
	public ResponseEntity<?> findByAgeGreaterThan(@RequestParam Integer age){
		return new ResponseEntity<>(repo.findByAgeGreaterThan(age), HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam Integer id){
		verifyIfStudentExists(id);
		repo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Student student){
		verifyIfStudentExists(student.getId());
		return new ResponseEntity<>(repo.save(student), HttpStatus.OK);
	}
	
	private void verifyIfStudentExists(Integer id) {
		Optional<Student> student = repo.findById(id);
		if(!student.isPresent()) {
			throw new ResourceNotFoundException("not found student with id " + id);
		}
	}
}
