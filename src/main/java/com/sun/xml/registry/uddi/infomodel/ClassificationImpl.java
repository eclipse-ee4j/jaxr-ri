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

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;
import java.io.Serializable;

/**
 * Implementation of Classification interface
 *
 * @author Bobby Bissett
 */
public class ClassificationImpl extends RegistryObjectImpl implements Classification, Serializable {

    private Concept concept;
    private RegistryObject classifiedObject;
    private ClassificationScheme classificationScheme;
    private String value;
		
    /**
     * Default constructor
     */
    public ClassificationImpl() {
        super();
    }
	
    public ClassificationImpl(ClassificationScheme scheme, String name, String value) {
        this();
        classificationScheme = scheme;
        this.name = new InternationalStringImpl(name);
        this.value = value;
    }
	
    public ClassificationImpl(Concept concept) {
        this();
        this.concept = concept;
    }
	
    /**
     * Gets the Concept that is classifying the object.
     */
    public Concept getConcept() throws JAXRException {
        return concept;
    }

    /**
     * Sets the Concept for this classification.
     */
    public void setConcept(Concept concept) throws JAXRException {
        this.concept = concept;
        setIsModified(true);
    }

    /**
     * Get the classifiation scheme
     */
    public ClassificationScheme getClassificationScheme() throws JAXRException {
        return (isExternal() ?
            classificationScheme : concept.getClassificationScheme());
    }

    /**
     * Sets the ClassificationScheme for this classification.
     * If this method is called then you must also call setValue method.
     * This method should be used mutually exclusively with
     * the setConcept method.
     */
    public void setClassificationScheme(ClassificationScheme classificationScheme)
        throws JAXRException {
            this.classificationScheme = classificationScheme;
            setIsModified(true);
        
            // now an external classification scheme
            concept = null;
    }

    /**
     * Gets the value of the classification. If it is an internal
     * Classification, then return the value of the concept.
     */
    public String getValue() throws JAXRException {	
	return (concept!=null ? concept.getValue() : value);
    }
    
    /**
     * Set the classification value. This causes the classification
     * to become external if it is not already.
     */
    public void setValue(String value) {
        this.value = value;
        setIsModified(true);

	// now an external classification scheme
	concept = null;
    }
   
    /**
     * Gets the Object that is being classified.
     */
    public RegistryObject getClassifiedObject() throws JAXRException {
        return classifiedObject;
    }
    
   
    /**
     * Sets the Object that is being classified.
     */
    public void setClassifiedObject(RegistryObject object) throws JAXRException {
        if (object != null) {
            classifiedObject = object;
            setIsModified(true);
        }
    }
	
    /**
     * Returns whether or not this is an external (has
     * value, no concept) or internal (gets value from
     * concept) classification.
     */
    public boolean isExternal() {
	return (concept == null);
    }

    /**
     * Override the behavior in RegistryObject to return a
     * provider generated id.
     */
    public Key getKey() throws JAXRException {
	if ((classifiedObject == null) || (classifiedObject.getKey() == null)) {
	    return null;
	}
	String id = null;
	if (isExternal()) {
	    if ((classificationScheme == null) || (classificationScheme.getKey() == null)) {
		return null;
	    }
	    id = classifiedObject.getKey().getId() + ":" +
		classificationScheme.getKey().getId() + ":" +
		value;
	} else {
	    if ((concept == null) || (concept.getKey() == null)) {
		return null;
	    }
	    id = classifiedObject.getKey().getId() + ":" +
		concept.getKey().getId();
	}
	return new KeyImpl(id);
    }

}
