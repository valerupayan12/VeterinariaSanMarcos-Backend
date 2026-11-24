package com.veterinaria.service;

import com.veterinaria.entity.Usuario;
import com.veterinaria.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(rolSeguro(usuario.getRol()))
                .build();
    }

    // Spring espera roles sin el prefijo ROLE_; si el dato viene vacio se asume cliente.
    private String rolSeguro(String rol) {
        if (rol == null || rol.isBlank()) {
            return "CLIENTE";
        }
        return rol.trim().toUpperCase();
    }
}
