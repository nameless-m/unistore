package com.unistore.resource;

import com.unistore.entity.Produto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    
    @GET
    public List<Produto> listarTodosProdutos(@QueryParam("categoria") String categoria) {
        if (categoria != null && !categoria.isEmpty()) {
            return Produto.list("categoria = ?1 and ativo = true", categoria);
        }
        return Produto.list("ativo = true");
    }
    
    @GET
    @Path("/{id}")
    public Response obterProduto(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(produto).build();
    }
    
    @POST
    @Transactional
    public Response criarProduto(Produto produto) {
        produto.persist();
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarProduto(@PathParam("id") Long id, Produto produtoAtualizado) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        produto.nome = produtoAtualizado.nome;
        produto.descricao = produtoAtualizado.descricao;
        produto.preco = produtoAtualizado.preco;
        produto.estoque = produtoAtualizado.estoque;
        produto.urlImagem = produtoAtualizado.urlImagem;
        produto.categoria = produtoAtualizado.categoria;
        produto.ativo = produtoAtualizado.ativo;
        
        return Response.ok(produto).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarProduto(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        produto.ativo = false;
        return Response.noContent().build();
    }
    
    @GET
    @Path("/buscar")
    public List<Produto> buscarProdutos(@QueryParam("q") String consulta) {
        return Produto.list("lower(nome) like ?1 and ativo = true", 
            "%" + consulta.toLowerCase() + "%");
    }
}
