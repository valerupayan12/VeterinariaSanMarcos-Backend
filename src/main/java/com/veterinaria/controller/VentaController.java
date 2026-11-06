package com.veterinaria.controller;

import java.util.List;

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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Venta", description = "Operaciones relacionadas con ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> getAllVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public Venta getVentaById(@PathVariable Long id) {
        return ventaService.getVentaById(id);
    }

    @PostMapping
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaService.saveVenta(venta);
    }

    @DeleteMapping("/{id}")
    public void deleteVenta(@PathVariable Long id) {
        ventaService.deleteVenta(id);
    }
}