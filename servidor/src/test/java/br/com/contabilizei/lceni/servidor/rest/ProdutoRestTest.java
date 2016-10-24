package br.com.contabilizei.lceni.servidor.rest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.contabilizei.lceni.servidor.JettyUtil;
import br.com.contabilizei.lceni.servidor.modelo.Produto;
import br.com.contabilizei.lceni.servidor.modelo.mongo.MongoUtil;
import br.com.contabilizei.lceni.servidor.modelo.mongo.ProdutoGerenciadorImpl;

/**
 * Testa o serviço rest para produtos (ex. http://localhost:8080/produtos)
 */
public class ProdutoRestTest {
	
	@Before
	public void prepararTeste() throws Exception {
		MongoUtil.instance.inicializar();
		Produto.setGerenciador(new ProdutoGerenciadorImpl());
		
		JettyUtil.instance.inicializar();
	}
	
	@After
	public void encerrarTeste() {
		MongoUtil.instance.encerrar();
		JettyUtil.instance.encerrar();
	}
	
	/**
	 * Testa o fluxo REST de salvar um Produto
	 * @throws Exception
	 */
	@Test
	public void salvarProduto() throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		
		// código do produto gerado aleatoriamente
		String uuid = UUID.randomUUID().toString();
		
		String conteudo = "{\"codigo\":\""+ uuid +"\",\"descricao\":\"aaa\",\"valor\":5.55}";
		
		// insere o produto com o código gerado
		ContentResponse r = httpClient.POST("http://localhost:8080/produtos")
			.content(new StringContentProvider(conteudo))
			.header("Content-Type", "application/json")
			.send();
		
		// certifica-se que o código UUID retornou corretamente
		assertTrue(r.getStatus() < 400);
		String conteudoInserido = r.getContentAsString();
		assertTrue(conteudoInserido.contains(uuid));
		
		// certifica-se que o código permanece na listagem após a inserção
		r = httpClient.GET("http://localhost:8080/produtos");
		assertTrue(r.getStatus() < 400);
		conteudo = r.getContentAsString();
		assertTrue(conteudo.contains(uuid));
		
		// altera o produto e tenta salvar novamente.
		int inicio = conteudoInserido.indexOf("\"id\":\"");
		int fim = conteudoInserido.indexOf("\",", inicio);
		String id = conteudoInserido.substring(inicio+6, fim);
		
		// certifica-se que o objeto foi criado com id unico e contém o uuid gerado.
		r = httpClient.GET("http://localhost:8080/produtos/" + id);
		assertTrue(r.getStatus() < 400);
		conteudo = r.getContentAsString();
		assertTrue(conteudo.contains(uuid));
		
		String uuidNovo = UUID.randomUUID().toString();
		String produtoAlterado = conteudoInserido.replace("aaa", "bbb").replace(uuid, uuidNovo);

		// certifica-se que o produto sofreu as alterações de uuid e de descrição
		r = httpClient.POST("http://localhost:8080/produtos/")
				.content(new StringContentProvider(produtoAlterado))
				.header("Content-Type", "application/json")
				.send();
		assertTrue(r.getStatus() < 400);
		conteudo = r.getContentAsString();
		assertTrue(conteudo.contains(uuidNovo));
		assertFalse(conteudo.contains(uuid));
		assertTrue(conteudo.contains("bbb"));
		
		// remove o objeto recém criado, limpado o banco de dados para novos testes
		r = httpClient.newRequest("http://localhost:8080/produtos/" + id)
				.method(HttpMethod.DELETE)
				.send();

		// certifica-se que o status retornado é OK (200)
		assertTrue(r.getStatus() < 400);
		
		// certifica-se que tanto o antigo quanto o novo UUID, não existem mais
		r = httpClient.GET("http://localhost:8080/produtos");
		assertTrue(r.getStatus() < 400);
		conteudo = r.getContentAsString();
		assertFalse(conteudo.contains(uuid));
		assertFalse(conteudo.contains(uuid));
		
		httpClient.stop();
	}
}
