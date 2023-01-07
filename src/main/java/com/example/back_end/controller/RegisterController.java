package com.example.back_end.controller;

import com.example.back_end.model.RegisterForm;
import com.example.back_end.model.User;
import com.example.back_end.service.IRoleService;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/register")
@PropertySource("classpath:application.properties")
public class RegisterController {
	@Autowired
	private UserService userService;
	@Autowired
	private IRoleService roleService;

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody RegisterForm user) {
		if (user.getUsername() == null || user.getPass() == null || user.getRePass() == null) {
			return new ResponseEntity<>("All fields can not be blank", HttpStatus.CONFLICT);
		}
		if (userService.findUserByUsername(user.getUsername()) == null &&
			userService.findUserByEmail(user.getEmail()) == null) {
			if (user.getPass().equals(user.getRePass())) {
				User userCreate = new User();
				userCreate.setUsername(user.getUsername());
				userCreate.setPassword(user.getPass());
				userCreate.setStatus(true);
				userCreate.setRole(roleService.findById(1L).get());
				userService.save(userCreate);
				return new ResponseEntity<>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Wrong re-pass", HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<>("Username or password is existed", HttpStatus.CONFLICT);
		}
	}
}
