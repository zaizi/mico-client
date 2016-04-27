package org.zaizi.mico.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.query.util.LDPathUtil;
import org.zaizi.mico.client.status.impl.StatusResponse;

public class QueryTest
{

    private static final String MICO_HOST = "demo4.mico-project.eu:8080";

    private static final String MICO_USER = "djayakody";
    private static final String MICO_PASSWORD = "7onjhMcFG1kf";

    // http://demo4.mico-project.eu:8080/marmotta/sparql/select

    private static MicoClientFactory micoClientfactory;
    private static final Logger logger = LoggerFactory.getLogger(QueryTest.class);


    @BeforeClass
    public static void startClient()
    {
        micoClientfactory = new MicoClientFactory(MICO_HOST, MICO_USER, MICO_PASSWORD);
    }

 
    @Test
    public void testEntityQuery() throws MicoClientException
    {
        QueryClient queryClient = micoClientfactory.createQueryServiceClient();
        StatusChecker statusChecker = micoClientfactory.createStatusChecker();
        String contentItemUri = "http://demo4.mico-project.eu:8080/marmotta/0c47da8a-50ba-498a-959d-e338f4653aad";
        //String contentItemUri = "http://demo4.mico-project.eu:8080/marmotta/50983b66-e763-4348-94aa-d9d845fd2200";
        List<LinkedEntity> entities = new ArrayList<LinkedEntity>();
        while (true)
        {
            List<StatusResponse> statusResponses = statusChecker.checkItemStatus(contentItemUri, true);
            if (!statusResponses.isEmpty())
            {
                StatusResponse statusResponse = statusResponses.get(0);
                if (statusResponse.isFinished())
               { 
                    List<LinkedEntity> linkedEntities = queryClient.getLinkedEntities(contentItemUri);
                    logger.info("Number of linked entities retrieved: " + linkedEntities.size());
                    for (LinkedEntity linkedEntity : linkedEntities)
                    {
                        logger.info("Entity Label: " + linkedEntity.getEntityLabel());
                        logger.info("Entity Mention: " + linkedEntity.getEntityMention());
                        logger.info("Entity Reference: " + linkedEntity.getEntityReference());
                        logger.info("Entity Type: " + linkedEntity.getEntityType());
                        logger.info("Confidence: " + linkedEntity.getConfidence());
                        logger.info("------------------------------------------");
                        entities.add(linkedEntity);
                    }
                    break;
                }
            }

        }
        assertFalse(entities.isEmpty());
    }


    //@Test
    public void testFilteredEntityQuery() throws MicoClientException
    {
        QueryClient queryClient = micoClientfactory.createQueryServiceClient();
        StatusChecker statusChecker = micoClientfactory.createStatusChecker();
        String contentItemUri = "http://demo4.mico-project.eu:8080/marmotta/0c47da8a-50ba-498a-959d-e338f4653aad";
        //String contentItemUri= "http://demo4.mico-project.eu:8080/marmotta/50983b66-e763-4348-94aa-d9d845fd2200";
        List<LinkedEntity> entities = new ArrayList<LinkedEntity>();
        while (true)
        {
            List<StatusResponse> statusResponses = statusChecker.checkItemStatus(contentItemUri, true);
            if (!statusResponses.isEmpty())
            {
                StatusResponse statusResponse = statusResponses.get(0);
                if (statusResponse.isFinished())
                {
                    Map<String,String> pathValues = new HashMap<String,String>();
                    pathValues.put("fam:entity-type", "skos:Concept");
                                   
                    List<LinkedEntity> linkedEntities = queryClient.getLinkedEntities(contentItemUri,
                            LDPathUtil.getResourcePathValueTests("oa:hasBody", pathValues, true, false, false));
                    logger.info("Number of linked entities with skos:Concept type retrieved: " + linkedEntities.size());
                    for (LinkedEntity linkedEntity : linkedEntities)
                    {
                        logger.info("Entity Label: " + linkedEntity.getEntityLabel());
                        logger.info("Entity Mention: " + linkedEntity.getEntityMention());
                        logger.info("Entity Reference: " + linkedEntity.getEntityReference());
                        logger.info("Entity Type: " + linkedEntity.getEntityType());
                        logger.info("Confidence: " + linkedEntity.getConfidence());
                        logger.info("------------------------------------------");
                        entities.add(linkedEntity);
                    }
                    break;
                }
            }

        }
        assertFalse(entities.isEmpty());
    }
    
	//@Test
	public void testFaceDetectionQuery() throws MicoClientException
	{
	    QueryClient queryClient = micoClientfactory.createQueryServiceClient();
        StatusChecker statusChecker = micoClientfactory.createStatusChecker();
        String contentItemUri = "http://demo4.mico-project.eu:8080/marmotta/540de56b-6d61-46f9-b541-81e743bc4dbb";
        List<FaceFragment> faceFragmentsArray = new ArrayList<FaceFragment>();
        while (true)
        {
            List<StatusResponse> statusResponses = statusChecker.checkItemStatus(contentItemUri, true);
            if (!statusResponses.isEmpty())
            {
                StatusResponse statusResponse = statusResponses.get(0);
                if (statusResponse.isFinished())
                {
                    List<FaceFragment> faceFragments = queryClient.getFaceFragments(contentItemUri);
                    logger.info("Number of face fragments retrieved : " + faceFragments.size());
                    for (FaceFragment fragment : faceFragments)
                    {
                        logger.info("width : " + fragment.getWidth());
                        logger.info("height: " + fragment.getHeight());
                        logger.info("X : " + fragment.getX());
                        logger.info("Y : " + fragment.getY());
                        logger.info("------------------------------------");
                        faceFragmentsArray.add(fragment);
                    }
                    break;
                }
            }

        }
        assertFalse(faceFragmentsArray.isEmpty());
    }

}
