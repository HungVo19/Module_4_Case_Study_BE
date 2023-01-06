package com.example.back_end.service;

import com.example.back_end.model.Label;

public interface ILabelService extends ICOREService<Label, Long> {
    public Label findByName(String name);
}
