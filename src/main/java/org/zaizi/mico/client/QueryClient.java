package org.zaizi.mico.client;

import java.util.List;
import java.util.Map;

import org.apache.marmotta.ldpath.parser.ParseException;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.ContentPart;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;

import com.github.anno4j.Anno4j;
import com.github.anno4j.model.Annotation;

public interface QueryClient {

    public static final String QUERY_SELECT_ENDPONT = "/marmotta/sparql/select";
     
    public List<LinkedEntity> getLinkedEntities(ContentItem contentItem) throws MicoClientException;
    
    public List<FaceFragment> getFaceFragments(ContentItem contentItem) throws MicoClientException;

   

}
