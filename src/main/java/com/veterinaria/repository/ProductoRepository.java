package com.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
