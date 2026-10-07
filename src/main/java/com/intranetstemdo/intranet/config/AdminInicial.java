package com.intranetstemdo.intranet.config;

import com.intranetstemdo.intranet.usuarios.Usuario;
import com.intranetstemdo.intranet.usuarios.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("prod")
public class AdminInicial {

    private static final Logger log = LoggerFactory.getLogger(AdminInicial.class);

    @Bean
    CommandLineRunner crearAdminInicial(UsuarioRepository repo,
                                        PasswordEncoder encoder,
                                        @Value("${app.admin-password:}") String password) {
        return args -> {
            if (repo.count() > 0) {
                return;
            }
            if (password.isBlank()) {
                log.warn("No hay usuarios y no se ha definido ADMIN_PASSWORD: nadie podrá entrar.");
                return;
            }
            repo.save(new Usuario("admin", encoder.encode(password), "ADMIN"));
            log.info("Administrador inicial creado.");
        };
    }
}