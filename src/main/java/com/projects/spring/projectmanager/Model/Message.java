package com.projects.spring.projectmanager.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int  id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    private Chat chat;


    @ManyToOne
    private User user;
}
