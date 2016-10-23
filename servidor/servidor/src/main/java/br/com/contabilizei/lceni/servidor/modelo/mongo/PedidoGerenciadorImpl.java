package br.com.contabilizei.lceni.servidor.modelo.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.com.contabilizei.lceni.servidor.modelo.Cliente;
import br.com.contabilizei.lceni.servidor.modelo.Pedido;
import br.com.contabilizei.lceni.servidor.modelo.Produto;

public class PedidoGerenciadorImpl implements Pedido.Gerenciador {

	private static final String COLLECTION = "pedidos";
	private MongoDatabase db = MongoUtil.instance.db;
	
	public Collection<Pedido> listar() {
		Collection<Pedido> pedidos = new ArrayList<Pedido>();
		
		MongoCursor<Document> cursor = db.getCollection(COLLECTION).find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				
				Pedido pedido = new Pedido();
				pedido.setId(doc.get("_id").toString());
				pedido.setNumero(doc.getInteger("numero"));
				pedido.setDataEmissao(doc.getDate("dataEmissao"));
				
				if (doc.get("cliente") != null) {
					Cliente cliente = Cliente.getGerenciadorGenerico().recuperar(doc.get("cliente").toString());
					pedido.setCliente(cliente);
				}
				
				Collection<ObjectId> produtos = doc.get("produtos", Collection.class);
				if (produtos != null) {
					for (ObjectId id : produtos) {
						Produto produto = Produto.getGerenciador().recuperar(id.toString());
						if (produto != null) {
							pedido.adicionarProduto(produto);
						}
					}
				}
				
				pedidos.add(pedido);
			}
		} finally {
			cursor.close();
		}
		
		return pedidos;
	}
	
	public Pedido criar(Pedido pedido) {
		MongoCollection<Document> pedidos = db.getCollection(COLLECTION);
		
		Document doc = new Document("numero", pedido.getNumero())
				.append("dataEmissao", pedido.getDataEmissao());
		
		try {
			if (pedido.getCliente() != null) {
				doc.append("cliente", new ObjectId(pedido.getCliente().getId()));
			}
			
			List<Produto> produtoIds = pedido.getProdutos();
			pedido.limparListaDeProdutos();
			
			if (produtoIds != null) {
				List<ObjectId> produtos = new ArrayList<ObjectId>();
				for (Produto p : produtoIds) {
					ObjectId oid = new ObjectId(p.getId());
					produtos.add(oid);
					
					pedido.adicionarProduto(Produto.getGerenciador().recuperar(oid.toString()));
				}
				doc.append("produtos", produtos);
			}
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return null;
		}
		
		pedidos.insertOne(doc);
		pedido.setId(doc.get("_id").toString());
		
		return pedido;
	}
	
	public Pedido salvar(Pedido pedido) {
		MongoCollection<Document> pedidos = db.getCollection(COLLECTION);

		Document doc = new Document("numero", pedido.getNumero())
				.append("dataEmissao", pedido.getDataEmissao());
		
		try {
			if (pedido.getCliente() != null) {
				doc.append("cliente", new ObjectId(pedido.getCliente().getId()));
			}
			
			List<Produto> produtoIds = pedido.getProdutos();
			pedido.limparListaDeProdutos();
			
			if (produtoIds != null) {
				List<ObjectId> produtos = new ArrayList<ObjectId>();
				for (Produto p : produtoIds) {
					ObjectId oid = new ObjectId(p.getId());
					produtos.add(oid);
					
					pedido.adicionarProduto(Produto.getGerenciador().recuperar(oid.toString()));
				}
				doc.append("produtos", produtos);
			}
		
			UpdateResult r = pedidos.replaceOne(eq("_id", new ObjectId(pedido.getId())), doc);
			if (r.getMatchedCount() == 1) {
				return pedido;
			}
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return null;
		}

		return null;
	}

	public Pedido recuperar(String id) {
		try {
			Document doc = db.getCollection(COLLECTION).find(eq("_id", new ObjectId(id))).first();
			
			if (doc != null) {
				Pedido pedido = new Pedido();
				pedido.setId(doc.get("_id").toString());
				pedido.setNumero(doc.getInteger("numero"));
				pedido.setDataEmissao(doc.getDate("dataEmissao"));

				if (doc.get("cliente") != null) {
					Cliente cliente = Cliente.getGerenciadorGenerico().recuperar(doc.get("cliente").toString());
					pedido.setCliente(cliente);
				}

				Collection<ObjectId> produtos = doc.get("produtos", Collection.class);
				if (produtos != null) {
					for (ObjectId oid : produtos) {
						Produto produto = Produto.getGerenciador().recuperar(oid.toString());
						if (produto != null) {
							pedido.adicionarProduto(produto);
						}
					}
				}
				
				return pedido;
			}
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return null;
		}
		
		return null;
	}

	public boolean remover(String id) {
		try {
			DeleteResult r = db.getCollection(COLLECTION).deleteOne(eq("_id", new ObjectId(id)));
			if (r.getDeletedCount() == 1) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return false;
		}
		
		return false;
	}
}
