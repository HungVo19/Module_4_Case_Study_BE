package com.example.back_end.service.impl;

import com.example.back_end.model.User;
import com.example.back_end.repository.IUserRepository;
import com.example.back_end.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findUserByUsernameContaining(String username) {
		return userRepository.findUserByUsernameContaining(username);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}
}
