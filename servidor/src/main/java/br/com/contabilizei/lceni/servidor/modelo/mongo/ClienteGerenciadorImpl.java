package br.com.contabilizei.lceni.servidor.modelo.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import br.com.contabilizei.lceni.servidor.modelo.Cliente;
import br.com.contabilizei.lceni.servidor.modelo.PessoaFisica;
import br.com.contabilizei.lceni.servidor.modelo.PessoaJuridica;

public class ClienteGerenciadorImpl implements Cliente.Gerenciador {

	private static final String COLLECTION = "clientes";
	private static final String PESSOA_FISICA = PessoaFisica.class.getCanonicalName();
	private static final String PESSOA_JURIDICA = PessoaJuridica.class.getCanonicalName();
	private final MongoDatabase db = MongoUtil.instance.db;
	
	public Collection<Cliente> listar() {
		Collection<Cliente> clientes = new ArrayList<Cliente>();
		
		MongoCursor<Document> cursor = db.getCollection(COLLECTION).find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();

				String tipo = doc.getString("tipo");
				String id = doc.get("_id").toString();
				String telefone = doc.getString("telefone");
				String email = doc.getString("email");
				
				Cliente cliente = null;
				if (PESSOA_FISICA.equals(tipo)) {
					String nome = doc.getString("nome");
					String cpf = doc.getString("cpf");
					
					cliente = new PessoaFisica(id, telefone, email, nome, cpf);
				} else if (PESSOA_JURIDICA.equals(tipo)) {
					String razaoSocial = doc.getString("razaoSocial");
					String cnpj = doc.getString("cnpj");
					
					cliente = new PessoaJuridica(id, telefone, email, razaoSocial, cnpj);
				} else {
					continue;
				}
				
				clientes.add(cliente);
			}
		} finally {
			cursor.close();
		}
		
		return clientes;
	}
	
	public Cliente criar(Cliente cliente) {
		MongoCollection<Document> clientes = db.getCollection(COLLECTION);
		
		Document doc = new Document("tipo", cliente.getClass().getCanonicalName())
			.append("telefone", cliente.getTelefone())
			.append("email", cliente.getEmail());
		
		doc = popularDoc(doc, cliente);
		
		clientes.insertOne(doc);
		cliente.setId(doc.get("_id").toString());
		
		return cliente;
	}
	
	public Cliente salvar(Cliente cliente) {
		MongoCollection<Document> clientes = db.getCollection(COLLECTION);

		Document doc = new Document("tipo", cliente.getClass().getCanonicalName())
			.append("telefone", cliente.getTelefone())
			.append("email", cliente.getEmail());
			
		doc = popularDoc(doc, cliente);
		
		try {
			UpdateResult r = clientes.replaceOne(eq("_id", new ObjectId(cliente.getId())), doc);
			if (r.getMatchedCount() == 1) {
				return cliente;
			}
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return null;
		}

		return null;
	}

	public Cliente recuperar(String id) {
		try {
			Document doc = db.getCollection(COLLECTION).find(eq("_id", new ObjectId(id))).first();
			
			Cliente cliente = null;
			if (doc != null) {
				String tipo = doc.getString("tipo");
				String oid = doc.get("_id").toString();
				String telefone = doc.getString("telefone");
				String email = doc.getString("email");
				
				if (PESSOA_FISICA.equals(tipo)) {
					String nome = doc.getString("nome");
					String cpf = doc.getString("cpf");
					
					cliente = new PessoaFisica(oid, telefone, email, nome, cpf);
				} else if (PESSOA_JURIDICA.equals(tipo)) {
					String razaoSocial = doc.getString("razaoSocial");
					String cnpj = doc.getString("cnpj");
					
					cliente = new PessoaJuridica(oid, telefone, email, razaoSocial, cnpj);
				}
			}
			
			return cliente;
		} catch (IllegalArgumentException e) {
			// caso o id seja com tamanho inválido ou com caracter inválido, impossibilitando a criação do ObjectId
			return null;
		}
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
	
	public Document popularDoc(Document doc, Cliente cliente) {
		return doc;
	}
}
