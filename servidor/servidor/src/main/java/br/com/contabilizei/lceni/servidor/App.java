package br.com.contabilizei.lceni.servidor;

import br.com.contabilizei.lceni.servidor.modelo.Cliente;
import br.com.contabilizei.lceni.servidor.modelo.Pedido;
import br.com.contabilizei.lceni.servidor.modelo.PessoaFisica;
import br.com.contabilizei.lceni.servidor.modelo.PessoaJuridica;
import br.com.contabilizei.lceni.servidor.modelo.Produto;
import br.com.contabilizei.lceni.servidor.modelo.mongo.ClienteGerenciadorImpl;
import br.com.contabilizei.lceni.servidor.modelo.mongo.MongoUtil;
import br.com.contabilizei.lceni.servidor.modelo.mongo.PedidoGerenciadorImpl;
import br.com.contabilizei.lceni.servidor.modelo.mongo.PessoaFisicaGerenciadorImpl;
import br.com.contabilizei.lceni.servidor.modelo.mongo.PessoaJuridicaGerenciadorImpl;
import br.com.contabilizei.lceni.servidor.modelo.mongo.ProdutoGerenciadorImpl;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		iniciar();
	}
	
	public static void iniciar() {
		try {
			iniciarBancoDeDados();
			iniciarServidor();
		} finally {
			encerrarBanco();
			encerrarServidor();
		}
	}
	
	private static void iniciarBancoDeDados() {
		System.out.println("Iniciando o banco de dados...");
		MongoUtil.instance.inicializar();
		
		Produto.setGerenciador(new ProdutoGerenciadorImpl());
		Cliente.setGerenciador(new ClienteGerenciadorImpl());
		PessoaFisica.setGerenciador(new PessoaFisicaGerenciadorImpl());
		PessoaJuridica.setGerenciador(new PessoaJuridicaGerenciadorImpl());
		Pedido.setGerenciador(new PedidoGerenciadorImpl());
		
		System.out.println("Pronto.");
	}
	
	private static void iniciarServidor() {
		try {
			JettyUtil.instance.inicializar();
			JettyUtil.instance.join();
		} catch (Exception e) {
			System.out.println("Erro ao inicializar o servidor.");
			e.printStackTrace();
		}
	}
	
	private static void encerrarBanco() {
		MongoUtil.instance.encerrar();
	}
	
	private static void encerrarServidor() {
		JettyUtil.instance.encerrar();
	}
}
