package com.unistore.resource;

import com.unistore.entity.Cliente;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {
    
    @GET
    public List<Cliente> listarTodosClientes() {
        return Cliente.list("ativo = true");
    }
    
    @GET
    @Path("/{id}")
    public Response obterCliente(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cliente).build();
    }
    
    @POST
    @Path("/registrar")
    @Transactional
    public Response registrarCliente(Cliente cliente) {
        // Verifica se o email já existe
        Cliente existente = Cliente.find("email", cliente.email).firstResult();
        if (existente != null) {
            return Response.status(Response.Status.CONFLICT)
                .entity("Email já cadastrado").build();
        }
        
        // Criptografa a senha
        cliente.senha = BcryptUtil.bcryptHash(cliente.senha);
        cliente.persist();
        
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarCliente(@PathParam("id") Long id, Cliente clienteAtualizado) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        cliente.nome = clienteAtualizado.nome;
        cliente.sobrenome = clienteAtualizado.sobrenome;
        cliente.telefone = clienteAtualizado.telefone;
        cliente.endereco = clienteAtualizado.endereco;
        cliente.cidade = clienteAtualizado.cidade;
        cliente.estado = clienteAtualizado.estado;
        cliente.cep = clienteAtualizado.cep;
        cliente.pais = clienteAtualizado.pais;
        
        return Response.ok(cliente).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarCliente(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        cliente.ativo = false;
        return Response.noContent().build();
    }
}
