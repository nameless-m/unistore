package com.unistore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.sql.Blob;

@Entity
public class Produto extends PanacheEntity {

  public String nome;
  public BigDecimal preco;
  public Integer estoque;
  public Blob imagem;

  public Produto() {
  }

  public Produto(String nome, BigDecimal preco, Integer estoque, Blob imagem) {
    this.nome = nome;
    this.preco = preco;
    this.estoque = estoque;
    this.imagem = imagem;
  }
}
