package org.zaizi.mico.client.model.text;

import java.net.URI;

import com.github.anno4j.model.Body;

import org.openrdf.annotations.Iri;
import org.zaizi.mico.client.model.namespace.FAM;

@Iri(FAM.LINKED_ENTITY)
public interface LinkedEntityBody extends Body
{
    @Iri(FAM.CONFIDENCE)
    void setConfidence(Double confidence);

    @Iri(FAM.CONFIDENCE)
    Double getConfidence();

    @Iri(FAM.ENTITY_LABEL)
    String getEntityLabel();
    
    @Iri(FAM.ENTITY_LABEL)
    void setEntityLabel(String label); 
    
    @Iri(FAM.ENTITY_TYPE)
    void setEntityType(String type);

    @Iri(FAM.ENTITY_TYPE)
    String getEntityType();
    
    @Iri(FAM.ENTITY_MENTION) 
    void setEntityMention(String mention);
    
    @Iri(FAM.ENTITY_MENTION) 
    String getEntityMention();
    
    @Iri(FAM.ENTITY_REFERENCE) 
    void setEntityReference(String reference);
    
    @Iri(FAM.ENTITY_REFERENCE) 
    String getEntityReference();
    
}
