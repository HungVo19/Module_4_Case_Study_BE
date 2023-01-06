package com.example.back_end.repository;

import com.example.back_end.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog,Long> {
    Page<Blog>findAllByUserIdAndStatusIsTrue(Long userId,Pageable pageable);
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrue(Pageable pageable);
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrueAndUserId(Long userId, Pageable pageable);
    @Query(value = "select * from Blog where title like ?1 or content like ?2", nativeQuery = true)
    Page<Blog>findAllByTitleContainingOrTitleContaining(String string1, String string2, Pageable pageable);

}
