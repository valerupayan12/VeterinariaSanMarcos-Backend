-- V1__crea_tablas.sql
-- Esquema inicial de la base de datos veterinaria_san_marcos_db.
--
-- IMPORTANTE:
-- - IF NOT EXISTS evita errores si Flyway intenta ejecutar esta migracion otra vez.
-- - INDEX se usa para compatibilidad con SQL mas portable y evitar problemas
--   con sintaxis mas especifica de MySQL.
-- - Se elimina ENGINE=... y COLLATE=... para mantener la migracion simple y
--   compatible con la configuracion de Spring Boot y pruebas.

CREATE TABLE IF NOT EXISTS mascota (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    especie VARCHAR(255) DEFAULT NULL,
    raza VARCHAR(255) DEFAULT NULL,
    edad INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS producto (
    id_producto BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    precio DOUBLE NOT NULL,
    PRIMARY KEY (id_producto)
);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    telefono VARCHAR(255) DEFAULT NULL,
    mascota_id BIGINT DEFAULT NULL,
    PRIMARY KEY (id),
    INDEX fk_usuario_mascota (mascota_id),
    CONSTRAINT fk_usuario_mascota FOREIGN KEY (mascota_id) REFERENCES mascota (id)
);

CREATE TABLE IF NOT EXISTS venta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    total DOUBLE NOT NULL,
    PRIMARY KEY (id)
);
