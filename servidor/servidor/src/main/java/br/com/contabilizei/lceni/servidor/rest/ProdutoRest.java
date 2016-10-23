package br.com.contabilizei.lceni.servidor.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.contabilizei.lceni.servidor.modelo.Produto;

@Path("/produtos")
public class ProdutoRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		Collection<Produto> produtos = Produto.getGerenciador().listar();
		return Response.ok(produtos.toArray(new Produto[0])).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") String id) {
		Produto produto = Produto.getGerenciador().recuperar(id);
		
		if (produto != null) {
			return Response.ok(produto).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response criar(Produto produtoImpl) {
		Produto produto = Produto.getGerenciador().criar(produtoImpl);
		return Response.ok(produto).header("Location", "/produtos/" + produto.getId()).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response substituir(@PathParam("id") String id, Produto produtoImpl) {
		produtoImpl.setId(id);
		produtoImpl = Produto.getGerenciador().salvar(produtoImpl);
		
		return Response.status(produtoImpl != null ? Status.OK : Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@PathParam("id") String id) {
		boolean ok = Produto.getGerenciador().remover(id);
		return Response.status(ok ? Status.OK : Status.NOT_FOUND).build();
	}
}
