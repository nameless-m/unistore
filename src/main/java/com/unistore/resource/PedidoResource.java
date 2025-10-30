package com.unistore.resource;

import com.unistore.entity.Pedido;
import com.unistore.entity.Cliente;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

  @GET
  public List<Pedido> listarTodosPedidos() {
    return Pedido.listAll();
  }

  @GET
  @Path("/{id}")
  public Response obterPedido(@PathParam("id") Long id) {
    Pedido pedido = Pedido.findById(id);
    if (pedido == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(pedido).build();
  }

  @GET
  @Path("/cliente/{clienteId}")
  public List<Pedido> obterPedidosPorCliente(@PathParam("clienteId") Long clienteId) {
    return Pedido.list("cliente.id", clienteId);
  }

  @POST
  @Transactional
  public Response criarPedido(Pedido pedido) {
    // Valida se o cliente existe
    Cliente cliente = Cliente.findById(pedido.cliente.id);
    if (cliente == null) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Cliente não encontrado").build();
    }

    pedido.cliente = cliente;
    pedido.persist();

    return Response.status(Response.Status.CREATED).entity(pedido).build();
  }

  @PUT
  @Path("/{id}/status")
  @Transactional
  public Response atualizarStatusPedido(@PathParam("id") Long id,
      @QueryParam("status") Pedido.StatusPedido status) {
    Pedido pedido = Pedido.findById(id);
    if (pedido == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    pedido.status = status;
    return Response.ok(pedido).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response cancelarPedido(@PathParam("id") Long id) {
    Pedido pedido = Pedido.findById(id);
    if (pedido == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    if (pedido.status == Pedido.StatusPedido.ENVIADO ||
        pedido.status == Pedido.StatusPedido.ENTREGUE) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Não é possível cancelar pedidos enviados ou entregues").build();
    }

    pedido.status = Pedido.StatusPedido.CANCELADO;
    return Response.ok(pedido).build();
  }
}
