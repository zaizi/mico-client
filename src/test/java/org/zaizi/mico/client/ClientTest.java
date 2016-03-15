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
import org.zaizi.mico.client.status.impl.StatusResponse;

public class ClientTest {

	private static final String MICO_HOST = "mico-platform:8080";
	private static final String MICO_USER = "mico";
	private static final String MICO_PASSWORD = "mico";

	private static MicoClientFactory micoClientfactory;

//	@BeforeClass
//	public static void startClient() {
//		micoClientfactory = new MicoClientFactory(MICO_HOST, MICO_USER, MICO_PASSWORD);
//	}
//
//	@Test
//	public void testInject() throws MicoClientException, FileNotFoundException {
//		String filePath = "/Users/cperera/Desktop/testdata/download.jpeg";
//		InputStream is = new FileInputStream(filePath);
//
//		 Injector injector = micoClientfactory.createInjectorClient();
//		 ContentItem ci = injector.createContentItem();
//		 ContentPart cp = injector.addContentPart(ci, "image/jpeg", filePath,
//		 is);
//		 ci.addContentPart(cp);
//		 injector.submitContentItem(ci);
//
//		
//
//		StatusChecker statusChecker = micoClientfactory.createStatusChecker();
//		while (true) {
//			List<StatusResponse> statusResponses = statusChecker.checkItemStatus(ci, true);
//			if(!statusResponses.isEmpty()){
//				StatusResponse statusResponse = statusResponses.get(0);
//				System.out.println(statusResponse.getUri() + ", " + statusResponse.isFinished());
//				if (statusResponse.isFinished()) {
//					break;
//				}
//			}
//			
//		}
//	}

}
