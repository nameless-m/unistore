package com.unistore.resource;

import com.unistore.entity.Produto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> listarTodos() {
        return Produto.listAll();
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(produto).build();
    }
    
    @POST
    @Transactional
    public Response criar(Produto produto) {
        produto.persist();
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Produto produtoAtualizado) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        produto.nome = produtoAtualizado.nome;
        produto.preco = produtoAtualizado.preco;
        produto.estoque = produtoAtualizado.estoque;
        produto.imagem = produtoAtualizado.imagem;
        
        return Response.ok(produto).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        boolean deleted = Produto.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
