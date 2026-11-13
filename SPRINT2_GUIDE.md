# Guía de implementación para Sprint 2 - Spring Boot

## 1. Objetivo

Para este segundo sprint, el backend de Veterinaria San Marcos debe incluir:

- Swagger/OpenAPI para documentación y pruebas de los endpoints REST.
- Autenticación JWT para proteger rutas y validar usuarios.
- Testing con JUnit 5 y generación de datos con DataFaker.
- Flyway para versionado y migración de la base de datos.

En el proyecto actual ya se observan avances relevantes:

- `springdoc-openapi-starter-webmvc-ui` está agregado en `pom.xml`.
- `net.datafaker:datafaker` ya está configurado para pruebas.
- `spring-boot-starter-flyway` y `flyway-mysql` ya están presentes.

Lo que aún falta es completar la configuración de seguridad JWT y dejar activo el flujo real de Flyway.

---

## 2. Swagger / OpenAPI

### Paso 1: Validar la dependencia

El proyecto ya incluye esta dependencia:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.2.0</version>
</dependency>
```

### Paso 2: Configurar la documentación

En `src/main/resources/application.properties` ya existe esta configuración:

```properties
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
```

También se puede dejar una configuración más completa con `SwaggerConfig.java`:

```java
package com.veterinaria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI veterinariaOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Veterinaria San Marcos API")
                .version("2.0")
                .description("Documentación de servicios REST del proyecto de Veterinaria"));
    }
}
```

### Paso 3: Probar Swagger

Una vez levantado el proyecto, se accede desde:

```text
http://localhost:8081/swagger-ui.html
```

Esto permite:

- Ver todos los endpoints.
- Probar solicitudes REST directamente desde el navegador.
- Ver los modelos de entrada/salida.

---

## 3. Autenticación JWT

### Paso 1: Agregar dependencias de seguridad

Agregar al `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
```

### Paso 2: Crear clase para generar/validar el token

```java
package com.veterinaria.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

### Paso 3: Crear la configuración de seguridad

```java
package com.veterinaria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

### Paso 4: Crear login de autenticación

Se recomienda crear un controlador para autenticación:

```java
package com.veterinaria.controller;

import com.veterinaria.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}
```

### Paso 5: Configurar secretos en properties

```properties
jwt.secret=QWxhZGRpbjpvcGVuIHNlc2FtZQ==
jwt.expiration=3600000
```

> La clave JWT debe ser segura y preferentemente generada con un valor base64 largo.

### Paso 6: Probar la autenticación

- Enviar una petición POST al endpoint `/auth/login`.
- Obtener el token JWT.
- Enviar el token en la cabecera:

```http
Authorization: Bearer <token>
```

---

## 4. Testing con JUnit y DataFaker

### Paso 1: Verificar dependencias

El proyecto ya tiene:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>net.datafaker</groupId>
    <artifactId>datafaker</artifactId>
    <version>2.4.2</version>
    <scope>test</scope>
</dependency>
```

### Paso 2: Crear prueba con JUnit + Faker

```java
package com.veterinaria.service;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    private final Faker faker = new Faker();

    @Test
    void deberiaCrearProductoConDatosAleatorios() {
        String nombre = faker.commerce().productName();
        double precio = faker.number().randomDouble(2, 100, 5000);

        assertNotNull(nombre);
        assertTrue(precio > 0);
    }
}
```

### Paso 3: Ejecutar pruebas

```bash
./mvnw test
```

### Paso 4: Generar datos de prueba

DataFaker sirve para:

- Crear nombres, emails, teléfonos, direcciones.
- Generar clientes, mascotas, productos y ventas con valores realistas.
- Simular carga de datos para pruebas de integración.

Ejemplo:

```java
Faker faker = new Faker();
String nombre = faker.name().fullName();
String email = faker.internet().emailAddress();
String telefono = faker.phoneNumber().cellPhone();
```

---

## 5. Flyway para migración de base de datos

### Paso 1: Verificar dependencias

El proyecto ya las incluye:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-flyway</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

### Paso 2: Configurar `application.properties`

Se recomienda usar:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_san_marcos_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Mexico_City
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

### Paso 3: Crear el script de migración

Archivo recomendado:

`src/main/resources/db/migration/V1__crea_tablas.sql`

Ejemplo:

```sql
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS mascotas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    especie VARCHAR(50) NOT NULL,
    usuario_id BIGINT,
    CONSTRAINT fk_mascotas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

### Paso 4: Ejecutar migración

Cuando se levanta la aplicación con `Flyway` habilitado, automáticamente ejecuta todas las migraciones en orden de versión.

### Paso 5: Buenas prácticas

- Cada script debe tener nombre con prefijo `V<number>__descripcion.sql`.
- No se deben modificar migraciones ya aplicadas.
- Si se cambia el esquema, se crea una nueva versión, no se edita la anterior.

---

## 6. Ejecución del proyecto

### Comandos clave

```bash
./mvnw clean test
./mvnw spring-boot:run
```

### URLs de acceso

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`
- Login JWT: `http://localhost:8081/auth/login`

---

## 7. Checklist para entregar el Sprint 2

- [ ] Swagger documentado y accesible.
- [ ] Seguridad JWT configurada.
- [ ] Endpoints protegidos y login funcional.
- [ ] Pruebas JUnit ejecutadas con éxito.
- [ ] DataFaker usado en pruebas.
- [ ] Flyway activado con migraciones versionadas.
- [ ] Documentación clara para el profesor.

---

## 8. Observación importante

El proyecto actual ya incluye varias dependencias del sprint 2, pero antes de entregarlo debe quedar consistente:

- `spring.flyway.enabled=false` debe cambiarse a `true` cuando se quiera usar migrations reales.
- `spring.jpa.hibernate.ddl-auto=update` debe cambiarse a `validate` para usar Flyway como fuente real del esquema.
- La seguridad JWT debe quedar finalizada para proteger los endpoints del backend.

Con esto, el proyecto queda listo para demostrar un backend moderno con documentación, autenticación, pruebas y migraciones controladas.
