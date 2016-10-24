package br.com.contabilizei.lceni.servidor.modelo.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.com.contabilizei.lceni.servidor.modelo.Produto;

public class ProdutoGerenciadorImpl implements Produto.Gerenciador {

	private static final String COLLECTION = "produtos";
	private MongoDatabase db = MongoUtil.instance.db;
	
	public Collection<Produto> listar() {
		Collection<Produto> produtos = new ArrayList<Produto>();
		
		MongoCursor<Document> cursor = db.getCollection(COLLECTION).find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();

				Produto produto = new Produto();
				produto.setId(doc.get("_id").toString());
				produto.setCodigo(doc.getString("codigo"));
				produto.setDescricao(doc.getString("descricao"));
				
				if (doc.get("valor") != null) {
					produto.setValor(new BigDecimal(doc.getDouble("valor")));
				}
				
				produtos.add(produto);
			}
		} finally {
			cursor.close();
		}
		
		return produtos;
	}
	
	public Produto criar(Produto produto) {
		MongoCollection<Document> produtos = db.getCollection(COLLECTION);
		
		Document doc = new Document("codigo", produto.getCodigo())
				.append("descricao", produto.getDescricao());
		
		if (produto.getValor() != null) {
			doc.append("valor", produto.getValor().doubleValue());
		}
		
		produtos.insertOne(doc);
		produto.setId(doc.get("_id").toString());
		
		return produto;
	}
	
	public Produto salvar(Produto produto) {
		MongoCollection<Document> produtos = db.getCollection(COLLECTION);

		Document doc = new Document("codigo", produto.getCodigo())
				.append("descricao", produto.getDescricao());
		
		if (produto.getValor() != null) {
			doc.append("valor", produto.getValor().doubleValue());
		}

		try {
			UpdateResult r = produtos.replaceOne(eq("_id", new ObjectId(produto.getId())), doc);
			if (r.getMatchedCount() == 1) {
				return produto;
			}
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return null;
		}

		return null;
	}

	public Produto recuperar(String id) {
		try {
			Document doc = db.getCollection(COLLECTION).find(eq("_id", new ObjectId(id))).first();
			
			if (doc != null) {
				Produto produto = new Produto();
				produto.setId(doc.get("_id").toString());
				produto.setCodigo(doc.getString("codigo"));
				produto.setDescricao(doc.getString("descricao"));
				
				if (doc.get("valor") != null) {
					produto.setValor(new BigDecimal(doc.getDouble("valor")));
				}
				
				return produto;
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
