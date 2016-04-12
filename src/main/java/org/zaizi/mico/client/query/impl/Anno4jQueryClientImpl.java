package org.zaizi.mico.client.query.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zaizi.mico.client.QueryClient;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.namespace.FAM;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.model.text.LinkedEntityBody;
import org.zaizi.mico.client.query.util.LDPathUtil;

import com.github.anno4j.Anno4j;
import com.github.anno4j.model.Annotation;
import com.github.anno4j.model.Selector;
import com.github.anno4j.model.Target;
import com.github.anno4j.model.impl.selector.FragmentSelector;
import com.github.anno4j.model.impl.targets.SpecificResource;
import com.github.anno4j.querying.QueryService;

import eu.mico.platform.anno4j.model.namespaces.MICO;

public class Anno4jQueryClientImpl implements QueryClient
{

    private Anno4j anno4j;

    public Anno4jQueryClientImpl(Anno4j anno4j)
    {
        this.anno4j = anno4j;
    }

    @Override
    public List<LinkedEntity> getLinkedEntities(String contentItemUri) throws MicoClientException
    {
        List<LinkedEntity> linkedEntities = new ArrayList<LinkedEntity>();
        try
        {
            QueryService queryService = anno4j.createQueryService();
            queryService.addPrefix(MICO.PREFIX, MICO.NS).addPrefix(FAM.PREFIX, FAM.NS);
            queryService.addCriteria("^mico:hasContent/^mico:hasContentPart", contentItemUri);
            queryService.addCriteria(LDPathUtil.getTypeRestriction(null, "fam:LinkedEntity", null));
            List<Annotation> linkedEntityAnnotations = queryService.execute();
            for (Annotation annotation : linkedEntityAnnotations)
            {
                linkedEntities.add(getLinkedEntityFromAnnotation(annotation));
            }

        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occurred while retrieving linked entities from the content item",
                    ex);
        }

        return linkedEntities;
    }

    @Override
    public List<FaceFragment> getFaceFragments(String contentItemUri) throws MicoClientException
    {
        List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
        try
        {
            QueryService queryService = anno4j.createQueryService();
            queryService.addPrefix(MICO.PREFIX, MICO.NS).addCriteria("^mico:hasContent/^mico:hasContentPart",
                    contentItemUri);
            queryService.addCriteria(LDPathUtil.getTypeRestriction(null, "mico:FaceDetectionBody", null));
            List<Annotation> faceDetectAnnotations = queryService.execute();
            for (Annotation annotation : faceDetectAnnotations)
            {
                List<FaceFragment> fragments = getFaceFragmentsFromAnnotation(annotation);
                faceFragments.addAll(fragments);
            }

        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occurred while retrieving face fragments from the content item",
                    ex);
        }
        return faceFragments;
    }

    @Override
    public List<LinkedEntity> getLinkedEntities(String contentItemUri, String... criterias) throws MicoClientException
    {
        List<LinkedEntity> linkedEntities = new ArrayList<LinkedEntity>();
        try
        {
            QueryService queryService = anno4j.createQueryService();
            queryService.addPrefix(MICO.PREFIX, MICO.NS).addPrefix(FAM.PREFIX, FAM.NS);
            queryService.addCriteria("^mico:hasContent/^mico:hasContentPart", contentItemUri);
            queryService.addCriteria(LDPathUtil.getTypeRestriction(null, "fam:LinkedEntity", null));
            for (String criteria : criterias)
            {
                queryService.addCriteria(criteria);
            }
            // processBodyResourcePathValue(queryService, "fam:entity-type", "skos:Concept");

            List<Annotation> linkedEntityAnnotations = queryService.execute();
            for (Annotation annotation : linkedEntityAnnotations)
            {
                linkedEntities.add(getLinkedEntityFromAnnotation(annotation));
            }

        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occurred while retrieving linked entities from the content item",
                    ex);
        }

        return linkedEntities;
    }

    @Override
    public List<FaceFragment> getFaceFragments(String contentItemUri, String... criterias) throws MicoClientException
    {
        List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
        try
        {
            QueryService queryService = anno4j.createQueryService();
            queryService.addPrefix(MICO.PREFIX, MICO.NS).addCriteria("^mico:hasContent/^mico:hasContentPart",
                    contentItemUri);
            queryService.addCriteria(LDPathUtil.getTypeRestriction(null, "mico:FaceDetectionBody", null));
            for (String criteria : criterias)
            {
                queryService.addCriteria(criteria);
            }
            List<Annotation> faceDetectAnnotations = queryService.execute();
            for (Annotation annotation : faceDetectAnnotations)
            {
                List<FaceFragment> fragments = getFaceFragmentsFromAnnotation(annotation);
                faceFragments.addAll(fragments);
            }

        }
        catch (Exception ex)
        {
            throw new MicoClientException("Exception occurred while retrieving face fragments from the content item",
                    ex);
        }
        return faceFragments;
    }

    private LinkedEntity getLinkedEntityFromAnnotation(Annotation annotation)
    {
        LinkedEntityBody body = (LinkedEntityBody) annotation.getBody();
        LinkedEntity entity = new LinkedEntity();
        entity.setConfidence(body.getConfidence());
        entity.setEntityLabel(body.getEntityLabel());
        entity.setEntityMention(body.getEntityMention());

        String entityReference = body.getEntityReference();
        entity.setEntityReference(entityReference);
        String entityType = body.getEntityType();
        entity.setEntityType(entityType);
        return entity;
    }

    private List<FaceFragment> getFaceFragmentsFromAnnotation(Annotation annotation)
    {

        List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
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
        return faceFragments;
    }

}