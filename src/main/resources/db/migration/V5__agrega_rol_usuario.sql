-- V5__agrega_rol_usuario.sql
-- Roles del usuario. El administrador del sitio se crea al iniciar la aplicacion
-- (DatosIniciales) con la contrasena encriptada; aqui solo se agrega la columna.

ALTER TABLE usuario
    ADD COLUMN rol VARCHAR(20) NOT NULL DEFAULT 'cliente';
