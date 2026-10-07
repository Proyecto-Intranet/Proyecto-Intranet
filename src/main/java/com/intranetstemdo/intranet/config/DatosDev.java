package com.intranetstemdo.intranet.config;

import com.intranetstemdo.intranet.usuarios.Usuario;
import com.intranetstemdo.intranet.usuarios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DatosDev {

    @Bean
    CommandLineRunner crearUsuariosDev(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new Usuario("admin", encoder.encode("admin1234"), "ADMIN"));
            }
            if (repo.findByUsername("usuario").isEmpty()) {
                repo.save(new Usuario("usuario", encoder.encode("usuario1234"), "USER"));
            }
        };
    }
}
