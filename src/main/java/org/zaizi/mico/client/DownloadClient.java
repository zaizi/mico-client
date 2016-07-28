package org.zaizi.mico.client;

import org.zaizi.mico.client.exception.MicoClientException;

public interface DownloadClient {
	
	public static final String DOWNLOAD_PATH = "/broker/status/download";
	public static final String MARMOTTA_PATH = "/marmotta/";
	
	/**
	 * Download transcript of a audio/video 
	 * @param urn
	 * @return
	 * @throws MicoClientException 
	 */
	public String downloadTranscript(String urn, String itemUri) throws MicoClientException;
	

}
