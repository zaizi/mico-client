package org.zaizi.mico.client;

import java.util.List;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;


public interface QueryClient {

    public static final String QUERY_SELECT_ENDPONT = "/marmotta/sparql/select";
     
    public List<LinkedEntity> getLinkedEntities(ContentItem contentItem) throws MicoClientException;
    
    public List<FaceFragment> getFaceFragments(ContentItem contentItem) throws MicoClientException;

   

}
