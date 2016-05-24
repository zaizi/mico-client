package org.zaizi.mico.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;
import org.zaizi.mico.client.status.impl.StatusResponse;

public class ClientTest {

	private static final String MICO_HOST = "demo4.mico-project.eu:8080";
	private static final String MICO_USER = "djayakody";
	private static final String MICO_PASSWORD = "7onjhMcFG1kf";

	private static MicoClientFactory micoClientfactory;
    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);


    @BeforeClass
	public static void startClient() {
		micoClientfactory = new MicoClientFactory(MICO_HOST, MICO_USER, MICO_PASSWORD);
	}

//    @Test
	public void testInject() throws MicoClientException, IOException {
    	String testResource = "text1.txt";
        InputStream is = getClass().getResourceAsStream("/"+testResource);
        byte[] bytes = IOUtils.toByteArray(is);
    	
        Injector injector = micoClientfactory.createInjectorClient();
        ContentItem ci = injector.createContentItem("text/plain", testResource, new ByteArrayInputStream(bytes));
        ContentPart cp = injector.addContentPart(ci, "text/plain", testResource,
        		new ByteArrayInputStream(bytes));
        ci.addContentPart(cp);
        injector.submitContentItem(ci);

		
		StatusChecker statusChecker = micoClientfactory.createStatusChecker();
		while (true) {
			List<StatusResponse> statusResponses = statusChecker.checkItemStatus(ci.getUri(), true);
			if(!statusResponses.isEmpty()){
				StatusResponse statusResponse = statusResponses.get(0);
				logger.info(statusResponse.getUri() + ", " + statusResponse.isFinished());
				if (statusResponse.isFinished()) {
					break;
				}
			}
			
		}
	}
	
	@Test
	public void statuscheck() throws MicoClientException{
		String uri = "http://demo4.mico-project.eu:8080/marmotta/b7ea7424-9177-4591-8bb1-ebceffd12a8b";
		StatusChecker statusChecker = micoClientfactory.createStatusChecker();
		while (true) {
			List<StatusResponse> statusResponses = statusChecker.checkItemStatus(uri, true);
			if(!statusResponses.isEmpty()){
				StatusResponse statusResponse = statusResponses.get(0);
				logger.info(statusResponse.getUri() + ", " + statusResponse.isFinished());
				if (statusResponse.isFinished()) {
					break;
				}
			}
			
		}
	}

}
