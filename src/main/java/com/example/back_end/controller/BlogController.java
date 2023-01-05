package com.example.back_end.controller;

import com.example.back_end.model.Blog;
import com.example.back_end.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blogs")
public class BlogController {
    @Autowired
    IBlogService blogService;

    @GetMapping
    public ResponseEntity<Page<Blog>> list (@PageableDefault(size = 2) Pageable pageable) {
        return new ResponseEntity<>(blogService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findById(@PathVariable Long id) {
        if (blogService.findById(id).isPresent()) {
            return new ResponseEntity<>(blogService.findById(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<Blog> create(@RequestPart Blog blog) {
        return new ResponseEntity<>(blogService.save(blog),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> update(@PathVariable Long id, @RequestPart Blog blog) {
        Blog blogUpdate = blogService.findById(id).get();
        blog.setId(blogUpdate.getId());
        return new ResponseEntity<>(blogService.save(blog),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        blogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
