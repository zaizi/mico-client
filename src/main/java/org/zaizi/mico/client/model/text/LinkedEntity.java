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

public class LinkedEntity
{

    private String entityMention;
    private String entityLabel;
    private String entityType;
    private String entityReference;
    private double confidence;

    public String getEntityMention()
    {
        return entityMention;
    }

    public void setEntityMention(String entityMention)
    {
        this.entityMention = entityMention;
    }

    public String getEntityLabel()
    {
        return entityLabel;
    }

    public void setEntityLabel(String entityLabel)
    {
        this.entityLabel = entityLabel;
    }

    public String getEntityType()
    {
        return entityType;
    }

    public void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }

    public String getEntityReference()
    {
        return entityReference;
    }

    public void setEntityReference(String entityReference)
    {
        this.entityReference = entityReference;
    }

    public double getConfidence()
    {
        return confidence;
    }

    public void setConfidence(double confidence)
    {
        this.confidence = confidence;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        if (obj instanceof LinkedEntity)
        {
            String entityRefOtherObj = ((LinkedEntity) obj).getEntityReference();
            if (entityRefOtherObj.equalsIgnoreCase(this.entityReference))
            {
                isEqual = true;
            }
        }

        return isEqual;
    }

    @Override
    public int hashCode()
    {
        return entityReference.hashCode();
    }

}
