# Plan de Pruebas - Veterinaria San Marcos

## Objetivo

Validar el correcto funcionamiento del backend de la Veterinaria San Marcos mediante pruebas unitarias utilizando JUnit y generación de datos ficticios con DataFaker, aplicando la metodología AAA (Arrange - Act - Assert).

---

## Herramientas

- Java 22
- Spring Boot 4.1.3-SNAPSHOT
- JUnit 5
- Mockito
- DataFaker 2.4.2
- H2 (base de datos en memoria para pruebas)

---

# Casos de Prueba

# CP-01: Registrar Mascota

## Método

Creación de objeto `Mascota`

## Objetivo

Verificar que una mascota pueda crearse correctamente con información válida.

## Resultado Esperado

* El objeto Mascota debe existir.
* El nombre de la mascota no debe ser nulo.
* La edad debe estar dentro del rango asignado.

---

# CP-02: Registrar Usuario

## Método

Creación de objeto `Usuario`

## Objetivo

Verificar que un usuario pueda crearse correctamente con información válida.

## Resultado Esperado

* El objeto Usuario debe existir.
* El correo electrónico no debe ser nulo.
* El correo electrónico debe contener un formato válido.

---

# CP-03: Registrar Producto

## Método

Creación de objeto `Producto`

## Objetivo

Verificar que un producto pueda crearse correctamente con información válida.

## Resultado Esperado

* El objeto Producto debe existir.
* El nombre del producto no debe ser nulo.
* El precio debe ser mayor a cero.

---

# CP-04: Registrar Venta

## Método

Creación de objeto `Venta`

## Objetivo

Verificar que una venta pueda crearse correctamente con su total.

## Resultado Esperado

* El objeto Venta debe existir.
* El total de la venta debe coincidir con el valor asignado.

---

# CP-05: Listar y Guardar Productos (Servicio)

## Método

`ProductoService.getAllProductos()` y `ProductoService.saveProducto()`

## Objetivo

Verificar la correcta colaboración entre el servicio y el repositorio mediante Mockito.

## Resultado Esperado

* La lista de productos no debe ser nula.
* El producto guardado debe conservar sus datos.
* El repositorio debe ser invocado.

---

## Casos de Prueba

| ID    | Caso de Prueba                  | Objetivo                                                        | Resultado Esperado                                                             | Tipo      |
| ----- | ------------------------------- | --------------------------------------------------------------- | ------------------------------------------------------------------------------ | --------- |
| CP-01 | Registrar Mascota               | Validar la creación de una mascota con información válida.       | La mascota existe y posee nombre y edad válidos.                                | Unitaria  |
| CP-02 | Registrar Usuario               | Validar la creación de un usuario con información válida.        | El usuario existe y posee nombre, email y teléfono válidos.                     | Unitaria  |
| CP-03 | Registrar Producto              | Validar la creación de un producto con información válida.       | El producto existe y posee nombre y precio válidos.                             | Unitaria  |
| CP-04 | Registrar Venta                 | Validar la creación de una venta con su total.                   | La venta existe y el total coincide con el valor asignado.                      | Unitaria  |
| CP-05 | Listar y Guardar Productos      | Validar la colaboración entre servicio y repositorio.            | El servicio retorna los productos del repositorio y guarda correctamente.       | Unitaria  |

---

## Ejecución

```bash
./mvnw test
```

Las pruebas utilizan H2 en memoria (`src/test/resources/application.properties`), por lo que no requieren MySQL ni XAMPP en ejecución.
