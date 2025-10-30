package com.unistore.resource;

import com.unistore.entity.Cliente;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cliente).build();
    }
    
    @POST
    @Transactional
    public Response criar(Cliente cliente) {
        cliente.persist();
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Cliente clienteAtualizado) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        cliente.nome = clienteAtualizado.nome;
        cliente.cpf = clienteAtualizado.cpf;
        cliente.telefone = clienteAtualizado.telefone;
        cliente.email = clienteAtualizado.email;
        cliente.endereco = clienteAtualizado.endereco;
        
        return Response.ok(cliente).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        boolean deleted = Cliente.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
