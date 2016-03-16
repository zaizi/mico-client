package org.zaizi.mico.client.injector.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaizi.mico.client.Injector;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class InjectorImpl implements Injector{
	
	private URIBuilder uriBuilder;
	
	private static final Logger logger = LoggerFactory.getLogger(InjectorImpl.class);
	
	public InjectorImpl(URIBuilder uriBuilder){
		this.uriBuilder = uriBuilder;
	}

	@Override
	public ContentItem createContentItem() throws MicoClientException {
		try {
			URIBuilder builderCopy = new URIBuilder(uriBuilder.build().toString());
			URI createUri = builderCopy.setPath(INJECT_CREATE_PATH).build();
			
			final HttpPost post = new HttpPost(createUri);
			final CloseableHttpResponse response = this.sendRequest(post);
			
			if(response.getStatusLine().getStatusCode() != Response.Status.OK.getStatusCode()){
				throw new MicoClientException("Could not create content item....");
			}
			
			final String responseBody = EntityUtils.toString(response.getEntity());
            HttpClientUtils.closeQuietly(response);

            ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode node = objectMapper.readTree(responseBody);
            
            if(!node.has("uri")){
            	throw new MicoClientException("Could not find URI of the contentItem....");
            }
            
            final String contentItemUri = node.get("uri").asText();
            String[] parts = contentItemUri.split("/");
            ContentItem contentItem = new ContentItem(contentItemUri, parts[parts.length-1]);
			
            return contentItem;
			
		} catch (URISyntaxException e) {
			logger.error("Invalide URL", e);
			throw new MicoClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error("Error in sending create request to mico", e);
			throw new MicoClientException(e.getMessage());
		} catch (IOException e) {
			logger.error("Error in sending create request to mico", e);
			throw new MicoClientException(e.getMessage());
		}
	}
	
	@Override
	public ContentPart addContentPart(ContentItem contentItem, String mimeType, String name, InputStream inputStream) throws MicoClientException {
		try {
			URIBuilder builderCopy = new URIBuilder(uriBuilder.build().toString());
			URI addUri = builderCopy.setPath(INJECT_ADD_PATH).setParameter("ci", contentItem.getUri())
					.setParameter("type", mimeType)
					.setParameter("name", name)
					.build();
			
			final HttpPost post = new HttpPost(addUri);
			File tempFile = File.createTempFile("mico-"+contentItem.getID(), "tmp");
			FileUtils.copyInputStreamToFile(inputStream, tempFile);
			
			FileEntity entity = new FileEntity(tempFile);
			post.setEntity(entity);
			
			final CloseableHttpResponse response = this.sendRequest(post);
			
			if(response.getStatusLine().getStatusCode() != Response.Status.OK.getStatusCode()){
				throw new MicoClientException("Could not create content part....");
			}
			
			final String responseBody = EntityUtils.toString(response.getEntity());
            HttpClientUtils.closeQuietly(response);

            ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode node = objectMapper.readTree(responseBody);
            
            if(!node.has("uri")){
            	throw new MicoClientException("Could not find URI of the contentPart....");
            }
            
            final String contentPartUri = node.get("uri").asText();
            
            ContentPart contentPart = new ContentPart(contentPartUri, mimeType, name);
            
            tempFile.delete();
            
            return contentPart;
            
		} catch (URISyntaxException e) {
			logger.error("Invalide URL", e);
			throw new MicoClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error("Error in sending add request to mico", e);
			throw new MicoClientException(e.getMessage());
		} catch (IOException e) {
			logger.error("Error in sending add request to mico", e);
			throw new MicoClientException(e.getMessage());
		}
		
	}


	@Override
	public void submitContentItem(ContentItem contentItem) throws MicoClientException {
		try {
			URIBuilder builderCopy = new URIBuilder(uriBuilder.build().toString());
			URI submitUri = builderCopy.setPath(INJECT_SUBMIT_PATH).setParameter("ci", contentItem.getUri()).build();
			final HttpPost post = new HttpPost(submitUri);
			
			final CloseableHttpResponse response = this.sendRequest(post);
			
			if(response.getStatusLine().getStatusCode() != Response.Status.OK.getStatusCode()){
				throw new MicoClientException("Could not submit content item....");
			}
			
			logger.info("Successfully Submitted ContentItem...");
			
		} catch (URISyntaxException e) {
			logger.error("Invalide URL", e);
			throw new MicoClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error("Error in sending submit request to mico", e);
			throw new MicoClientException(e.getMessage());
		} catch (IOException e) {
			logger.error("Error in sending submit request to mico", e);
			throw new MicoClientException(e.getMessage());
		}
		
	}
	
	
	private CloseableHttpResponse sendRequest(HttpPost post) throws ClientProtocolException, IOException{
		final CloseableHttpClient client = HttpClients.createDefault();
		final CloseableHttpResponse response = client.execute(post);
		return response;
	}

}
