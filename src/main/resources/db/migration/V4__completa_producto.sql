-- V4__completa_producto.sql
-- Completa la tabla producto con los campos que usa el catalogo del frontend
-- y crea la tabla de mascotas asociadas a cada producto.

ALTER TABLE producto
    ADD COLUMN descripcion TEXT DEFAULT NULL,
    ADD COLUMN categoria VARCHAR(50) DEFAULT NULL,
    ADD COLUMN imagen VARCHAR(255) DEFAULT NULL,
    ADD COLUMN stock INT NOT NULL DEFAULT 0,
    ADD COLUMN requiere_receta BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE IF NOT EXISTS producto_mascota (
    producto_id BIGINT NOT NULL,
    mascota VARCHAR(50) DEFAULT NULL,
    INDEX fk_producto_mascota (producto_id),
    CONSTRAINT fk_producto_mascota FOREIGN KEY (producto_id) REFERENCES producto (id_producto) ON DELETE CASCADE
);
