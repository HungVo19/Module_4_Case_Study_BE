package com.example.back_end.controller;

import com.example.back_end.model.Role;
import com.example.back_end.model.User;
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
@RequestMapping("/register")
@PropertySource("classpath:application.properties")
public class RegisterController {
	@Autowired
	private UserService userService;

	@Value("${upload.path}")
	private String link;

	@Value("${display.path}")
	private String displayLink;

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
			user.setStatus(true);
			userService.save(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("User name is existed",HttpStatus.CONFLICT);
	}
}
