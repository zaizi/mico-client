package org.zaizi.mico.client;

import java.util.List;
import java.util.Map;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;


public interface QueryClient {

    public static final String QUERY_SELECT_ENDPOINT = "/marmotta/sparql/select";
    public static final String QUERY_UPDATE_ENDPOINT = "/marmotta/sparql/update";
     
    public List<LinkedEntity> getLinkedEntities(String contentItemUri) throws MicoClientException;
    public List<LinkedEntity> getLinkedEntities(String contentItemUri, String... criterias) throws MicoClientException;
    
    public List<FaceFragment> getFaceFragments(String contentItemUri) throws MicoClientException;
    public List<FaceFragment> getFaceFragments(String contentItemUri, String... criterias) throws MicoClientException;

    

}
