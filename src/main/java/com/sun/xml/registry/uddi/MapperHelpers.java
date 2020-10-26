/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Payara Services Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.xml.registry.uddi;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import java.util.*;

import com.sun.xml.registry.uddi.infomodel.*;

import com.sun.xml.registry.common.*;
import com.sun.xml.registry.common.util.*;
import com.sun.xml.registry.uddi.bindings_v2_2.*;


import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.logging.Logger;


/**
 *
 * @version 1.4, 01/11/00
 *
 *
 */

public class MapperHelpers extends JAXRConstants {
    
    Logger logger = (Logger)
	AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		return Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".uddi");
	    }
	});
        
    private static XMLUtil xmlUtil;
    private static MarshallerUtil marshallerUtil;
    
    MapperHelpers(){
        
    }
    
    Collection getCallerIsTargetByState(Collection associations,
    Boolean callerConfirmed,
    Boolean otherConfirmed) throws JAXRException {
        
        //caller is target key
        Collection matchingTargetAssociations = new ArrayList();
        if ((callerConfirmed != null) && (otherConfirmed != null)) {
            boolean callerIsConfirmed = callerConfirmed.booleanValue();
            boolean otherIsConfirmed = otherConfirmed.booleanValue();
            
            Iterator assocIterator = associations.iterator();
            while (assocIterator.hasNext()) {
                Association association = (Association)assocIterator.next();
                //other
                boolean sourceObjectConfirmed = association.isConfirmedBySourceOwner();
                //caller
                boolean targetObjectConfirmed = association.isConfirmedByTargetOwner();
                if (targetObjectConfirmed == callerIsConfirmed) {
                    //we've got a hit for caller
                    if (sourceObjectConfirmed == otherIsConfirmed){
                        //got a hit add to
                        matchingTargetAssociations.add(association);
                    }
                }
            }
        } //caller and other have boolean values
        
        if ((callerConfirmed != null) && (otherConfirmed == null)) {
            boolean callerIsConfirmed = callerConfirmed.booleanValue();
            
            Iterator assocIterator = associations.iterator();
            while (assocIterator.hasNext()) {
                Association association = (Association)assocIterator.next();
                //caller
                boolean targetObjectConfirmed = association.isConfirmedByTargetOwner();
                //other - don't care
                if (targetObjectConfirmed == callerIsConfirmed) {
                    //we've got a hit for caller - that's all we care about
                    matchingTargetAssociations.add(association);
                }
            }
        }
        
        if ((callerConfirmed == null) && (otherConfirmed != null)) {
            boolean otherIsConfirmed = otherConfirmed.booleanValue();
            Iterator assocIterator = associations.iterator();
            while (assocIterator.hasNext()) {
                Association association = (Association)assocIterator.next();
                //caller - don't care
                //other
                boolean sourceObjectConfirmed = association.isConfirmedBySourceOwner();
                if (sourceObjectConfirmed == otherIsConfirmed) {
                    //we've got a hit for other - that's all we care about
                    matchingTargetAssociations.add(association);
                }
            }
        }
        
        if ((callerConfirmed == null) && (otherConfirmed == null)) {
            //don't care return all
            matchingTargetAssociations.addAll(associations);
        }
        return matchingTargetAssociations;
    }
    
    Collection getCallerIsSourceByState(Collection associations,
    Boolean callerConfirmed,
    Boolean otherConfirmed) throws JAXRException {
        
        //caller is source key
        Collection matchingSourceAssociations = new ArrayList();
        if ((callerConfirmed != null) && (otherConfirmed != null)) {
            boolean callerIsConfirmed = callerConfirmed.booleanValue();
            boolean otherIsConfirmed = otherConfirmed.booleanValue();
            
            Iterator assocIterator = associations.iterator();
            while (assocIterator.hasNext()) {
                Association association = (Association)assocIterator.next();
                //caller
                boolean sourceObjectConfirmed = association.isConfirmedBySourceOwner();
                //other
                boolean targetObjectConfirmed = association.isConfirmedByTargetOwner();
                if (sourceObjectConfirmed == callerIsConfirmed) {
                    //we've got a hit for caller
                    if (targetObjectConfirmed == otherIsConfirmed){
                        //got a hit add to
                        matchingSourceAssociations.add(association);
                    }
                }
            }
        } //caller and other have boolean values
        
        if ((callerConfirmed != null) && (otherConfirmed == null)) {
            
            boolean callerIsConfirmed = callerConfirmed.booleanValue();
            
            Iterator assocIterator = associations.iterator();
            while (assocIterator.hasNext()) {
                Association association = (Association)assocIterator.next();
                //caller
                boolean sourceObjectConfirmed = association.isConfirmedBySourceOwner();
                //other - don't care
                if (sourceObjectConfirmed == callerIsConfirmed) {
                    //we've got a hit for caller - that's all we care about
                    matchingSourceAssociations.add(association);
                }
            }
        }
        
        if ((callerConfirmed == null) && (otherConfirmed != null)) {
            
            boolean otherIsConfirmed = otherConfirmed.booleanValue();
            Iterator assocIterator = associations.iterator();
            while (assocIterator.hasNext()) {
                Association association = (Association)assocIterator.next();
                //caller - don't care
                //other
                boolean targetObjectConfirmed = association.isConfirmedByTargetOwner();
                if (targetObjectConfirmed == otherIsConfirmed) {
                    //we've got a hit for other - that's all we care about
                    matchingSourceAssociations.add(association);
                }
            }
        }
        
        if ((callerConfirmed == null) && (otherConfirmed == null)) {
            //don't care return all
            matchingSourceAssociations.addAll(associations);
        }
        
        return matchingSourceAssociations;
    }
    
    Collection getCallerTargetAssociations(Collection associations, Collection callerToKeys)
    throws JAXRException {
        
        Collection callerTargetAssociations = new ArrayList();
        Iterator associationIter = associations.iterator();
        while (associationIter.hasNext()) {
            Association association = (Association)associationIter.next();
            RegistryObject targetObject = association.getTargetObject();
            if (targetObject != null){
                String targetId = targetObject.getKey().getId();
                
                Iterator keysIterator = callerToKeys.iterator();
                while (keysIterator.hasNext()) {
                    String toKey = (String)keysIterator.next();
                    if (toKey.equalsIgnoreCase(targetId))
                        callerTargetAssociations.add(association);
                }
            }
        }
        return callerTargetAssociations;
    }
    
    Collection getCallerSourceAssociations(Collection associations, 
    Collection callerFromKeys, Collection fromKeysOwned, Collection toKeysOwned)
    throws JAXRException {
        
        Collection callerSourceAssociations = new ArrayList();
        Iterator associationIter = associations.iterator();
        while (associationIter.hasNext()) {
            Association association = (Association)associationIter.next();
            //getSourceObject
            RegistryObject sourceObject = association.getSourceObject();
            if (sourceObject != null) {
                String sourceId = sourceObject.getKey().getId();
                
                Iterator keysIterator = callerFromKeys.iterator();
                while (keysIterator.hasNext()) {
                    String fromKey = (String)keysIterator.next();
                    if (fromKey.equalsIgnoreCase(sourceId))
                        callerSourceAssociations.add(association);
                }
            }
        }
        return callerSourceAssociations;
    }
    
    BulkResponse filterAssociationsByConfirmationState(BulkResponse br,
    Boolean callerIsConfirmed,
    Boolean otherIsConfirmed,
    Collection fromKeysOwned,
    Collection toKeysOwned)
    throws JAXRException {
        //set up return response
        BulkResponseImpl filteredResponse = new BulkResponseImpl();
        Collection filteredAssociations = new ArrayList();
        
        Collection callerIsSource;
        Collection callerIsTarget;
        //get associations
        Collection associations = br.getCollection();
        //get Iterators
        Iterator associationIter = associations.iterator();
        Collection callerFromKeys = fromKeysOwned;
        Collection callerToKeys = toKeysOwned;
        
        
        
        
        callerIsSource = getCallerSourceAssociations(associations, callerFromKeys);
        callerIsTarget = getCallerTargetAssociations(associations, callerToKeys);
        
        Collection resultSourceAssociations =
        getCallerIsSourceByState(callerIsSource, callerIsConfirmed, otherIsConfirmed);
        Collection resultTargetAssociations =
        getCallerIsTargetByState(callerIsTarget, callerIsConfirmed, otherIsConfirmed);
        
        if ((resultSourceAssociations != null) && (resultSourceAssociations.size() != 0))
            filteredAssociations.addAll(resultSourceAssociations);
        if ((resultTargetAssociations != null) && (resultTargetAssociations.size() != 0))
            filteredAssociations.addAll(resultTargetAssociations);
        
        filteredResponse.setPartialResponse(br.isPartialResponse());
        filteredResponse.setStatus(br.getStatus());
        filteredResponse.addCollection(filteredAssociations);
        return filteredResponse;
    }
    BulkResponse filterByAssociationTypes(BulkResponse br, Collection associationTypes)
    throws JAXRException {
        
        if (associationTypes == null)
            return null;
        //set up return response
        BulkResponseImpl filteredResponse = new BulkResponseImpl();
        Collection filteredAssociations = new ArrayList();
        //get associations
        Collection associations = br.getCollection();
        //get Iterators
        Iterator associationIter = associations.iterator();
        Iterator typeIterator = associationTypes.iterator();
        while (typeIterator.hasNext()){
            Concept type = (Concept)typeIterator.next();
            while (associationIter.hasNext()){
                Association association = (Association)associationIter.next();
                //getAssociationtype
                Concept associationType = association.getAssociationType();
                if (associationType != null) {
                    if ( associationType.getValue().equalsIgnoreCase(type.getValue()) ){
                        //put it in the bucket
                        filteredAssociations.add(association);
                    }
                }
            }
        }
        filteredResponse.setPartialResponse(br.isPartialResponse());
        filteredResponse.setStatus(br.getStatus());
        filteredResponse.addCollection(filteredAssociations);
        
        
        
        return filteredResponse;
    }
    
    BulkResponse filterAssociations(BulkResponse br, int criteria,
    String sourceKeyId, String targetKeyId)
    throws JAXRException {
        
        //assume not exceptions otherwise it's gone back already
        //set up return response
        BulkResponseImpl filteredResponse = new BulkResponseImpl();
        Collection filteredAssociations = new ArrayList();
        //get associations
        Collection associations = br.getCollection();
        Iterator associationIter = associations.iterator();
        while (associationIter.hasNext()) {
            Association association = (Association)associationIter.next();
            RegistryObject sourceObject = association.getSourceObject();
            RegistryObject targetObject = association.getTargetObject();
            String sourceId = sourceObject.getKey().getId();
            String targetId = targetObject.getKey().getId();
            
            switch(criteria){
                
                case SOURCE_KEY_MUST_MATCH_SOURCE:
                    if (sourceId.equals(sourceKeyId))
                        filteredAssociations.add(association);
                    break;
                    
                case TARGET_KEY_MUST_MATCH_TARGET:
                    if (targetId.equals(targetKeyId))
                        filteredAssociations.add(association);
                    break;
                    
                case SOURCE_KEY_MUST_MATCH_SOURCE_AND_TARGET_KEY_MUST_MATCH_TARGET:
                    if ( sourceId.equals(sourceKeyId) && targetId.equals(targetKeyId) )
                        filteredAssociations.add(association);
                    break;
                    
                default:
                    //do nothing
            }
        }
        
        filteredResponse.setPartialResponse(br.isPartialResponse());
        filteredResponse.setStatus(br.getStatus());
        filteredResponse.addCollection(filteredAssociations);
        return filteredResponse;
        
    }
    
    BulkResponse cullDuplicates(BulkResponse br) throws JAXRException{
        
        Collection list = (Collection)br.getCollection();
        if (list.isEmpty()) return br;
        
        Collection result = cullDuplicates(list);
        ((BulkResponseImpl)br).setCollection(result);
        return br;
    }

    Collection cullDuplicates(Collection list) throws JAXRException{

        HashMap map = new HashMap(list.size() * 2 - 1);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            RegistryObject registryObject = (RegistryObject)iterator.next();
            String key = registryObject.getKey().getId().toUpperCase();
            if (!map.containsKey(key)) { //if false not a dup
                map.put(key, registryObject);
            } else { //found dup which do I keep
                logger.finest("Found a dup of " + key);
                RegistryObject dupRegistryObject = (RegistryObject)map.get(key);
                if ((registryObject instanceof ClassificationScheme) &&
                (dupRegistryObject instanceof ClassificationScheme)){
                    logger.finest("Both RO's are classificationSchemes");
                    if (((ClassificationScheme)registryObject).isExternal()) {
                        logger.finest("registryObject is external - don't keep it");
                        continue;
                    } else { //it's internal we want to keep it
                        logger.finest("RegistryObject is internal - remove dup - keep internal ro");
                        map.remove(key);
                        map.put(key, registryObject);
                    }
                } //tbd consider general case not just classificationScheme
            }
        }
        return map.values();
    }


    BulkResponse extractRegistryObjectByClass(BulkResponse br, String type)
    throws JAXRException {
        Collection robjs = new ArrayList();
        if (br.getExceptions() == null) {
            Collection ros = br.getCollection();
            Iterator riter = ros.iterator();
            while (riter.hasNext()) {
                RegistryObject ro = (RegistryObject)riter.next();
                if (type.equals("Concept")) {
                    if (ro instanceof Concept){
                        robjs.add(ro);
                    }
                } else if (type.equals("ClassificationScheme")) {
                    if (ro instanceof ClassificationScheme) {
                        robjs.add(ro);
                    }
                }
            }
            ((BulkResponseImpl)br).setCollection(robjs);
        }
        return br;
    }
    
    
    
    Collection getAllServiceBindingsFromOrganizations(Collection orgs)
    throws JAXRException{
        
        Collection bindings = new ArrayList();
        Collection services =
        getAllServicesFromOrganizations(orgs);
        Iterator iter = services.iterator();
        while (iter.hasNext()){
            ServiceImpl service = (ServiceImpl) iter.next();
            Collection sbindings = service.getServiceBindings();
            bindings.addAll(sbindings);
        }
        return bindings;
    }
    
    Collection getAllServicesFromOrganizations(Collection orgs)
    throws JAXRException {
        
        Collection services = new ArrayList();
        Iterator iter  = orgs.iterator();
        while (iter.hasNext()){
            OrganizationImpl org = (OrganizationImpl) iter.next();
            Collection orgsServices = org.getServices();
            services.addAll(orgsServices);
        }
        return services;
    }
    
    URLType parseUrlForUrlType(String accessUri) {
        //then we want this concepts value
        URLType urlType = null;
        if ((accessUri.indexOf("https") != -1) || (accessUri.indexOf("HTTPS") != -1)){
            urlType = URLType.HTTPS;
        } else if ((accessUri.indexOf("http") != -1) || (accessUri.indexOf("HTTP") != -1)) {
            urlType = URLType.HTTP;
        } else if ((accessUri.indexOf("ftp") != -1)  || (accessUri.indexOf("FTP") != -1)) {
            urlType = URLType.FTP;
        } else if ((accessUri.indexOf("phone") != -1) || (accessUri.indexOf("PHONE") != -1)){
            urlType = URLType.PHONE;
        } else if ((accessUri.indexOf("mailto") != -1) || (accessUri.indexOf("MAILTO") != -1)) {
            urlType = URLType.MAILTO;
        } else {
            urlType = URLType.OTHER;
        }
        return urlType;
    }
   
    Collection getCallerSourceAssociations(Collection associations, Collection callerFromKeys) 
    throws JAXRException {
        
        Collection callerSourceAssociations = new ArrayList();
        Iterator associationIter = associations.iterator();
        while (associationIter.hasNext()) {
            Association association = (Association)associationIter.next();
            //getSourceObject
            RegistryObject sourceObject = association.getSourceObject();
            if (sourceObject != null) {
                String sourceId = sourceObject.getKey().getId();

                Iterator keysIterator = callerFromKeys.iterator();
                while (keysIterator.hasNext()) {
                    String fromKey = (String)keysIterator.next();
                    if (fromKey.equalsIgnoreCase(sourceId))
                        callerSourceAssociations.add(association);                                
                }           
            }
        }
      return callerSourceAssociations;  
    }     
}
