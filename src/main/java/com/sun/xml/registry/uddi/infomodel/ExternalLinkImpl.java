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

import java.net.*;
import java.util.*;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import com.sun.xml.registry.uddi.ConnectionImpl;
import java.io.Serializable;

/**
 * Implementation of ExternalLink interface
 *
 * @author Farrukh S. Najmi 
 * @author Kathy Walsh  
 */
public class ExternalLinkImpl extends RegistryObjectImpl implements ExternalLink, Serializable {

    private ArrayList registryObjects;
    private String externalURI;
    private URIValidatorImpl validator;
    private RegistryObject parent;
	
    /**
     * Default constructor initializes list and creates
     * URIValidatorImpl object for delgating uri validations
     * tasks.
     */
    public ExternalLinkImpl() throws JAXRException {
	super();
	validator = new URIValidatorImpl();
	registryObjects = new ArrayList();
    }
	
    /**
     * Utility constructor used when URI is given.
     */	
    public ExternalLinkImpl(String uri) throws JAXRException {
	this();
	setExternalURI(uri);
    } 
	
    /**
     * Utility constructor with URI and description.
     */
    public ExternalLinkImpl(String uri, String description)	 throws JAXRException {
	this(uri);
	this.description = new InternationalStringImpl(description);
    }       
	
    /**
     * Gets the collection of RegistryObjects that are annotated by this
     * ExternalLink
     */
    public Collection getLinkedObjects() throws JAXRException {
	return (Collection) registryObjects.clone();
    }

    /**
     * Turns validation on or off.
     *
     * @see URIValidatorImpl
     */        
    public void setValidateURI(boolean validate) throws JAXRException {
	validator.setValidateURI(validate);
    }
        
    /**
     * Whether or not validation is turned on
     *
     * @see URIValidatorImpl
     */
    public boolean getValidateURI(){
	return validator.getValidateURI();
    }
	
    /**
     * Gets URI to the an external resource
     */
    public String getExternalURI() throws JAXRException   {
	return externalURI;    
    }
				
    /**
     * Sets URI for an external resource. Throws an exception
     * if the uri is invalid (when validation is on).
     */
    public void setExternalURI(String externalUri) throws JAXRException {
	validator.validate(externalUri);
	externalURI = externalUri;
        setIsModified(true);
    }

    /**
     * Method used by other classes when adding an
     * external link to themselves.
     */
    void addLinkedObject(RegistryObject registryObject) {
	registryObjects.add(registryObject);
    }

    /**
     * Override the behavior in RegistryObject to return a
     * provider generated id. Gets a sequence id from the
     * first parent registry object in registryObjects
     * ArrayList.
     */
    public Key getKey() throws JAXRException {
	Iterator iter = registryObjects.iterator();
	if (iter.hasNext()) {
	    RegistryObjectImpl rObject = (RegistryObjectImpl) iter.next();
	    int sequenceId = rObject.getSequenceId(this);
	    return new KeyImpl(externalURI + ":" + sequenceId);
	} else {
	    return null;
	}
    }

    /*
     * Set the parent registry object when this external
     * link is added to a registry object.
     */
    void setParent(RegistryObject object) {
	parent = object;
    }

}

