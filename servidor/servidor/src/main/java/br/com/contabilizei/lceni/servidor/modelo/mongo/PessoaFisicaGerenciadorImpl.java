package br.com.contabilizei.lceni.servidor.modelo.mongo;

import org.bson.Document;

import br.com.contabilizei.lceni.servidor.modelo.Cliente;
import br.com.contabilizei.lceni.servidor.modelo.PessoaFisica;

public class PessoaFisicaGerenciadorImpl extends ClienteGerenciadorImpl {

	public Document popularDoc(Document doc, Cliente cliente) {
		if (doc == null || cliente == null) {
			throw new IllegalArgumentException("doc ou cliente estão nulos");
		}

		PessoaFisica pf = (PessoaFisica) cliente;
		return doc.append("nome", pf.getNome()).append("cpf", pf.getCpf());
	}
	
}
