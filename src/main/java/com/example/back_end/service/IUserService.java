package com.example.back_end.service;

import com.example.back_end.model.User;

import java.util.List;

public interface IUserService extends ICOREService<User, Long>{
	List<User> findUserByUsernameContaining(String username);

	User findUserByUsername(String username);


}
