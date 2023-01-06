package com.example.back_end.controller;

import com.example.back_end.model.User;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> login(@RequestBody User user) {
		User userExist = userService.findUserByUsername(user.getUsername());
		if (userExist == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else if (user.getPassword().equals(userExist.getPassword()) && userExist.isStatus()) {
			return new ResponseEntity<>(userExist, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
