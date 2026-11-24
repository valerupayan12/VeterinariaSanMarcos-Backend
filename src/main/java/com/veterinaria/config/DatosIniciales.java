package com.veterinaria.config;

import com.veterinaria.entity.Usuario;
import com.veterinaria.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Crea (o repara) el administrador del sitio al iniciar la aplicacion.
// Asi el login de "admin@duocuc.cl" funciona sin depender de datos manuales.
@Component
public class DatosIniciales implements CommandLineRunner {

    public static final String ADMIN_EMAIL = "admin@duocuc.cl";
    public static final String ADMIN_PASSWORD = "Admin.123";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DatosIniciales(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        crearAdministrador();
        repararUsuarioSemilla();
    }

    private void crearAdministrador() {
        usuarioRepository.findByEmail(ADMIN_EMAIL).ifPresentOrElse(
                usuario -> {
                    if (!"admin".equalsIgnoreCase(usuario.getRol())) {
                        usuario.setRol("admin");
                        usuarioRepository.save(usuario);
                    }
                },
                () -> {
                    Usuario admin = new Usuario();
                    admin.setNombre("Administrador San Marcos");
                    admin.setEmail(ADMIN_EMAIL);
                    admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
                    admin.setTelefono("+56900000000");
                    admin.setRol("admin");
                    usuarioRepository.save(admin);
                }
        );
    }

    // El usuario de ejemplo de la migracion V3 quedo con password vacio y no podia iniciar sesion.
    private void repararUsuarioSemilla() {
        usuarioRepository.findByEmail("ana.perez@correo.cl").ifPresent(usuario -> {
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(passwordEncoder.encode("Cliente.123"));
                usuarioRepository.save(usuario);
            }
        });
    }
}
