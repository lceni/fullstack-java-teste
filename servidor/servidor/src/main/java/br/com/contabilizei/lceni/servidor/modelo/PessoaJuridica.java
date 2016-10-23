package br.com.contabilizei.lceni.servidor.modelo;

import javax.xml.bind.annotation.XmlTransient;

public class PessoaJuridica extends Cliente {
	
private static PessoaJuridica.Gerenciador gerenciadorPJ = null;
	
	public static void setGerenciador(PessoaJuridica.Gerenciador gerenciador) {
		PessoaJuridica.gerenciadorPJ = gerenciador;
	}

	@XmlTransient
	public PessoaJuridica.Gerenciador getGerenciador() {
		return gerenciadorPJ;
	}
	
	private String razaoSocial;
	private String cnpj;

	public PessoaJuridica() {
	}

	public PessoaJuridica(String id, String telefone, String email, String razaoSocial, String cnpj) {
		super(id, telefone, email);
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
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
		PessoaJuridica other = (PessoaJuridica) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		return true;
	}
}
