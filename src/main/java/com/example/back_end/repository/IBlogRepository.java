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
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrueOrderByCreatedDateDesc(Pageable pageable);
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrueOrderByIdDesc(Pageable pageable);
    Page<Blog>findAllByPrivacyIsTrueAndStatusIsTrueAndUserId(Long userId, Pageable pageable);
    @Query(value = "select * from Blog where (title like ?1 or content like ?2)", nativeQuery = true)
    Page<Blog>findAllByTitleContainingOrTitleContaining(String string1, String string2, Pageable pageable);
    @Query(nativeQuery = true,value = "select * from blog right join label_blogs" +
            " on blog.id = label_blogs.blogs_id left join label on label_blogs.label_id = label.id " +
            "where label.id = ?1  order by blog.created_date desc")
    Page<Blog>findBlogsByLabelId(Long id, Pageable pageable);

}
