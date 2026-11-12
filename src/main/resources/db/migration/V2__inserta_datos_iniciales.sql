-- V2__inserta_datos_iniciales.sql
-- Datos semilla (seed data) para probar la API.

INSERT INTO mascota (nombre, especie, raza, edad) VALUES
('Firulais', 'Perro', 'Labrador', 3),
('Michi', 'Gato', 'Siames', 2),
('Rocky', 'Perro', 'Bulldog', 5);

INSERT INTO producto (nombre, precio) VALUES
('Alimento premium 3kg', 24990),
('Vacuna antirrabica', 12000),
('Collar antipulgas', 8500);

INSERT INTO usuario (nombre, email, telefono, mascota_id)
SELECT 'Ana Perez', 'ana.perez@correo.cl', '+56911111111', id
FROM mascota WHERE nombre = 'Firulais' LIMIT 1;

INSERT INTO venta (total) VALUES (37490);
