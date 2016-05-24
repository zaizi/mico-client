package org.zaizi.mico.client;

import java.io.InputStream;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;

/**
 * Interface for creating and injecting content items for mico platform
 * @author Chalitha Perera
 *
 */
public interface Injector {
	public static final String INJECT_CREATE_PATH = "/broker/inject/create";
	public static final String INJECT_ADD_PATH = "/broker/inject/add";
	public static final String INJECT_SUBMIT_PATH = "/broker/inject/submit";
	
	
	/**
	 * Create a contentItem by requesting the mico platform server
	 * @return ContentItem
	 * @throws MicoClientException 
	 */
	public ContentItem createContentItem(String type, String name, InputStream inputStream) throws MicoClientException;
	

	/**
	 * Create a content part for a content item
	 * @param contentItem
	 * @param mimeType
	 * @param name
	 * @return
	 * @throws MicoClientException
	 */
	public ContentPart addContentPart(ContentItem contentItem, String mimeType, String name, InputStream inputStream) throws MicoClientException;
	
	
	/**
	 * submit content item to mico platform
	 * @param contentItem
	 * @throws MicoClientException 
	 */
	public void submitContentItem(ContentItem contentItem) throws MicoClientException;
	 
	
}
