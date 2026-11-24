-- V6__catalogo_inicial_completo.sql
-- Carga el mismo catalogo que usa el frontend (23 productos) para que la tienda
-- muestre los productos de la base de datos sin perder imágenes ni detalles.
-- Las imágenes las aporta el frontend por id, porque son archivos del bundle.

INSERT INTO producto (id_producto, nombre, descripcion, precio, categoria, stock, requiere_receta) VALUES
(1, 'Alimento Premium Perro Adulto 15kg', 'Fórmula balanceada alta en proteínas y vitaminas.', 25990, 'alimento', 15, FALSE),
(2, 'Alimento Felino Estéril 3kg', 'Control de peso e higiene urinaria para gatos.', 14990, 'alimento', 9, FALSE),
(3, 'Pipeta Antipulgas y Garrapatas', 'Protección mensual efectiva para mascotas.', 8990, 'farmacia', 16, TRUE),
(4, 'Desparasitante Interno Multidosis', 'Elimina parásitos intestinales en perros y gatos.', 6500, 'farmacia', 10, TRUE),
(5, 'Cama Acolchada Lavable Talla L', 'Máximo confort y tela resistente para tu mascota.', 18990, 'accesorios', 17, FALSE),
(6, 'Juguete Mordedor Interactivo', 'Fomenta la salud dental y reduce el estrés.', 5490, 'accesorios', 11, FALSE),
(7, 'Alimento Especializado para Conejos', 'Mix de heno y pellets con fibra y vitaminas esenciales.', 12990, 'alimento', 18, FALSE),
(8, 'Casilla de Descanso para Conejo', 'Refugio cómodo y seguro con ventilación. Ideal para interiores.', 16490, 'accesorios', 12, FALSE),
(9, 'Juguete Mordedor Madera para Conejo', 'Juguetes de madera natural 100% seguros para roer. Pack x3.', 7990, 'accesorios', 19, FALSE),
(10, 'Alimento Premium para Hámster', 'Mezcla nutritiva de granos, semillas y vegetales deshidratados.', 4990, 'alimento', 13, FALSE),
(11, 'Jaula Completa para Hámster 40x40cm', 'Jaula con accesorios: rueda, comedero, bebedero y casa.', 19990, 'accesorios', 20, FALSE),
(12, 'Rueda de Ejercicio Silenciosa para Hámster', 'Rueda de 20cm sin ruido con cojinetes de precisión.', 9990, 'accesorios', 14, FALSE),
(13, 'Heno Premium Timothy para Conejos 1kg', 'Heno de alta calidad, fibra natural para digestión óptima.', 6990, 'alimento', 8, FALSE),
(14, 'Mix de Verduras Deshidratadas para Conejo', 'Zanahorias, manzana y perejil deshidratados. Snack saludable.', 5490, 'alimento', 15, FALSE),
(15, 'Corral Portátil para Conejo 120x100cm', 'Recinto seguro para ejercitarse al aire libre. Fácil de transportar.', 24990, 'accesorios', 9, FALSE),
(16, 'Túneles de PVC para Hámster Pack x4', 'Túneles conectables para crear circuitos de diversión.', 8490, 'accesorios', 16, FALSE),
(17, 'Suplemento Vitamínico para Hámster', 'Complejo vitamínico en forma de grageas nutritivas.', 4690, 'farmacia', 10, TRUE),
(18, 'Lecho Sanitario para Hámster 5L', 'Lecho absorbente seguro y confortable para pequeños roedores.', 3490, 'accesorios', 17, FALSE),
(19, 'Alimento Premium para Pájaros 1kg', 'Mix de semillas variadas con nutrientes esenciales. Para canarios y periquitos.', 7990, 'alimento', 11, FALSE),
(20, 'Jaula Completa para Pequeños Pájaros 60x40x60cm', 'Jaula con perchas, comedero, bebedero y casa. Fácil de limpiar.', 34990, 'accesorios', 18, FALSE),
(21, 'Set de Perchas de Madera Natural Pack x3', 'Perchas de diferentes diámetros para canarios, periquitos y diamantes.', 6490, 'accesorios', 12, FALSE),
(22, 'Juguetes Interactivos para Pájaros Pack x4', 'Campanas, espejos y columpios para entretenimiento.', 9890, 'accesorios', 19, FALSE),
(23, 'Arena Sanitaria para Pájaros 2kg', 'Arena con minerales para el fondo de la jaula. Higiénica y absorbente.', 4290, 'accesorios', 13, FALSE)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    descripcion = VALUES(descripcion),
    precio = VALUES(precio),
    categoria = VALUES(categoria),
    stock = VALUES(stock),
    requiere_receta = VALUES(requiere_receta);

INSERT INTO producto_mascota (producto_id, mascota) VALUES
(1, 'perro'),
(2, 'gato'),
(3, 'perro'),
(3, 'gato'),
(4, 'perro'),
(4, 'gato'),
(5, 'perro'),
(5, 'gato'),
(6, 'perro'),
(7, 'conejo'),
(8, 'conejo'),
(9, 'conejo'),
(10, 'hamster'),
(11, 'hamster'),
(12, 'hamster'),
(13, 'conejo'),
(14, 'conejo'),
(15, 'conejo'),
(16, 'hamster'),
(17, 'hamster'),
(18, 'hamster'),
(19, 'pajaro'),
(20, 'pajaro'),
(21, 'pajaro'),
(22, 'pajaro'),
(23, 'pajaro');
