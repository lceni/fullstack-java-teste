package br.com.contabilizei.lceni.servidor.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.NotSupportedException;
import javax.xml.bind.annotation.XmlList;

public class Pedido {
	
	public interface Gerenciador {
		public Pedido criar(Pedido produto);
		public Pedido salvar(Pedido produto);
		public Collection<Pedido> listar();
		public Pedido recuperar(String id);
		public boolean remover(String id);
	}
	
	private static Pedido.Gerenciador gerenciador = null;

	public static Pedido.Gerenciador getGerenciador() {
		return Pedido.gerenciador;
	}

	public static void setGerenciador(Pedido.Gerenciador gerenciador) {
		Pedido.gerenciador = gerenciador;
	}
	
	private String id;
	private Integer numero;
	private Date dataEmissao;
	private Cliente cliente;
	@XmlList
	private List<Produto> produtos;
	private BigDecimal total;

	public Pedido() {
		this.produtos = new ArrayList<Produto>();
	}

	public Pedido(String id, Integer numero, Date dataEmissao, Cliente cliente, List<Produto> produtos,
			BigDecimal total) {
		super();
		this.id = id;
		this.numero = numero;
		this.dataEmissao = dataEmissao;
		this.cliente = cliente;
		this.produtos = produtos;
		this.total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Produto> getProdutos() {
		return new ArrayList<Produto>(produtos);
	}
	
	public void setProdutos(List<Produto> produtos) {
		throw new NotSupportedException("Método não suportado, para editar a lista de produtos, utilize os métodos, adicionar e remover produto");
	}

	public BigDecimal getTotal() {
		
		// verifica se o total é nulo, se sim, precisa calcular a partir da listagem de produtos
		if (total == null) {
			total = BigDecimal.ZERO;
			
			for (Produto p : produtos) {
				total = total.add(p.getValor());
			}
		}
		
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		throw new NotSupportedException("Método não suportado, o total é calculado de acordo com os produtos inseridos no pedido.");
	}
	
	public void adicionarProduto(Produto produto) {
		if (produto == null) {
			throw new IllegalArgumentException("Produto não pode ser nulo");
		}
		
		this.produtos.add(produto);
		
		// reinicia o total para ser recalculado quando o método getTotal for executado.
		total = null;
	}
	
	public void removerProduto(Produto produto) {
		if (produto == null) {
			throw new IllegalArgumentException("Produto não pode ser nulo");
		}
		
		this.produtos.remove(produto);
		
		// reinicia o total para ser recalculado quando o método getTotal for executado.
		this.total = null;
	}
	
	public void limparListaDeProdutos() {
		this.produtos.clear();
		this.total = null;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataEmissao == null) ? 0 : dataEmissao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((produtos == null) ? 0 : produtos.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		Pedido other = (Pedido) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataEmissao == null) {
			if (other.dataEmissao != null)
				return false;
		} else if (!dataEmissao.equals(other.dataEmissao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
}
