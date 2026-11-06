
// Producto.java
package com.veterinaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;
    private String nombre;
    private double precio;

    public Producto() {}

    public Long getId() { return id_producto; }
    public void setId(Long id_producto) { this.id_producto = id_producto; }
    public String getName() { return nombre; }
    public void setName(String nombre) { this.nombre = nombre; }
    public double getPrice() { return precio; }
    public void setPrice(double precio) { this.precio = precio; }
}