package com.example.back_end.controller;

import com.example.back_end.model.User;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

//	@PostMapping
//	public ResponseEntity<User> login(@RequestBody User user) {
//		User userExist = userService.findUserByUsername(user.getUsername());
//		if (userExist == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		} else if (user.getPassword().equals(userExist.getPassword()) && userExist.isStatus()) {
//			return new ResponseEntity<>(userExist, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	@PostMapping
	public ResponseEntity<User> login(@RequestParam("loginInput") String loginInput,
									  @RequestParam("password") String password) {
		User usernameLogin = userService.findUserByUsername(loginInput);
		User emailLogin = userService.findUserByEmail(loginInput);
		if (usernameLogin != null && usernameLogin.getPassword().equals(password)) {
			return new ResponseEntity<>(usernameLogin, HttpStatus.OK);
		}
		if (emailLogin != null && emailLogin.getPassword().equals(password)) {
			return new ResponseEntity<>(emailLogin, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
