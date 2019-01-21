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

import java.util.*;
import java.io.Serializable;

/**
 * Implementation of the ServiceBinding interface
 * 
 * @author Farrukh S. Najmi
 * @author Kathy Walsh
 */
public class ServiceBindingImpl extends RegistryObjectImpl implements ServiceBinding, Serializable {

    private String accessURI;
    private ServiceBinding targetBinding;
    private Service parentService;
    private ArrayList specificationLinks;
    private URIValidatorImpl validator;
	
    /**
     * Default constructor initializes list and creates
     * URIValidatorImpl object for delgating uri validations
     * tasks.
     */
    public ServiceBindingImpl() {
	super();
	validator = new URIValidatorImpl();
	specificationLinks = new ArrayList();
    }
	
    /**
     * Gets the URI that gives access to the service via this binding.
     */ 
    public String getAccessURI() throws JAXRException {
	if (accessURI == null) {
	    getObject();
	}
	return accessURI;
    }
	
    /**
     * Sets the URI that gives access to the service via this binding.
     * If there is already a target binding, throw an exception.
     */ 
    public void setAccessURI(String uri) throws JAXRException {
	if (targetBinding != null) {
	    throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceBindingImpl:TargetBinding_already_set"));
	}
        getObject();
	validator.validate(uri);
	accessURI = uri;
        setIsModified(true);
    }
	
    /**
     * Gets the next ServiceInterfaceBinding in case there is a redirection
     */
    public ServiceBinding getTargetBinding() throws JAXRException {
	if (targetBinding == null) {
	    getObject();
	}
	return targetBinding;
    }
	
    /**
     * Sets the next ServiceInterfaceBinding in case there is a redirection.
     * Throws an exception if there is already an accessURI.
     */
    public void setTargetBinding(ServiceBinding binding) throws JAXRException {
	if (accessURI != null) {
	    throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceBindingImpl:AccessURI_already_set"));
	}
        getObject();
	targetBinding = binding;
        setIsModified(true);
    }
 
    /**
     * Gets the parent service for which this is a binding
     */ 
    public Service getService() throws JAXRException {
	if (parentService == null) {
	    getObject();
	}	
	return parentService;		
    }
	
    /**
     * Internal method for setting service
     */
    public void setService(Service service) {
	parentService = service;		
    }

    /**
     * Get specification links
     */
    public Collection getSpecificationLinks() throws JAXRException {
	if (this.specificationLinks.size() == 0) {
	    getObject();
	}
	return (Collection) specificationLinks.clone();
    }
	
    /**
     * Add specification link. This will call
     * setServiceBinding() on the added SpecificationLink.
     */
    public void addSpecificationLink(SpecificationLink link) throws JAXRException {
	if (link != null) {
            getObject();
            ((SpecificationLinkImpl) link).setServiceBinding(this);
	    specificationLinks.add(link);
            setIsModified(true);
	}
    }

    /**
     * Add a Collection of SpecificationLink children. Treat null
     * collection as an empty collection. Sets the service binding
     * on each specification link.
     */
    public void addSpecificationLinks(Collection specificationLinks) 
    	throws JAXRException {
	    if (specificationLinks == null) {
                return;
            }
            getObject();
            Iterator iter = specificationLinks.iterator();
            try {
                while (iter.hasNext()) {
                    ((SpecificationLinkImpl) iter.next()).setServiceBinding(this);
                }
            } catch (ClassCastException cce) {
                throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceBindingImpl:Objects_in_collection_must_be_SpecificationLinks"), cce);
            }
            this.specificationLinks.addAll(specificationLinks);
            setIsModified(true);
    }

    /**
     * Remove a child SpecificationLink
     */
    public void removeSpecificationLink(SpecificationLink specificationLink) 
	throws JAXRException {
	    if (specificationLink != null) {
                getObject();
		this.specificationLinks.remove(specificationLink);
                setIsModified(true);
	    }
    }

    /**
     * Remove a Collection of children SpecificationLinks. Treat null
     * param as an empty collection.
     */
    public void removeSpecificationLinks(Collection specificationLinks) 
	throws JAXRException {
	    if (specificationLinks != null) {
                getObject();
		this.specificationLinks.removeAll(specificationLinks);
                setIsModified(true);
	    }
    }

    /**
     * Internal method to set the specification links
     */
    public void setSpecificationLinks(Collection links) throws JAXRException {
	if (links == null) {
	    specificationLinks = new ArrayList();
	} else {
            getObject();
	    specificationLinks = new ArrayList(links);
	}
    }
    
    /**
     * Turns validation on or off.
     *
     * @see URIValidatorImpl
     */        
    public void setValidateURI(boolean validate) {
	validator.setValidateURI(validate);
    }
    
    /**
     * Whether or not validation is turned on
     *
     * @see URIValidatorImpl
     */
    public boolean getValidateURI() {
	return validator.getValidateURI();
    }

    /*
     * Internal method allowing a child SpecificationLink
     * to get its sequenceId. Returns null if it is not
     * a child of this ServiceBinding.
     */
    int getSequenceId(SpecificationLink link) {
	return specificationLinks.indexOf(link);
    }

}
