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

import br.com.contabilizei.lceni.servidor.modelo.Cliente;

@Path("/clientes")
public class ClienteRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		Collection<Cliente> clientes = Cliente.getGerenciadorGenerico().listar();
		return Response.ok(clientes.toArray(new Cliente[0])).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") String id) {
		Cliente cliente = Cliente.getGerenciadorGenerico().recuperar(id);
		
		if (cliente != null) {
			return Response.ok(cliente).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response criar(Cliente clienteImpl) {
		Cliente cliente = clienteImpl.getGerenciador().criar(clienteImpl);
		return Response.ok(cliente).header("Location", "/clientes/" + cliente.getId()).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response substituir(@PathParam("id") String id, Cliente clienteImpl) {
		clienteImpl.setId(id);
		clienteImpl = clienteImpl.getGerenciador().salvar(clienteImpl);
		
		return Response.status(clienteImpl != null ? Status.OK : Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remover(@PathParam("id") String id) {
		boolean ok = Cliente.getGerenciadorGenerico().remover(id);
		return Response.status(ok ? Status.OK : Status.NOT_FOUND).build();
	}
}
