package org.zaizi.mico.client;


import org.apache.http.client.utils.URIBuilder;
import org.openrdf.repository.sparql.SPARQLRepository;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.injector.impl.InjectorImpl;
import org.zaizi.mico.client.query.impl.QueryClientImpl;
import org.zaizi.mico.client.status.impl.StatusCheckerImpl;

import com.github.anno4j.Anno4j;

/**
 * 
 * @author Chalitha Perera
 *
 */
public class MicoClientFactory {

	private URIBuilder uriBuilder;
	private String username;
	String password;

	public MicoClientFactory(String host, String username, String password) {
		uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http").setHost(host).setUserInfo(username, password);
		this.username = username;
		this.password = password;
	}

	public final Injector createInjectorClient() {
		return new InjectorImpl(uriBuilder);
	}

	public final QueryClient createQueryServiceClient() throws MicoClientException {
		QueryClient queryClient = null;
		try {
			Anno4j anno4j = new Anno4j();
			SPARQLRepository repo = new SPARQLRepository(uriBuilder.build() + QueryClient.QUERY_SELECT_ENDPONT, uriBuilder.build() + QueryClient.QUERY_UPDATE_ENDPONT);
			repo.setUsernameAndPassword(username, password);
			repo.initialize();
			anno4j.setRepository(repo);
			queryClient = new QueryClientImpl(anno4j);
		} catch (Exception ex) {
			throw new MicoClientException("Exception occured when creating QueryClient", ex);
		}
		return queryClient;
	}

	public final StatusChecker createStatusChecker() {
		return new StatusCheckerImpl(uriBuilder);
	}
}
