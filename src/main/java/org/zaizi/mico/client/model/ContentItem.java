package org.zaizi.mico.client.model;

import java.util.HashMap;
import java.util.Map;

public class ContentItem {
	private String uri;
	private String uuid;
	private Map<String, ContentPart> contentParts;
	
	public ContentItem(String uri, String uuid){
		this.uri = uri;
		this.uuid = uuid;
		contentParts = new HashMap<>();
	}
	
	public String getUri(){
		return this.uri;
	}
	
	public String getID(){
		return this.uuid;
	}
	
	public void addContentPart(ContentPart contentPart){
		this.contentParts.put(contentPart.getUri(), contentPart);
	}
	
	public Map<String, ContentPart> getContentParts(){
		return contentParts;
	}
	
	public ContentPart getContentPart(String uri){
		return contentParts.get(uri);
	}
}
