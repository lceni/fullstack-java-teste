package br.com.contabilizei.lceni.servidor.modelo;

import java.util.Collection;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.bson.Document;

@XmlSeeAlso({ PessoaFisica.class, PessoaJuridica.class })
public abstract class Cliente {
	
	public interface Gerenciador {
		public Cliente criar(Cliente produto);
		public Cliente salvar(Cliente produto);
		public Collection<Cliente> listar();
		public Cliente recuperar(String id);
		public boolean remover(String id);
		public Document popularDoc(Document doc, Cliente cliente);
	}
	
	private static Cliente.Gerenciador gerenciador = null;
	
	public static Cliente.Gerenciador getGerenciadorGenerico() {
		return Cliente.gerenciador;
	}

	public static void setGerenciador(Cliente.Gerenciador gerenciador) {
		Cliente.gerenciador = gerenciador;
	}
	
	@XmlTransient
	public Cliente.Gerenciador getGerenciador() {
		return gerenciador;
	}
	
	private String id;
	private String telefone;
	private String email;

	public Cliente() {
	}

	public Cliente(String id, String telefone, String email) {
		super();
		this.id = id;
		this.telefone = telefone;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}
}
