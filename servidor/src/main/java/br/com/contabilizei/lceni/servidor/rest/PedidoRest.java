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

import br.com.contabilizei.lceni.servidor.modelo.Pedido;

@Path("/pedidos")
public class PedidoRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		Collection<Pedido> pedidos = Pedido.getGerenciador().listar();
		return Response.ok(pedidos.toArray(new Pedido[0])).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") String id) {
		Pedido pedido = Pedido.getGerenciador().recuperar(id);
		
		if (pedido != null) {
			return Response.ok(pedido).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response criar(Pedido pedidoImpl) {
		Pedido pedido = Pedido.getGerenciador().criar(pedidoImpl);
		return Response.ok(pedido).header("Location", "/pedidos/" + pedido.getId()).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response substituir(@PathParam("id") String id, Pedido pedidoImpl) {
		pedidoImpl.setId(id);
		pedidoImpl = Pedido.getGerenciador().salvar(pedidoImpl);
		
		return Response.status(pedidoImpl != null ? Status.OK : Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@PathParam("id") String id) {
		boolean ok = Pedido.getGerenciador().remover(id);
		return Response.status(ok ? Status.OK : Status.NOT_FOUND).build();
	}
}
