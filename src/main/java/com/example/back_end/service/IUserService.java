package com.example.back_end.service;

import com.example.back_end.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService extends ICOREService<User, Long>{
	Page<User> findUserByUsernameContaining(Pageable pageable, String username);

	User findUserByUsername(String username);

	Page<User> findNormalUsers(Pageable pageable);

	ResponseEntity<User> activeUser(Boolean b, Long id);

}
