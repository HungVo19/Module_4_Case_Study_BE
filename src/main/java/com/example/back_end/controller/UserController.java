package com.example.back_end.controller;

import com.example.back_end.model.User;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@PropertySource("classpath:application.properties")
public class UserController {
	@Autowired
	private UserService userService;

	@Value("${upload.path}")
	private String link;

	@Value("${display.path}")
	private String displayLink;

	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		if (!userService.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
