package com.example.back_end.controller;

import com.example.back_end.model.User;
import com.example.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

	@GetMapping
	public ResponseEntity<Page<User>> listUsers(@PageableDefault(size = 2)Pageable pageable) {
		if (userService.findAll(pageable).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestPart(value = "file", required = false) MultipartFile file,
										@RequestBody User user) {
		if (userService.findUserByUsername(user.getUsername()) == null) {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				try {
					FileCopyUtils.copy(file.getBytes(), new File(link + fileName));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				user.setAvatar(displayLink + fileName);
			} else {
				user.setAvatar(displayLink + "avatar.jpg");
			}
			userService.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("User name is existed",HttpStatus.CONFLICT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		if (!userService.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		if (!userService.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/status/{id}")
	public ResponseEntity<User> blockUser(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		user.get().setStatus(false);
		userService.save(user.get());
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}


}
