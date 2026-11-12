-- V1__crea_tablas.sql
-- Esquema inicial de la base de datos veterinaria_san_marcos_db.

CREATE TABLE mascota (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    especie VARCHAR(255) DEFAULT NULL,
    raza VARCHAR(255) DEFAULT NULL,
    edad INT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE producto (
    id_producto BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    precio DOUBLE NOT NULL,
    PRIMARY KEY (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    telefono VARCHAR(255) DEFAULT NULL,
    mascota_id BIGINT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_usuario_mascota (mascota_id),
    CONSTRAINT fk_usuario_mascota FOREIGN KEY (mascota_id) REFERENCES mascota (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE venta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    total DOUBLE NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
