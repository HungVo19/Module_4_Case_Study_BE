package com.example.back_end.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private LocalDate createdDate;
    private String content;
    private String image;
    private boolean status;
    @Column(nullable = false)
    private boolean privacy;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @Transient
    private List<Label> labels;

}
