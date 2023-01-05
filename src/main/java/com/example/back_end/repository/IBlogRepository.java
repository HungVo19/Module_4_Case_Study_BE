package com.example.back_end.repository;

import com.example.back_end.model.Blog;
import com.example.back_end.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByStatusIsTrue();
    List<Blog> findAllByStatusIsTrueAndPrivacyIsTrue();
}
