package br.com.contabilizei.lceni.servidor;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

public class JettyUtil {
	public final static JettyUtil instance = new JettyUtil();
	private static Server jettyServer;
	
	private JettyUtil() {
	}
	
	public void inicializar() throws Exception {
		System.out.println("Iniciando o servidor...");

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		jettyServer = new Server(8080);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);
		
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "br.com.contabilizei.lceni.servidor.rest");
		
		FilterHolder corsFilter = context.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
		corsFilter.setInitParameter("allowedMethods", "GET,POST,DELETE,PUT,HEAD");

		jettyServer.start();
		System.out.println("Pronto.");
	}
	
	public void join() throws Exception {
		jettyServer.join();
	}
	
	public void encerrar() throws IllegalStateException {
		if (jettyServer == null) {
			throw new IllegalStateException("O servidor não foi inicalizado.");
		}
		
		try {
			jettyServer.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
