package com.intranetstemdo.intranet.usuarios;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String rol;

    @Column(nullable = false)
    private boolean activo = true;

    protected Usuario() {
        // constructor sin argumentos que exige JPA
    }

    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
    public boolean isActivo() { return activo; }
}
