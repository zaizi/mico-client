package org.zaizi.mico.client;

import java.io.InputStream;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.status.impl.StatusResponse;

public class QueryTest {

	private static final String MICO_HOST = "localhost:8080";
	private static final String MICO_USER = "mico";
	private static final String MICO_PASSWORD = "mico";

	private static MicoClientFactory micoClientfactory;

	@BeforeClass
	public static void startClient() {
		micoClientfactory = new MicoClientFactory(MICO_HOST, MICO_USER, MICO_PASSWORD);
	}
	
	@Test
	public void testMico() throws MicoClientException{
		String testResource = "text1.txt";
		InputStream is = getClass().getResourceAsStream("/"+testResource);

		Injector injector = micoClientfactory.createInjectorClient();
		ContentItem ci = injector.createContentItem();
		ContentPart cp = injector.addContentPart(ci, "text/plain", testResource, is);
		ci.addContentPart(cp);
		injector.submitContentItem(ci);
		
		QueryClient queryClient = micoClientfactory.createQueryServiceClient();

		StatusChecker statusChecker = micoClientfactory.createStatusChecker();
		while (true) {
			List<StatusResponse> statusResponses = statusChecker.checkItemStatus(ci, true);
			if (!statusResponses.isEmpty()) {
				StatusResponse statusResponse = statusResponses.get(0);
				if (statusResponse.isFinished()) {
					List<LinkedEntity> linkedEntities = queryClient.getLinkedEntities(ci);
					for (LinkedEntity linkedEntity : linkedEntities) {
						System.out.println("Entity Label: "+linkedEntity.getEntityLabel());
						System.out.println("Entity Mention: "+linkedEntity.getEntityMention());
						System.out.println("Entity Reference: "+linkedEntity.getEntityReference());
						System.out.println("Entity Type: "+linkedEntity.getEntityType());
						System.out.println("Confidence: "+linkedEntity.getConfidence());
						System.out.println("------------------------------------------");
					}
					break;
				}
			}

		}
	}

}
