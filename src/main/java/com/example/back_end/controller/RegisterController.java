package com.example.back_end.controller;

import com.example.back_end.model.RegisterForm;
import com.example.back_end.model.User;
import com.example.back_end.service.IRoleService;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
			.limit(targetStringLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
		if (user.getUsername().equals("") || user.getPass().equals("") || user.getRePass().equals("")) {
			return new ResponseEntity<>("All fields can not be blank", HttpStatus.CONFLICT);
		}
		if (userService.findUserByUsername(user.getUsername()) == null &&
			userService.findUserByEmail(user.getEmail()) == null) {
			if (user.getPass().length() < 6 || user.getPass().length() > 8) {
				return new ResponseEntity<>("Password must be 6 to 8 characters", HttpStatus.NOT_FOUND);
			}
			if (user.getPass().equals(user.getRePass())) {
				User userCreate = new User();
				userCreate.setName(generatedString);
				userCreate.setUsername(user.getUsername());
				userCreate.setPassword(user.getPass());
				userCreate.setEmail(user.getEmail());
				userCreate.setStatus(true);
				userCreate.setRole(roleService.findById(1L).get());
				userCreate.setAvatar("images/blog/d5e500cc4db9a1b28372cd9d9166ea89.jpg");
				userService.save(userCreate);
				return new ResponseEntity<>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Wrong re-pass", HttpStatus.CONFLICT);
			}
		} else if (userService.findUserByUsername(user.getUsername()) != null){
			return new ResponseEntity<>("Username is existed", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>("Email is existed", HttpStatus.CONFLICT);
		}
	}
}
