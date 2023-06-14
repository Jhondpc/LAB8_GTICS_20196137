package com.example.lab8_20196137.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tipo_ticket_evento")
public class TipoTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String precio;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "idEvento")
    private Evento evento;

}
