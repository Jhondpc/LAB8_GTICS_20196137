package com.example.lab8_20196137.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idTipoTicket")
    private TipoTicket tipoTicket;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
