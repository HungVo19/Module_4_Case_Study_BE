package com.example.back_end.service;

import com.example.back_end.model.Blog;
import com.example.back_end.model.User;

import java.util.List;

public interface IBlogService extends ICOREService<Blog,Long> {
    List<Blog> findAllByUser(User user);
    void softDeleteById(Long id);
}
