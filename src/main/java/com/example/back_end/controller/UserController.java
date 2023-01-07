package com.example.back_end.controller;

import com.example.back_end.model.ChangePassword;
import com.example.back_end.model.ForgotPassword;
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
@CrossOrigin("*")
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

	@PutMapping("/change-password/{id}")
	public ResponseEntity<User> changePassword(@PathVariable Long id,
											   @RequestBody ChangePassword changePassword) {
		User user = userService.findById(id).get();
		if (!changePassword.getCurrentPass().equals(user.getPassword())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else if (changePassword.getNewPass().equals(user.getPassword())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else if (!changePassword.getConfirmPass().equals(changePassword.getNewPass())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		user.setPassword(changePassword.getNewPass());
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<User> forgotPassword(@RequestBody ForgotPassword forgotPassword) {
		User confirmUsername = userService.findUserByUsername(forgotPassword.getUsername());
		User confirmEmail = userService.findUserByEmail(forgotPassword.getEmail());
		if (confirmUsername.equals(confirmEmail)) {
			return new ResponseEntity<>(confirmEmail, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/change-password/{id}")
	public ResponseEntity<User> changePassAfterForgot(@PathVariable Long id,
													  @RequestBody ChangePassword changePassword) {
		Optional<User> user = userService.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (changePassword.getNewPass().equals(changePassword.getConfirmPass())) {
			user.get().setPassword(changePassword.getNewPass());
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
