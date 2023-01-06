package com.example.back_end.controller;

import com.example.back_end.model.User;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Page<User>> listNormalUsers(@PageableDefault(size = 5) Pageable pageable) {
		if (userService.findNormalUsers(pageable).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userService.findNormalUsers(pageable), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		if (!userService.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/{id}")
	public ResponseEntity<User> blockUser(@PathVariable Long id) {
		return userService.activeUser(false, id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> unBlockUser(@PathVariable Long id) {
		return userService.activeUser(true, id);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<User>> searchUserByUsername(@RequestParam("q") String username, Pageable pageable) {
		Page<User> users = userService.findUserByUsernameContaining(pageable, username);
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
