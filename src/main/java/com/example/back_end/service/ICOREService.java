package com.example.back_end.service;

import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ICOREService <E,K>{
    Page<E> findAll(Pageable pageable);
    Optional<E> findById(K k);
    E save(E e);
    void deleteById(K k);
}
