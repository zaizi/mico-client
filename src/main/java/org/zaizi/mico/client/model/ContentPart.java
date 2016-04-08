package org.zaizi.mico.client.model;

public class ContentPart {
	
	private String uri;
	private String mimeType;
	private String name;
	
	public ContentPart(String uri){
		this.uri = uri;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setMimeType(String mimeType){
		this.mimeType = mimeType;
	}
	
	public String getUri(){
		return uri;
	}
	
	public String getMimeType(){
		return mimeType;
	}
	
	public String getName(){
		return name;
	}

}
