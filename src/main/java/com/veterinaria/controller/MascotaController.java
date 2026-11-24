package com.veterinaria.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.entity.Mascota;
import com.veterinaria.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
@Tag(name = "Mascota", description = "Operaciones relacionadas con mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    @GetMapping
    @Operation(summary = "Listar mascotas", description = "Obtiene la lista de todas las mascotas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public List<Mascota> getAllMascotas() {
        return mascotaService.getAllMascotas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID", description = "Obtiene los detalles de una mascota específica por su ID")
    @ApiResponse(responseCode = "200", description = "Mascota obtenida exitosamente")
    @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    public Mascota getMascotaById(@PathVariable Long id) {
        return mascotaService.getMascotaById(id);
    }

    @PostMapping
    @Operation(summary = "Crear mascota", description = "Crea una nueva mascota")
    @ApiResponse(responseCode = "201", description = "Mascota creada exitosamente")
    public Mascota createMascota(@Valid @RequestBody Mascota mascota) {
        return mascotaService.saveMascota(mascota);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota específica por su ID")
    @ApiResponse(responseCode = "200", description = "Mascota eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    public void deleteMascota(@PathVariable Long id) {
        mascotaService.deleteMascota(id);
    }
}