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

import com.veterinaria.entity.Venta;
import com.veterinaria.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Venta", description = "Operaciones relacionadas con ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping
    @Operation(summary = "Listar ventas", description = "Obtiene la lista de todas las ventas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public List<Venta> getAllVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Obtiene los detalles de una venta específica por su ID")
    @ApiResponse(responseCode = "200", description = "Venta obtenida exitosamente")
    @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    public Venta getVentaById(@PathVariable Long id) {
        return ventaService.getVentaById(id);
    }

    @PostMapping
    @Operation(summary = "Crear venta", description = "Crea una nueva venta")
    @ApiResponse(responseCode = "201", description = "Venta creada exitosamente")
    public Venta createVenta(@Valid @RequestBody Venta venta) {
        return ventaService.saveVenta(venta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta", description = "Elimina una venta específica por su ID")
    @ApiResponse(responseCode = "200", description = "Venta eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    public void deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
    }
}