package com.example.back_end.service.impl;

import com.example.back_end.model.Blog;
import com.example.back_end.model.User;
import com.example.back_end.repository.IBlogRepository;
import com.example.back_end.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (blogRepository.findById(id).isPresent()) {
            return blogRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> findAllByUser(User user) {
        return blogRepository.findAllByUser(user);
    }
}
