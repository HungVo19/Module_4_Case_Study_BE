package com.example.back_end.repository;

import com.example.back_end.model.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILabelRepository extends JpaRepository<Label, Long> {
    Label findByName(String name);
}
