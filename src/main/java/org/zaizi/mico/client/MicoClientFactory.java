package org.zaizi.mico.client;

import org.apache.http.client.utils.URIBuilder;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
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
	private String host;
	private String username;
	String password;
	
	public MicoClientFactory(String host, String username, String password){
		uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http").setHost(host).setUserInfo(username, password);    
	}
	
	public final Injector createInjectorClient(){
		return new InjectorImpl(uriBuilder);
	}
	
	public final QueryClient createQueryServiceClient() throws MicoClientException{
	    QueryClient queryClient = null;
	    try
        {
	        SPARQLRepository repo = new SPARQLRepository(host + QueryClient.QUERY_SELECT_ENDPONT);
	        repo.setUsernameAndPassword(username, password);
	        repo.initialize();
	        Anno4j anno4j = new Anno4j(repo);
	        queryClient = new QueryClientImpl(anno4j);
        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occured when creating QueryClient", ex);
        }  
	    return queryClient;
	}
	
	public final StatusChecker createStatusChecker(){
		return new StatusCheckerImpl(uriBuilder);
	}
}
