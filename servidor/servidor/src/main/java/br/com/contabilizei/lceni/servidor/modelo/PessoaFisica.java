package br.com.contabilizei.lceni.servidor.modelo;

import javax.xml.bind.annotation.XmlTransient;

public class PessoaFisica extends Cliente {
	
	private static PessoaFisica.Gerenciador gerenciadorPF = null;
	
	public static void setGerenciador(PessoaFisica.Gerenciador gerenciador) {
		PessoaFisica.gerenciadorPF = gerenciador;
	}
	
	@XmlTransient
	public PessoaFisica.Gerenciador getGerenciador() {
		return gerenciadorPF;
	}
	
	private String nome;
	private String cpf;

	public PessoaFisica() {
	}

	public PessoaFisica(String id, String telefone, String email, String nome, String cpf) {
		super(id, telefone, email);
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaFisica other = (PessoaFisica) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
