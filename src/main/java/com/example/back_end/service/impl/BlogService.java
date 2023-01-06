package com.example.back_end.service.impl;

import com.example.back_end.model.Blog;
import com.example.back_end.model.User;
import com.example.back_end.repository.IBlogRepository;
import com.example.back_end.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService {
    @Autowired
    IBlogRepository blogRepository;

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteById(Long id) {
        Blog blog = findById(id).get();
        blog.setStatus(false);
    }

    @Override
    public Page<Blog> findAllByUserIdAndStatusIsTrue(Long userId, Pageable pageable) {
        return blogRepository.findAllByUserIdAndStatusIsTrue(userId, pageable);
    }

    @Override
    public Page<Blog> findAllPublicBlogs(Pageable pageable) {
        return blogRepository.findAllByPrivacyIsTrueAndStatusIsTrue(pageable);
    }

    @Override
    public Page<Blog> findAllPublicBlogsByUserId(Long userId, Pageable pageable) {
        return blogRepository.findAllByPrivacyIsTrueAndStatusIsTrueAndUserId(userId,pageable);
    }

    @Override
    public Page<Blog> findAllByTitleContainingOrTitleContaining(String string1, String string2, Pageable pageable) {
        return blogRepository.findAllByTitleContainingOrTitleContaining(string1,string2,pageable);
    }
}