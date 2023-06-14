package com.example.lab8_20196137.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fecha;

    private String nombre;

    private String descripcion;

    @Column(name = "path_image")
    private String pathImage;

    @ManyToOne
    @JoinColumn(name = "idLocal")
    private Local local;


}
