package br.com.contabilizei.lceni.servidor.modelo.mongo;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
	public final static MongoUtil instance = new MongoUtil();
	public MongoDatabase db;
	private MongoClient mongo;

	private MongoUtil() {
	}

	public void inicializar() {
		if (mongo == null && db == null) {

//			this.mongo = new MongoClient("localhost", 27017);
			ServerAddress endereco = new ServerAddress("ds011735.mlab.com", 11735);
			MongoCredential credencial = MongoCredential.createCredential("contabilizei", "contabilizei", "contabilizei".toCharArray());
			this.mongo = new MongoClient(endereco, Arrays.asList(credencial)); // user contabilize, pass contabilizei
			this.db = mongo.getDatabase("contabilizei");

		}
	}

	public void encerrar() {
		mongo.close();
	}

	public MongoDatabase getDb() {
		return db;
	}
}
