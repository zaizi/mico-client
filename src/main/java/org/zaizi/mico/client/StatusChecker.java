package org.zaizi.mico.client;

import java.util.List;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.status.impl.StatusResponse;

public interface StatusChecker {
	
	public static final String STATUS_CHECK_PATH = "/broker/status/items";
	
	public List<StatusResponse> checkItemStatus(ContentItem contentItem, boolean parts) throws MicoClientException;

}
