package com.unistore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
public class Produto extends PanacheEntity {
    
    @Column(nullable = false)
    public String nome;
   
    @Column(length = 1000)
    public String descricao;
    
    @Column(nullable = false)
    public BigDecimal preco;
    
    @Column(nullable = false)
    public Integer estoque;
    
    public Blob urlImagem;
    
    @Column(nullable = false)
    public String categoria;
    
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
