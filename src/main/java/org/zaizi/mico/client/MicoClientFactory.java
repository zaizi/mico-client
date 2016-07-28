package org.zaizi.mico.client;


import org.apache.http.client.utils.URIBuilder;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.zaizi.mico.client.download.impl.DownloadClientImpl;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.injector.impl.InjectorImpl;
import org.zaizi.mico.client.query.impl.Anno4jQueryClientImpl;
import org.zaizi.mico.client.status.impl.StatusCheckerImpl;

import com.github.anno4j.Anno4j;

/**
 * Provide factory to create mico clients
 * @author Chalitha Perera
 *
 */
public class MicoClientFactory {

	private URIBuilder uriBuilder;
	private String username;
	String password;

	/**
	 * 
	 * @param host
	 * @param username
	 * @param password
	 */
	public MicoClientFactory(String host, String username, String password) {
		uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http").setHost(host).setUserInfo(username, password);
		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 * @return Injector
	 */
	public final Injector createInjectorClient() {
		return new InjectorImpl(uriBuilder);
	}

	/**
	 * 
	 * @return QueryClient
	 * @throws MicoClientException
	 */
	public final QueryClient createQueryServiceClient() throws MicoClientException {
		QueryClient queryClient = null;
		try {
			Anno4j anno4j = new Anno4j();
			SPARQLRepository repo = new SPARQLRepository(uriBuilder.build() + QueryClient.QUERY_SELECT_ENDPOINT, uriBuilder.build() + QueryClient.QUERY_UPDATE_ENDPOINT);
			repo.setUsernameAndPassword(username, password);
			repo.initialize();
			anno4j.setRepository(repo);
			queryClient = new Anno4jQueryClientImpl(anno4j);
		} catch (Exception ex) {
			throw new MicoClientException("Exception occured when creating QueryClient", ex);
		}
		return queryClient;
	}

	/**
	 * 
	 * @return StatusChecker
	 */
	public final StatusChecker createStatusChecker() {
		return new StatusCheckerImpl(uriBuilder);
	}
	
	public final DownloadClient createDownloadClient(){
		return new DownloadClientImpl(uriBuilder);
	}
}
