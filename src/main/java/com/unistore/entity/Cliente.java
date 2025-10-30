package com.unistore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente extends PanacheEntity {
    
    @Column(nullable = false)
    public String nome;
    
    @Column(nullable = false)
    public String sobrenome;
    
    @Column(nullable = false, unique = true)
    public String email;
    
    @Column(nullable = false)
    public String senha;
    
    public String telefone;
    
    public String endereco;
    
    public String cidade;
    
    public String estado;
    
    public String cep;
    
    public String pais;
    
    @Column(nullable = false)
    public Boolean ativo = true;
    
    @Column(nullable = false, updatable = false)
    public LocalDateTime criadoEm;
    
    @Column(nullable = false)
    public LocalDateTime atualizadoEm;
    
    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
