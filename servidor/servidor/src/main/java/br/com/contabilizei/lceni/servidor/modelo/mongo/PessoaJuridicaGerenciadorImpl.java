package br.com.contabilizei.lceni.servidor.modelo.mongo;

import org.bson.Document;

import br.com.contabilizei.lceni.servidor.modelo.Cliente;
import br.com.contabilizei.lceni.servidor.modelo.PessoaJuridica;

public class PessoaJuridicaGerenciadorImpl extends ClienteGerenciadorImpl {

	public Document popularDoc(Document doc, Cliente cliente) {
		if (doc == null || cliente == null) {
			throw new IllegalArgumentException("doc ou cliente estão nulos");
		}

		PessoaJuridica pj = (PessoaJuridica) cliente;
		return doc.append("razaoSocial", pj.getRazaoSocial()).append("cnpj", pj.getCnpj());
	}
}
