package com.example.back_end.repository;

import com.example.back_end.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
	List<User> findUserByUsernameContaining(String username);
	User findUserByUsername(String username);
	Page<User> findUserByRole_Id(Pageable pageable, Long id);
}
