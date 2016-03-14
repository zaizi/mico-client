package org.zaizi.mico.client.model;

public class ContentPart {
	
	private String uri;
	private String mimeType;
	private String name;
	
	
	public ContentPart(String uri, String mimeType, String name){
		this.uri = uri;
		this.mimeType = mimeType;
		this.name = name;
	}
	
	public String getUri(){
		return uri;
	}
	
	public String getType(){
		return mimeType;
	}
	
	public String getName(){
		return name;
	}

}
