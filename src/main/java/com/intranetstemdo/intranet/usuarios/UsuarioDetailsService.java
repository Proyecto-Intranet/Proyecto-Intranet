package com.intranetstemdo.intranet.usuarios;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario: " + username));

        return User.withUsername(u.getUsername())
            .password(u.getPassword())      // aquí va el hash BCrypt guardado en la BD
            .roles(u.getRol())              // "ADMIN" pasa a ser la autoridad ROLE_ADMIN
            .disabled(!u.isActivo())
            .build();
    }
}