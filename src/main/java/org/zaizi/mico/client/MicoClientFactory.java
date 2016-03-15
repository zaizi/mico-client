package org.zaizi.mico.client;

import org.apache.http.client.utils.URIBuilder;
import org.zaizi.mico.client.injector.impl.InjectorImpl;
import org.zaizi.mico.client.status.impl.StatusCheckerImpl;

/**
 * 
 * @author Chalitha Perera
 *
 */
public class MicoClientFactory {
	
	private URIBuilder uriBuilder;
	
	public MicoClientFactory(String host, String username, String password){
		uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http").setHost(host).setUserInfo(username, password);
	}
	
	public final Injector createInjectorClient(){
		return new InjectorImpl(uriBuilder);
	}
	
	public final StatusChecker createStatusChecker(){
		return new StatusCheckerImpl(uriBuilder);
	}

}
