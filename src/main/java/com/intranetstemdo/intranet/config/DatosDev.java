package com.intranetstemdo.intranet.config;

import com.intranetstemdo.intranet.usuarios.Usuario;
import com.intranetstemdo.intranet.usuarios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Crea usuarios de prueba al arrancar (la BD es en memoria). */
@Configuration
public class DatosDev {

    @Bean
    CommandLineRunner crearUsuarios(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(new Usuario("admin", encoder.encode("admin1234"), "ADMIN"));
            repo.save(new Usuario("usuario", encoder.encode("usuario1234"), "USER"));
        };
    }
}
