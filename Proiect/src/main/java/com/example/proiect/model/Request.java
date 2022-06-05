package com.example.proiect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_sequence")
    @SequenceGenerator(name = "request_sequence", sequenceName = "request_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    private Boolean approved;
}
