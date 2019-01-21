/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.registry.uddi.infomodel;

import com.sun.xml.registry.uddi.*;
import com.sun.xml.registry.common.util.*;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import java.util.*;
import java.io.Serializable;

/**
 * Implementation of ClassificationScheme interface
 */
public class ClassificationSchemeImpl extends RegistryEntryImpl 
	implements ClassificationScheme, Serializable {
	
    transient boolean childrenLoaded=false;
    transient boolean isPredefined = false;
	    
    ArrayList children;	
    
    public ClassificationSchemeImpl() {
	super();
	children = new ArrayList();
	childrenLoaded = false;
    }
	    
    public ClassificationSchemeImpl(Key key) {
        this();
        this.key = key;
    }
	    
    public ClassificationSchemeImpl(Concept concept) throws JAXRException {
	this(concept.getKey());
	name = concept.getName();
	description = concept.getDescription();	
    }
	
    public ClassificationSchemeImpl(Key key, String description, String name) {
        this(key);
        this.description = new InternationalStringImpl(description);
        this.name = new InternationalStringImpl(name);
    }
	    
    public ClassificationSchemeImpl(String key, String description, String name) {
        this(new KeyImpl(key), description, name);
    }
	    
    public ClassificationSchemeImpl(String name, String description) {
        this();
	this.name = new InternationalStringImpl(name);
	this.description = new InternationalStringImpl(description);
    }
	
    /**
     * Add a child Concept
     */
    public void addChildConcept(Concept concept) throws JAXRException {
	if ((concept != null) && (!children.contains(concept))) {
	    ((ConceptImpl) concept).setClassificationScheme(this);
	    children.add(concept);
	    childrenLoaded = true;
	    setIsModified(true);
	}
    }
    
    /**
     * Add a Collection of Concept children. Treat null
     * parameter as empty collection.
     */
    public void addChildConcepts(Collection concepts) throws JAXRException {
        if (concepts == null) {
            return;
        }
        Iterator iter = concepts.iterator();
	try {
	    while (iter.hasNext()) {
		ConceptImpl concept = (ConceptImpl) iter.next();
		addChildConcept(concept);
	    }
	} catch (ClassCastException e) {
	    throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Objects_in_collection_must_be_Concepts"), e);
	}
    }

    /**
     * Remove child concept
     */
    public void removeChildConcept(Concept concept) throws JAXRException {
        if (concept != null) {
            children.remove(concept);
            setIsModified(true);
        }
    }
    
    /**
     * Remove a Collection of children Concepts. Treat null
     * paramter as empty collection.
     */
    public void removeChildConcepts(Collection concepts) throws JAXRException {
        if (concepts != null) {
            children.removeAll(concepts);
            setIsModified(true);
        }
    }
    
    /**
     * Get number of children
     */
    public int getChildConceptCount() throws JAXRException {
        return children.size();
    }

    /**
     * Get all immediate children Concepts
     */
    public Collection getChildrenConcepts() throws JAXRException {
         return (Collection) children.clone();
    }
    
    /**
     * Get all descendant Concepts
     */
    public Collection getDescendantConcepts() throws JAXRException {
        ArrayList descendants = new ArrayList(children);
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            Concept child = (Concept) iter.next();
            if (child.getChildConceptCount() > 0) {
                descendants.addAll(child.getDescendantConcepts());
            }
        }
        return descendants;
    }
    
    public boolean isExternal() {
	return (children.size() == 0);
    }

    public boolean isPredefined() {
	return isPredefined;
    }
	    
    public void setPredefined(boolean predefined) {
	isPredefined = predefined;
    }
	    
    public boolean childrenLoaded() {
	return childrenLoaded;
    }
	    
    public void setChildrenLoaded(boolean loaded) {
	childrenLoaded = loaded;
    }
			
    
    /**
     * Overrides behavior in RegistryObjectImpl to allow adding
     * external links. If an external link already exists, this 
     * method throws UnsupportedCapabilityException. See appendix
     * D of specification.
     */
    public void addExternalLink(ExternalLink link) throws JAXRException {
	if (externalLinks.size() > 0) {
	    throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:ExternalLink_already_exists,_cannot_add_more."));
	}
	if (link != null) {
	    ExternalLinkImpl externalLink = (ExternalLinkImpl) link;
	    externalLink.addLinkedObject(this);
	    externalLinks.add(externalLink);
            setIsModified(true);
	}
    }
       
    /**
     * Overrides behavior in RegistryObjectImpl to allow adding
     * external links. If an external link already exists or if
     * the collection contains more than one external link, this
     * method throws UnsupportedCapabilityException. See appendix
     * D of specification.
     */
    public void addExternalLinks(Collection links) throws JAXRException {
	if (externalLinks.size() > 0) {
	    throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:ExternalLink_already_exists,_cannot_add_more."));
	}
	if (links != null) {			
	    if (links.size() > 1) {
		throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Cannot_add_more_than_one_ExternalLink"));
	    }
	    Iterator iter = links.iterator();
	    try {
		while (iter.hasNext()) {
		    ExternalLinkImpl externalLink = (ExternalLinkImpl) iter.next();
		    externalLink.addLinkedObject(this);
		    externalLinks.add(externalLink);
                    setIsModified(true);
		}	        
	    } catch (ClassCastException e) {
		throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Objects_in_collection_must_be_ExternalLinks"), e);
	    }
	}
    }
   
    /**
     * Overrides behavior in RegistryObjectImpl to allow adding
     * external links. If an external link already exists or if
     * the collection contains more than one external link, this
     * method throws UnsupportedCapabilityException. See appendix
     * D of specification.
     */
    public void setExternalLinks(Collection links) throws JAXRException {
        if (links == null) {
            externalLinks.clear();
            return;
        }
	if (links.size() > 1) {
	    throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Cannot_set_more_than_one_ExternalLink."));
	}
	externalLinks.clear();
	addExternalLinks(links);
    }
    
    /**
     * Level 1 method
     */
    public int getValueType() throws JAXRException{
        throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setValueType(int valueType) throws JAXRException{
        throw new UnsupportedCapabilityException();
    }

}
