package com.forumhub.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "perfis")
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    public Perfil() {}
    public Perfil(String nome) { this.nome = nome; }

    public Long getId() { return id; }


    @Override
    public String getAuthority() {
        return nome;
    }

    public String getNome() { return nome; }
}
