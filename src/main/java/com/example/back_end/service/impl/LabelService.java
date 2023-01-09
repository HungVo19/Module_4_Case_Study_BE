package com.example.back_end.service.impl;

import com.example.back_end.model.Label;
import com.example.back_end.repository.ILabelRepository;
import com.example.back_end.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService {
    @Autowired
    private ILabelRepository iLabelRepository;

    @Override
    public Page<Label> findAll(Pageable pageable) {
        return iLabelRepository.findAll(pageable);
    }

    @Override
    public Optional<Label> findById(Long id) {
        return iLabelRepository.findById(id);
    }

    @Override
    public Label save(Label label) {
        return iLabelRepository.save(label);
    }

    @Override
    public void deleteById(Long id) {
        iLabelRepository.deleteById(id);
    }


    @Override
    public Label findByName(String name) {
        return iLabelRepository.findByName(name);
    }

}
