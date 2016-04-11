package org.zaizi.mico.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.status.impl.StatusResponse;

public class QueryTest {

	private static final String MICO_HOST = "demo4.mico-project.eu:8080";
	private static final String MICO_USER = "djayakody";
	private static final String MICO_PASSWORD = "7onjhMcFG1kf";
	
	//http://demo4.mico-project.eu:8080/marmotta/sparql/select

	private static MicoClientFactory micoClientfactory;

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
                System.out.println(statusResponse.getUri() + ", " + statusResponse.isFinished());
                if (statusResponse.isFinished()) {
                    break;
                }
            }
            
        }
    }
	
	@Test
	public void testEntityQuery() throws MicoClientException
	{	
		QueryClient queryClient = micoClientfactory.createQueryServiceClient();
		StatusChecker statusChecker = micoClientfactory.createStatusChecker();
		String contentItemUri = "http://demo4.mico-project.eu:8080/marmotta/92b42302-7608-47e0-a5a5-8b457cf7e3da";
		while (true) {
			List<StatusResponse> statusResponses = statusChecker.checkItemStatus(contentItemUri, true);
			if (!statusResponses.isEmpty()) {
				StatusResponse statusResponse = statusResponses.get(0);
				if (statusResponse.isFinished()) {
					List<LinkedEntity> linkedEntities = queryClient.getLinkedEntities(contentItemUri);
					System.out.println("Number of linked entities retrieved: " + linkedEntities.size());
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
	
	@Test
	public void testFaceDetectionQuery() throws MicoClientException
	{
	    QueryClient queryClient = micoClientfactory.createQueryServiceClient();
        StatusChecker statusChecker = micoClientfactory.createStatusChecker();
        String contentItemUri = "http://demo4.mico-project.eu:8080/marmotta/540de56b-6d61-46f9-b541-81e743bc4dbb";
        while (true) {
            List<StatusResponse> statusResponses = statusChecker.checkItemStatus(contentItemUri, true);
            if (!statusResponses.isEmpty()) {
                StatusResponse statusResponse = statusResponses.get(0);
                if (statusResponse.isFinished()) {
                    List<FaceFragment> faceFragments = queryClient.getFaceFragments(contentItemUri);
                    System.out.println("Number of face fragments retrieved : " + faceFragments.size());                    
                    for (FaceFragment fragment : faceFragments) {
                        System.out.println("width : " + fragment.getWidth());
                        System.out.println("height: " + fragment.getHeight());
                        System.out.println("X : " + fragment.getX());
                        System.out.println("Y : " + fragment.getY());
                        System.out.println("------------------------------------");
                        
                    }
                    break;
                }
            }

        }
        
	}

}