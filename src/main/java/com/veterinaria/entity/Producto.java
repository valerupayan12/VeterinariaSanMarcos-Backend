package com.veterinaria.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
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

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Size(max = 50, message = "La categoría no puede superar los 50 caracteres")
    private String categoria;

    @Size(max = 255, message = "La imagen no puede superar los 255 caracteres")
    private String imagen;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "producto_mascota", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "mascota")
    private List<String> mascotas = new ArrayList<>();

    private boolean requiereReceta;

    public Producto() {}

    public Long getId() { return id_producto; }
    public void setId(Long id_producto) { this.id_producto = id_producto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public List<String> getMascotas() { return mascotas; }
    public void setMascotas(List<String> mascotas) {
        this.mascotas = mascotas == null ? new ArrayList<>() : mascotas;
    }

    public boolean isRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(boolean requiereReceta) { this.requiereReceta = requiereReceta; }
}
