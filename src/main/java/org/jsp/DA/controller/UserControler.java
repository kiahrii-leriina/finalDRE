package org.jsp.DA.controller;

import java.util.List;


import org.jsp.DA.entity.User;
import org.jsp.DA.service.UserService;
import org.jsp.DA.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserControler {

	@Autowired
	private UserService service;

	@PostMapping()
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody User user) {
		return service.saveUser(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<User>> deleteUser(@PathVariable int id) {
		return service.deleteUser(id);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<User>>> findAll() {
		return service.findAll();
	}

	@GetMapping("/match/{id}/{top}")
	public ResponseEntity<ResponseStructure<List<User>>> findMatch(@PathVariable int id, @PathVariable int top) {
		return service.findMatch(id, top);
	}

	@GetMapping("/namelike/{name}")
	public ResponseEntity<ResponseStructure<User>> findByNameLike(@PathVariable(name = "name") String name) {
		return service.findByNameLike(name);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody User user) {
		return service.updateUser(user);
	}

	@PatchMapping("/updateEmail/{name}/{phone}/{email}")
	public ResponseEntity<ResponseStructure<User>> updateEmail(@PathVariable String name,
			@PathVariable long phone, @PathVariable String email) {

		return service.updateEmail(name,phone,email);
	}
	

}
