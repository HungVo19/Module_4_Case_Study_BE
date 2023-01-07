package com.example.back_end.controller;

import com.example.back_end.model.LoginForm;
import com.example.back_end.model.User;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> login(@RequestBody LoginForm loginForm) {
		User usernameLogin = userService.findUserByUsername(loginForm.getLoginInput());
		User emailLogin = userService.findUserByEmail(loginForm.getLoginInput());
		if (usernameLogin != null && usernameLogin.getPassword().equals(loginForm.getPassword())) {
			return new ResponseEntity<>(usernameLogin, HttpStatus.OK);
		}
		if (emailLogin != null && emailLogin.getPassword().equals(loginForm.getPassword())) {
			return new ResponseEntity<>(emailLogin, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
