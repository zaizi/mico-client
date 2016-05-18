package org.zaizi.mico.client;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

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

    @Test
	public void testInject() throws MicoClientException, FileNotFoundException {
    	String testResource = "text1.txt";
        InputStream is = getClass().getResourceAsStream("/"+testResource);

    	
        Injector injector = micoClientfactory.createInjectorClient();
        ContentItem ci = injector.createContentItem();
        ContentPart cp = injector.addContentPart(ci, "text/plain", testResource,
        is);
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

}
