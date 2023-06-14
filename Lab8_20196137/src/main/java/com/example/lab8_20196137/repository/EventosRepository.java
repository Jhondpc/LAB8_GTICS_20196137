package com.example.lab8_20196137.repository;

import com.example.lab8_20196137.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventosRepository extends JpaRepository<Evento, Integer> {
}
