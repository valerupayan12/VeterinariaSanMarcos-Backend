// Usuario.java
package com.veterinaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    public Usuario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
}