package com.example.back_end.controller;

import com.example.back_end.model.Blog;
import com.example.back_end.model.Comment;
import com.example.back_end.service.IBlogService;
import com.example.back_end.service.ICommentService;
import com.example.back_end.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping
public class BlogController {
    @Autowired
    IBlogService blogService;

    @Value("${upload.path}")
    private String link;

    @Value("${display.path}")
    private String displayLink;

    @GetMapping("/users/{userId}/blogs")
    public ResponseEntity<Page<Blog>> findAllByUserIdAndStatusIsTrue(@PathVariable Long userId,
                                                                     @PageableDefault(size = 2)
                                                                     Pageable pageable) {
        return new ResponseEntity<>(blogService.findAllByUserIdAndStatusIsTrue(userId, Pageable.unpaged()), HttpStatus.OK);
    }

    @GetMapping("/blogs/{blogId}")
    public ResponseEntity<Blog> findBlogByUserId(@PathVariable(value = "blogId") Long blogId) {
        return new ResponseEntity<>(blogService.findById(blogId).get(), HttpStatus.OK);
    }

    @GetMapping("/blogs")
    public ResponseEntity<Page<Blog>> findAll(@PageableDefault(size = 2) Pageable pageable) {
        return new ResponseEntity<>(blogService.findAll(Pageable.unpaged()), HttpStatus.OK);
    }

    @GetMapping("/blogs/public")
    public ResponseEntity<Page<Blog>> findPublicBlogs(@PageableDefault(size = 2) Pageable pageable) {
       return new ResponseEntity<>(blogService.findAllPublicBlogs(pageable),HttpStatus.OK);
    }

    @GetMapping("/blogs/latestBlog")
    public ResponseEntity<Page<Blog>> findLatestBlogs(@PageableDefault(size = 4) Pageable pageable) {
        return new ResponseEntity<>(blogService.findAllPublicBlogs(pageable),HttpStatus.OK);
    }

    @GetMapping("/users/{id}/blogs/public")
    public ResponseEntity<Page<Blog>> findAllPublicBlogsByUserId(@PathVariable Long id,Pageable pageable) {
        return new ResponseEntity<>(blogService.findAllPublicBlogsByUserId(id, Pageable.unpaged()), HttpStatus.OK);
    }

    @PostMapping("/blogs")
    public ResponseEntity<Blog> create(@RequestPart(value = "file", required = false)
                                       MultipartFile file,
                                       @RequestPart("blog") Blog blog) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File((link + fileName)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            blog.setImage(displayLink + fileName);
        } else {
            blog.setImage(displayLink + "default.jpg");
        }

        if (blog.getDescription() == null) {
            blog.setDescription(blog.getContent().substring(0, 50) + "...");
        }

        blog.setCreatedDate(LocalDate.now());

        blog.setStatus(true);


        return new ResponseEntity<>(blogService.save(blog), HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> update(@PathVariable Long id, @RequestPart(value = "file", required = false)
    MultipartFile file, @RequestPart("blog") Blog blog) {
        Blog blogUpdate = blogService.findById(id).get();

        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(link + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            blog.setImage(displayLink + fileName);
        } else {
            blog.setImage(displayLink + "default.jpg");
        }

        blog.setId(blogUpdate.getId());

        blog.setCreatedDate(blogUpdate.getCreatedDate());

        if (blog.getDescription() == null) {
            blog.setDescription(blog.getContent().substring(0, 50) + "...");
        }

        return new ResponseEntity<>(blogService.save(blog), HttpStatus.OK);

    }

    @GetMapping("/blogs/search")
    public ResponseEntity<Page<Blog>> search(@RequestParam(value = "q") String q, Pageable pageable) {
        Page<Blog> pages = blogService.findAllByTitleContainingOrTitleContaining("%" +q + "%", "%" +q + "%", Pageable.unpaged());
        System.out.println("test");
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        blogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
