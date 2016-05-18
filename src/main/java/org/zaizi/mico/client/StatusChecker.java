package org.zaizi.mico.client;

import java.util.List;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.status.impl.StatusResponse;

/**
 * Check status of a given content item (As MICO platform is asynchronous, use this as a polling mechanism to 
 * check whether the content item is processed by the platform or not)
 * @author Chalitha Perera
 *
 */
public interface StatusChecker {
	
	public static final String STATUS_CHECK_PATH = "/broker/status/items";
	
	/**
	 * Check Item Status
	 * @param contentItemURI
	 * @param parts
	 * @return
	 * @throws MicoClientException
	 */
	public List<StatusResponse> checkItemStatus(String contentItemURI, boolean parts) throws MicoClientException;


}
