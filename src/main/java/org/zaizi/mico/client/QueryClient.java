package org.zaizi.mico.client;

import java.util.List;
import java.util.Map;

import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;

/**
 * Client for quering mico backend marmotta for metadata
 *
 */
public interface QueryClient {

    public static final String QUERY_SELECT_ENDPOINT = "/marmotta/sparql/select";
    public static final String QUERY_UPDATE_ENDPOINT = "/marmotta/sparql/update";
    
    /**
     * get linked entities for a given content item (content item is identified from the uri)
     * @param contentItemUri
     * @return
     * @throws MicoClientException
     */
    public List<LinkedEntity> getLinkedEntities(String contentItemUri) throws MicoClientException;
    
    /**
     * get linked entities for a given content item (content item is identified from the uri)
     * @param contentItemUri
     * @param namespaces add addtional namespaces (E.g. "dbo", "http://dbpedia.org/ontology#")
     * @param criterias addtional criterias for filtering query (Can use LDPathUtil to create such filters)
     * @return
     * @throws MicoClientException
     */
    public List<LinkedEntity> getLinkedEntities(String contentItemUri, Map<String,String> namespaces, String... criterias) throws MicoClientException;
    
    /**
     * get linked entities for a given content item (content item is identified from the uri)
     * @param contentItemUri
     * @param criterias addtional criterias for filtering query (Can use LDPathUtil to create such filters)
     * @return
     * @throws MicoClientException
     */
    public List<LinkedEntity> getLinkedEntities(String contentItemUri, String... criterias) throws MicoClientException;
    
    /**
     * get Face Detecting related metadata
     * @param contentItemUri
     * @return
     * @throws MicoClientException
     */
    public List<FaceFragment> getFaceFragments(String contentItemUri) throws MicoClientException;
    
    /**
     * get Face Detecting related metadata
     * @param contentItemUri
     * @param criterias addtional criterias for filtering query (Can use LDPathUtil to create such filters)
     * @return
     * @throws MicoClientException
     */
    public List<FaceFragment> getFaceFragments(String contentItemUri, String... criterias) throws MicoClientException;

    

}
