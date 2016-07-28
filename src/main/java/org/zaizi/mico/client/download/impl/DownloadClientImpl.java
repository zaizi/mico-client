package org.zaizi.mico.client.download.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaizi.mico.client.DownloadClient;
import org.zaizi.mico.client.exception.MicoClientException;

public class DownloadClientImpl implements DownloadClient {

	private static final Logger logger = LoggerFactory.getLogger(DownloadClientImpl.class);

	private URIBuilder uriBuilder;

	public DownloadClientImpl(URIBuilder uriBuilder) {
		this.uriBuilder = uriBuilder;
	}

	@Override
	public String downloadTranscript(String urn, String itemUri) throws MicoClientException {
		
		String content = "";
		try {
			String[] urnSplitted = urn.split(":");
			String paths = urnSplitted[urnSplitted.length - 1];
			String[] pathSplitted = paths.split("/");
			
			URIBuilder baseURIBuilder = new URIBuilder().setScheme(uriBuilder.getScheme()).setHost(uriBuilder.getHost());
			
			String partUriPath = new StringBuilder(MARMOTTA_PATH).append(pathSplitted[0]).toString();
			
			String partUri = new URIBuilder(baseURIBuilder.build().toString()).setPath(partUriPath).build().toString();
			
			URIBuilder builderCopy = new URIBuilder(uriBuilder.build().toString());
			URI downloadUri = builderCopy.setPath(DOWNLOAD_PATH).setParameter("itemUri", itemUri).setParameter("partUri", partUri).build();

			final HttpGet getRequest = new HttpGet(downloadUri);
            
            final CloseableHttpClient client = HttpClients.createDefault();
            final CloseableHttpResponse response = client.execute(getRequest);
            
            content = EntityUtils.toString(response.getEntity()); 
            
		} catch (URISyntaxException e) {
			logger.error("Invalide URL", e);
			throw new MicoClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
