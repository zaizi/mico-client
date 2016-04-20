package org.zaizi.mico.client.query.util;

import java.util.Map;

/**
 * LDPath util methods
 * 
 * @author djayakody
 * 
 */
public class LDPathUtil
{

    /**
     * Checks if type restrictions were set and adds them to the QueryService object.
     * 
     * @param selectorTypeRestriction
     * @param bodyTypeRestriction
     * @param targetTypeRestriction
     * @return criteria string
     */
    public static String getTypeRestriction(String selectorTypeRestriction, String bodyTypeRestriction,
            String targetTypeRestriction)
    {
        String criteria = "";
        if (selectorTypeRestriction != null)
        {

            criteria = "oa:hasTarget/oa:hasSelector[is-a " + selectorTypeRestriction + "]";
        }

        if (bodyTypeRestriction != null)
        {
            criteria = "oa:hasBody[is-a " + bodyTypeRestriction + "]";
        }

        if (targetTypeRestriction != null)
        {
            criteria = "oa:hasTarget[is-a " + targetTypeRestriction + "]";
        }
        return criteria;
    }

    /**
     * PATH & PATH
     * eg: foaf:interest & foaf:topic_interest
     * @param paths
     * @return criteria string
     */
    public static String getIntersections(String... paths)
    {
        String pathString = "";
        if (paths != null)
        {
            pathString += paths[0];
            if (paths.length > 1)
            {
                for (int x = 1; x < paths.length; x++)
                {
                    pathString += " & " + paths[x];
                }
            }
        }
        return pathString;
    }

    /**
     * PATH | PATH
     * eg: foaf:knows/foaf:name | foaf:knows/rdfs:label
     * @param paths
     * @return criteria string
     */
    public static String getUnions(String... paths)
    {
        String pathString = "";
        if (paths != null)
        {
            pathString += paths[0];
            if (paths.length > 1)
            {
                for (int x = 1; x < paths.length; x++)
                {
                    pathString += " | " + paths[x];
                }
            }
        }
        return pathString;
    }

    /**
     * eg: foaf:interest[rdf:type is ex:Food]
     * @param path eg: oa:hasBody
     * @param testCondition eg : fam:entity-type
     * @param testValue eg : skos:Concept
     * @param notFilter NOT filter (!) to negate the path value test
     * @return criteria string
     */
    public static String getResourcePathValueTest(String path, String testCondition, String testValue, boolean notFilter)
    {
        String filter = "";
        if (notFilter)
        {
            filter = "!";
        }

        String criteria = path + "[" + filter + "(" + testCondition + " is " + testValue + ")]";
        return criteria;
    }

    /**
     * eg: foaf:interest[rdf:type is ex:Food & rdf:type is ex:Drink]
     * @param path eg: oa:hasBody
     * @param testsMap
     * @param conjunction AND
     * @param disjunction OR
     * @param notFilter NOT filter (!) to negate the path value test
     * @return criteria string
     */
    public static String getResourcePathValueTests(String path, Map<String, String> testsMap, boolean conjunction,
            boolean disjunction, boolean notFilter)
    {
        String filter = "";
        if (notFilter)
        {
            filter = "!";
        }
        String criteriaBegin = path + "[";
        String criteriaEnd = "]";
        String criteriaChainString = "";
        Object[] paths = testsMap.keySet().toArray();
        for (int index = 0; index < paths.length; index++)
        {
            if (index > 0)
            {
                if (conjunction)
                {
                    criteriaChainString += " & ";
                }
                else if (disjunction)
                {
                    criteriaChainString += " | ";
                }
            }
            String key = (String) paths[index];
            String val = testsMap.get(key);
            criteriaChainString += filter + "(" + key + " is " + val + ")";
        }
        String criteria = criteriaBegin + criteriaChainString + criteriaEnd;
        return criteria;
    }

}
