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

import com.veterinaria.entity.Mascota;
import com.veterinaria.entity.Producto;
import com.veterinaria.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Producto", description = "Operaciones relacionadas con productos")
public class ProductoController {


    @Autowired
    private ProductoService productoService;

    @GetMapping
     @Operation(summary = "Listar productos", description = "Obtiene la lista de todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene los detalles de un producto específico por su ID")
    @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public Producto getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto específico por su ID")
    @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }
}