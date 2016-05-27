package org.zaizi.mico.client.query.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openrdf.repository.object.RDFObject;
import org.zaizi.mico.client.QueryClient;
import org.zaizi.mico.client.exception.MicoClientException;
import org.zaizi.mico.client.model.face.FaceFragment;
import org.zaizi.mico.client.model.text.LinkedEntity;
import org.zaizi.mico.client.query.util.LDPathUtil;

import com.github.anno4j.Anno4j;
import com.github.anno4j.model.Annotation;
import com.github.anno4j.model.Selector;
import com.github.anno4j.model.Target;
import com.github.anno4j.model.impl.selector.FragmentSelector;
import com.github.anno4j.model.impl.targets.SpecificResource;
import com.github.anno4j.querying.QueryService;

import eu.mico.platform.anno4j.model.PartMMM;
import eu.mico.platform.anno4j.model.fam.LinkedEntityBody;
import eu.mico.platform.anno4j.model.namespaces.FAM;
import eu.mico.platform.anno4j.model.namespaces.MMM;
import eu.mico.platform.anno4j.model.namespaces.MMMTERMS;

public class Anno4jQueryClientImpl implements QueryClient {

	private Anno4j anno4j;

	public Anno4jQueryClientImpl(Anno4j anno4j) {
		this.anno4j = anno4j;
	}

	@Override
	public List<LinkedEntity> getLinkedEntities(String contentItemUri) throws MicoClientException {

		List<LinkedEntity> linkedEntities;
		try {
			QueryService queryService = anno4j.createQueryService();
			queryService.addPrefix(MMM.PREFIX, MMM.NS).addPrefix(FAM.PREFIX, FAM.NS);
			queryService.addCriteria("^mmm:hasPart", contentItemUri);
			queryService.addCriteria(LDPathUtil.getAnnotationTypeRestriction(null, "fam:LinkedEntity", null));
			List<PartMMM> linkedEntityAnnotations = queryService.execute(PartMMM.class);
			linkedEntities = this.addToLinkedEntities(linkedEntityAnnotations);

		} catch (Exception ex) {
			throw new MicoClientException("Exception occurred while retrieving linked entities from the content item",
					ex);
		}

		return linkedEntities;
	}

	@Override
	public List<FaceFragment> getFaceFragments(String contentItemUri) throws MicoClientException {
		List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
		try {
			QueryService queryService = anno4j.createQueryService();
			queryService.addPrefix(MMM.PREFIX, MMM.NS).addCriteria("^mmm:hasPart", contentItemUri);
			queryService.addCriteria(LDPathUtil.getAnnotationTypeRestriction(null, MMMTERMS.FACE_DETECTION_BODY, null));
			List<Annotation> faceDetectAnnotations = queryService.execute();
			for (Annotation annotation : faceDetectAnnotations) {
				List<FaceFragment> fragments = getFaceFragmentsFromAnnotation(annotation);
				faceFragments.addAll(fragments);
			}

		} catch (Exception ex) {
			throw new MicoClientException("Exception occurred while retrieving face fragments from the content item",
					ex);
		}
		return faceFragments;
	}

	@Override
	public List<LinkedEntity> getLinkedEntities(String contentItemUri, String... criterias) throws MicoClientException {
		List<LinkedEntity> linkedEntities;
		try {
			QueryService queryService = anno4j.createQueryService();
			queryService.addPrefix(MMM.PREFIX, MMM.NS).addPrefix(FAM.PREFIX, FAM.NS);
			queryService.addCriteria("^mmm:hasPart", contentItemUri);
			queryService.addCriteria(LDPathUtil.getAnnotationTypeRestriction(null, "fam:LinkedEntity", null));
			for (String criteria : criterias) {
				queryService.addCriteria(criteria);
			}

			List<PartMMM> linkedEntityAnnotations = queryService.execute(PartMMM.class);
			linkedEntities = this.addToLinkedEntities(linkedEntityAnnotations);

		} catch (Exception ex) {
			throw new MicoClientException("Exception occurred while retrieving linked entities from the content item",
					ex);
		}

		return linkedEntities;
	}

