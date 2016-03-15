package org.zaizi.mico.client.status.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaizi.mico.client.StatusChecker;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StatusCheckerImpl implements StatusChecker {

	private static final Logger logger = LoggerFactory.getLogger(StatusCheckerImpl.class);
	private URIBuilder uriBuilder;

	public StatusCheckerImpl(URIBuilder uriBuilder) {
		this.uriBuilder = uriBuilder;
	}

	@Override
	public List<StatusResponse> checkItemStatus(ContentItem contentItem, boolean parts) throws MicoClientException {
		try {
			URIBuilder builderCopy = new URIBuilder(uriBuilder.build().toString());
			URI checkStatusUri = builderCopy.setPath(STATUS_CHECK_PATH).setParameter("uri", contentItem.getUri())
					.setParameter("parts", parts ? "true" : "false").build();
			
			final HttpGet getRequest = new HttpGet(checkStatusUri);
			
			final CloseableHttpClient client = HttpClients.createDefault();
			final CloseableHttpResponse response = client.execute(getRequest);
			
			final String responseBody = EntityUtils.toString(response.getEntity());
			
			if (responseBody.isEmpty())
                return Collections.emptyList();
			
			ObjectMapper objectMapper = new ObjectMapper();
			List<StatusResponse> statusResponses = objectMapper.readValue(responseBody, new TypeReference<List<StatusResponse>>(){});
			
			return statusResponses;

		} catch (URISyntaxException e) {
			logger.error("Invalide URL", e);
			throw new MicoClientException(e.getMessage());
		} catch (ClientProtocolException e) {
			logger.error("Error in sending status check request to mico", e);
			throw new MicoClientException(e.getMessage());
		} catch (IOException e) {
			logger.error("Error in sending status check request to mico", e);
			throw new MicoClientException(e.getMessage());
		}
	}

}
