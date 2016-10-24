package br.com.contabilizei.lceni.servidor.modelo;

import java.math.BigDecimal;
import java.util.Collection;

public class Produto {
	
	public interface Gerenciador {
		public Produto criar(Produto produto);
		public Produto salvar(Produto produto);
		public Collection<Produto> listar();
		public Produto recuperar(String id);
		public boolean remover(String id);
	}
	
	private static Produto.Gerenciador gerenciador = null;

	public static Produto.Gerenciador getGerenciador() {
		return Produto.gerenciador;
	}

	public static void setGerenciador(Produto.Gerenciador gerenciador) {
		Produto.gerenciador = gerenciador;
	}

	private String id;
	private String codigo;
	private String descricao;
	private BigDecimal valor;

	public Produto() {
	}

	public Produto(String id, String codigo, String descricao, BigDecimal valor) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.valor = valor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
