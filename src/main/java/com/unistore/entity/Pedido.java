package com.unistore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido extends PanacheEntity {

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  public Cliente cliente;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  public StatusPedido status = StatusPedido.PENDENTE;

  @Column(nullable = false)
  public BigDecimal valorTotal;

  public String enderecoEntrega;
  public String cidadeEntrega;
  public String estadoEntrega;
  public String cepEntrega;
  public String paisEntrega;

  public String metodoPagamento;
  public String statusPagamento;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<ItemPedido> itens = new ArrayList<>();

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

  public enum StatusPedido {
    PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO
  }
}

@Entity
@Table(name = "itens_pedido")
class ItemPedido extends PanacheEntity {

  @ManyToOne
  @JoinColumn(name = "pedido_id", nullable = false)
  public Pedido pedido;

  @ManyToOne
  @JoinColumn(name = "produto_id", nullable = false)
  public Produto produto;

  @Column(nullable = false)
  public Integer quantidade;

  @Column(nullable = false)
  public BigDecimal precoUnitario;

  @Column(nullable = false)
  public BigDecimal subtotal;
}