	@Override
	public List<LinkedEntity> getLinkedEntities(String contentItemUri, Map<String, String> namespaces,
			String... criterias) throws MicoClientException {
		List<LinkedEntity> linkedEntities;
		try {
			QueryService queryService = anno4j.createQueryService();
			queryService.addPrefix(MMM.PREFIX, MMM.NS).addPrefix(FAM.PREFIX, FAM.NS);
			// adding new namespaces
			for (String prefix : namespaces.keySet()) {
				queryService.addPrefix(prefix, namespaces.get(prefix));
			}

			queryService.addCriteria("^mmm:hasPart", contentItemUri);
			queryService.addCriteria(LDPathUtil.getAnnotationTypeRestriction(null, "fam:LinkedEntity", null));
			for (String criteria : criterias) {
				queryService.addCriteria(criteria);
			}

			List<PartMMM> linkedEntityAnnotations = queryService.execute(PartMMM.class);
			linkedEntities = this.addToLinkedEntities(linkedEntityAnnotations);

		} catch (Exception ex) {
			throw new MicoClientException("Exception occurred while retrieving linked entities from the content item",
					ex);
		}

		return linkedEntities;
	}

	@Override
	public List<FaceFragment> getFaceFragments(String contentItemUri, String... criterias) throws MicoClientException {
		List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
		try {
			QueryService queryService = anno4j.createQueryService();
			queryService.addPrefix(MMM.PREFIX, MMM.NS).addCriteria("^mmm:hasPart", contentItemUri);
			queryService.addCriteria(LDPathUtil.getAnnotationTypeRestriction(null, MMMTERMS.FACE_DETECTION_BODY, null));
			for (String criteria : criterias) {
				queryService.addCriteria(criteria);
			}
			List<Annotation> faceDetectAnnotations = queryService.execute();
			for (Annotation annotation : faceDetectAnnotations) {
				List<FaceFragment> fragments = getFaceFragmentsFromAnnotation(annotation);
				faceFragments.addAll(fragments);
			}

		} catch (Exception ex) {
			throw new MicoClientException("Exception occurred while retrieving face fragments from the content item",
					ex);
		}
		return faceFragments;
	}

	private LinkedEntity getLinkedEntityFromAnnotation(PartMMM annotation) {
		LinkedEntity entity = new LinkedEntity();

		if (annotation.getBody() instanceof LinkedEntityBody) {
			LinkedEntityBody body = (LinkedEntityBody) annotation.getBody();
			entity.setConfidence(body.getConfidence());
			Set<RDFObject> typeSet = body.getTypes();
			if(typeSet != null){
				List<RDFObject> typesList = new ArrayList<>(body.getTypes());
				entity.setEntityType(typesList.get(0).toString());
			}

			entity.setEntityLabel(body.getLabel().toString());
			entity.setEntityReference(body.getEntity().getResource().toString());
		}

		return entity;
	}

	private List<LinkedEntity> addToLinkedEntities(List<PartMMM> linkedEntityAnnotations) {

		List<LinkedEntity> linkedEntities = new ArrayList<LinkedEntity>();
		for (PartMMM partMMM : linkedEntityAnnotations) {
			LinkedEntity entity = getLinkedEntityFromAnnotation(partMMM);
			if(entity != null){
				linkedEntities.add(entity);
			}
		}
		return linkedEntities;
	}

	private List<FaceFragment> getFaceFragmentsFromAnnotation(Annotation annotation) {

		List<FaceFragment> faceFragments = new ArrayList<FaceFragment>();
		Set<Target> targets = annotation.getTarget();
		for (Target target : targets) {
			if (target instanceof SpecificResource) {
				Selector selector = ((SpecificResource) target).getSelector();
				if (selector instanceof FragmentSelector) {
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