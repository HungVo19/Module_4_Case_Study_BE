package com.example.back_end.service;

import com.example.back_end.model.Blog;
import com.example.back_end.model.Comment;
import com.example.back_end.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBlogService extends ICOREService<Blog, Long> {
    Page<Blog> findAllByUserIdAndStatusIsTrue(Long userId, Pageable pageable);

    Page<Blog> findAllPublicBlogs(Pageable pageable);

    Page<Blog> findAllPublicBlogsByUserId(Long userId, Pageable pageable);
    Page<Blog>findAllByTitleContainingOrTitleContaining(String string1, String string2, Pageable pageable);

    ResponseEntity<Blog> setStatus(Long id);

    Long countComment(Long id);
}
