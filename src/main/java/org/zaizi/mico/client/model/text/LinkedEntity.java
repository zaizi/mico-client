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
