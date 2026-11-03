package com.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    
}
