package com.example.back_end.repository;

import com.example.back_end.model.Blog;
import com.example.back_end.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBlogRepository extends JpaRepository<Blog,Long> {
    Page<Blog>findAllByUserIdAndStatusIsTrue(Long userId,Pageable pageable);
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrueOrderByCreatedDateDesc(Pageable pageable);
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrueAndUserId(Long userId, Pageable pageable);
    @Query(value = "select * from Blog where title like ?1 or content like ?2", nativeQuery = true)
    Page<Blog>findAllByTitleContainingOrTitleContaining(String string1, String string2, Pageable pageable);

    @Query(value = "select count(c) from Comment c join Blog b on c.blog.id = b.id where c.blog.id = ?1")
    Long countAllCommentByBlogId(Long id);
}
