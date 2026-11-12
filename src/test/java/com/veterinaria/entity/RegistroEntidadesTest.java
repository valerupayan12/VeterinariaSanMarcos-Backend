package com.veterinaria.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.datafaker.Faker;

class RegistroEntidadesTest {

    private final Faker faker = new Faker();

    @Test
    void registrarMascota() {
        // ARRANGE (preparar)
        Mascota mascota = new Mascota();
        String nombre = faker.animal().name();
        mascota.setNombre(nombre);
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setEdad(faker.number().numberBetween(1, 15));

        // ACT (ejecutar)
        String nombreObtenido = mascota.getNombre();

        // ASSERT (verificar)
        assertNotNull(mascota);
        assertEquals(nombre, nombreObtenido);
        assertTrue(mascota.getEdad() >= 1 && mascota.getEdad() <= 15);
    }

    @Test
    void registrarUsuario() {
        // ARRANGE (preparar)
        Usuario usuario = new Usuario();
        usuario.setNombre(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setTelefono(faker.phoneNumber().phoneNumber());

        // ACT (ejecutar)
        String email = usuario.getEmail();

        // ASSERT (verificar)
        assertNotNull(usuario);
        assertNotNull(email);
        assertTrue(email.contains("@"));
    }

    @Test
    void registrarProducto() {
        // ARRANGE (preparar)
        Producto producto = new Producto();
        producto.setName(faker.commerce().productName());
        producto.setPrice(faker.number().randomDouble(2, 1000, 50000));

        // ACT (ejecutar)
        double precio = producto.getPrice();

        // ASSERT (verificar)
        assertNotNull(producto);
        assertNotNull(producto.getName());
        assertTrue(precio > 0);
    }

    @Test
    void registrarVenta() {
        // ARRANGE (preparar)
        Venta venta = new Venta();
        double total = faker.number().randomDouble(2, 1000, 100000);
        venta.setTotal(total);

        // ACT (ejecutar)
        double totalObtenido = venta.getTotal();

        // ASSERT (verificar)
        assertNotNull(venta);
        assertEquals(total, totalObtenido, 0.01);
    }
}
