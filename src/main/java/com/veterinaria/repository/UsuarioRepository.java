package com.veterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
