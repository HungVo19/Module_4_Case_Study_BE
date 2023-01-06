package com.example.back_end.controller;

import com.example.back_end.model.Comment;
import com.example.back_end.model.Label;
import com.example.back_end.service.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{blogId}")
    public ResponseEntity<Page<Comment>> findAllCommentByBlogId(@PageableDefault(size = 5)Pageable pageable,
                                                              @PathVariable Long blogId) {
        return new ResponseEntity<>(commentService.findAllByBlogId(pageable, blogId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment (@RequestBody Comment comment, @PathVariable Long id) {
        if (commentService.findById(id).isPresent()) {
            return new ResponseEntity<>(commentService.save(comment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment (@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()) {
            commentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
