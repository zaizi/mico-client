package org.zaizi.mico.client.query.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openrdf.model.URI;
import org.zaizi.mico.client.QueryClient;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.ContentItem;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.model.text.LinkedEntityBody;

import com.github.anno4j.Anno4j;
import com.github.anno4j.model.Annotation;
import com.github.anno4j.model.Selector;
import com.github.anno4j.model.Target;
import com.github.anno4j.model.impl.selector.FragmentSelector;
import com.github.anno4j.model.impl.targets.SpecificResource;
import com.github.anno4j.querying.QueryService;

import eu.mico.platform.anno4j.model.namespaces.MICO;

public class QueryClientImpl implements QueryClient
{

    private Anno4j anno4j;
    private QueryService queryService;

    public QueryClientImpl(Anno4j anno4j)
    {
        this.anno4j = anno4j;
        queryService = this.anno4j.createQueryService();

    }

    @Override
    public List<LinkedEntity> getLinkedEntities(ContentItem contentItem) throws MicoClientException
    {
        List<LinkedEntity> linkedEntities = new ArrayList<LinkedEntity>();
        try
        {
            queryService.addPrefix(MICO.PREFIX, MICO.NS).addCriteria("^mico:hasContent/^mico:hasContentPart",
                    contentItem.getID());
            processTypeRestriction(queryService, null, "fam:LinkedEntity", null);
            List<Annotation> linkedEntityAnnotations = queryService.execute();
            for (Annotation annotation : linkedEntityAnnotations)
            {

                LinkedEntityBody body = (LinkedEntityBody) annotation.getBody();
                LinkedEntity entity = new LinkedEntity();
                entity.setConfidence(body.getConfidence());
                entity.setEntityLabel(body.getEntityLabel());
                entity.setEntityMention(body.getEntityMention());

                URI entityReference = body.getEntityReference();
                entity.setEntityReference(entityReference.stringValue());
                URI entityType = body.getEntityType();
                entity.setEntityType(entityType.stringValue());
                linkedEntities.add(entity);
            }

        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occurred while retrieving flinked entities from the content item",
                    ex);
        }

        return linkedEntities;
    }

    @Override
    public List<FaceFragment> getFaceFragments(ContentItem contentItem) throws MicoClientException
    {
        List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
        try
        {
            queryService.addPrefix(MICO.PREFIX, MICO.NS).addCriteria("^mico:hasContent/^mico:hasContentPart",
                    contentItem.getID());
            processTypeRestriction(queryService, null, "mico:FaceDetectionBody", null);
            List<Annotation> faceDetectAnnotations = queryService.execute();
            for (Annotation annotation : faceDetectAnnotations)
            {
                Set<Target> targets = annotation.getTarget();
                for (Target target : targets)
                {
                    if (target instanceof SpecificResource)
                    {
                        Selector selector = ((SpecificResource) target).getSelector();
                        if (selector instanceof FragmentSelector)
                        {
                            int x = ((FragmentSelector) selector).getX();
                            int y = ((FragmentSelector) selector).getY();
                            int w = ((FragmentSelector) selector).getWidth();
                            int h = ((FragmentSelector) selector).getHeight();
                            FaceFragment faceFragment = new FaceFragment(x, y, w, h);
                            faceFragments.add(faceFragment);
                        }

                    }
                }
            }

        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occurred while retrieving face fragments from the content item",
                    ex);
        }
        return faceFragments;

    }

    /**
     * Checks if type restrictions were set and adds them to the QueryService object.
     * 
     * @param qs The anno4j QueryService object
     * @param selectorTypeRestriction
     * @param bodyTypeRestriction
     * @param targetTypeRestriction
     */
    private void processTypeRestriction(QueryService qs, String selectorTypeRestriction, String bodyTypeRestriction,
            String targetTypeRestriction)
    {
        if (selectorTypeRestriction != null)
        {

            qs.addCriteria("oa:hasTarget/oa:hasSelector" + selectorTypeRestriction);
        }

        if (bodyTypeRestriction != null)
        {
            qs.addCriteria("oa:hasBody" + bodyTypeRestriction);
        }

        if (targetTypeRestriction != null)
        {
            qs.addCriteria("oa:hasTarget" + targetTypeRestriction);
        }
    }

}
