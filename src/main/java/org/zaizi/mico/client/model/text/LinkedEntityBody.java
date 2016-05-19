/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
