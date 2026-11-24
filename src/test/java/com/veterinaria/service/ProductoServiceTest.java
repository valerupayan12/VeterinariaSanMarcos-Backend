package com.veterinaria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.veterinaria.entity.Producto;
import com.veterinaria.repository.ProductoRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private final Faker faker = new Faker();

    @Test
    void listarProductos() {
        // ARRANGE (preparar)
        Producto producto = new Producto();
        producto.setNombre(faker.commerce().productName());
        producto.setPrecio(faker.number().randomDouble(2, 1000, 50000));
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        // ACT (ejecutar)
        List<Producto> resultado = productoService.getAllProductos();

        // ASSERT (verificar)
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(producto.getNombre(), resultado.get(0).getNombre());
        verify(productoRepository).findAll();
    }

    @Test
    void guardarProducto() {
        // ARRANGE (preparar)
        Producto producto = new Producto();
        producto.setNombre(faker.commerce().productName());
        producto.setPrecio(faker.number().randomDouble(2, 1000, 50000));
        when(productoRepository.save(producto)).thenReturn(producto);

        // ACT (ejecutar)
        Producto guardado = productoService.saveProducto(producto);

        // ASSERT (verificar)
        assertNotNull(guardado);
        assertEquals(producto.getNombre(), guardado.getNombre());
        verify(productoRepository).save(producto);
    }

    @Test
    void actualizarProducto() {
        // ARRANGE (preparar)
        Producto existente = new Producto();
        existente.setId(1L);
        existente.setNombre("Nombre anterior");
        existente.setPrecio(1000);

        Producto cambios = new Producto();
        cambios.setNombre("Nombre nuevo");
        cambios.setPrecio(2500);

        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(existente));
        when(productoRepository.save(existente)).thenReturn(existente);

        // ACT (ejecutar)
        Producto actualizado = productoService.updateProducto(1L, cambios);

        // ASSERT (verificar)
        assertEquals("Nombre nuevo", actualizado.getNombre());
        assertEquals(2500, actualizado.getPrecio(), 0.01);
        verify(productoRepository).save(existente);
    }
}
