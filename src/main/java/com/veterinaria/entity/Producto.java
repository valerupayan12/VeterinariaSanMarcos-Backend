
// Producto.java
package com.veterinaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    @NotBlank(message = "nombre obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    public Producto() {}

    public Long getId() { return id_producto; }
    public void setId(Long id_producto) { this.id_producto = id_producto; }
    public String getName() { return nombre; }
    public void setName(String nombre) { this.nombre = nombre; }
    public double getPrice() { return precio; }
    public void setPrice(double precio) { this.precio = precio; }
}