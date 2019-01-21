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
 * Implementation of Service interface.
 *
 * @author  kwalsh
 * @author Bobby Bissett
 */
public class ServiceImpl extends RegistryEntryImpl implements Service, Serializable {

    private ArrayList serviceBindings;
    private Organization providingOrganization;
    
    /**
     * Default constructor
     */
    public ServiceImpl() {
	super();
        serviceBindings = new ArrayList();
    }
	
    /**
     * Creates new ServiceImpl with the given name
     */
    public ServiceImpl(String name) {
	this();
	this.name = new InternationalStringImpl(name);       
    }

    /**
     * Get the organization that provides this service
     */	
    public Organization getProvidingOrganization() throws JAXRException{
        return providingOrganization;
        
    }
    
    /**
     * Set the organization that provides this service
     */
    public void setProvidingOrganization(Organization org) throws JAXRException{
        providingOrganization = org;
        setIsModified(true);
    }
    
    /** 
     * Add a child ServiceBinding. Sets service on the binding.
     */
    public void addServiceBinding(ServiceBinding serviceBinding) throws JAXRException {
	if (serviceBinding != null) {
            getObject();
	    ((ServiceBindingImpl) serviceBinding).setService(this);
	    serviceBindings.add(serviceBinding);
            setIsModified(true);
	}
    }

    /** 
     * Add a Collection of ServiceBinding children. Treats null
     * param as an empty collection.
     */
    public void addServiceBindings(Collection serviceBindings) throws JAXRException {
	if (serviceBindings == null) {
	    return;
	}
        getObject();
	Iterator iter = serviceBindings.iterator();
	try {
	    while (iter.hasNext()) {
		addServiceBinding((ServiceBinding) iter.next());
	    }
	} catch (ClassCastException e) {
	    throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceImpl:Objects_in_collection_must_be_ServiceBindings"), e);
	}
        setIsModified(true);
    }

    /** 
     * Remove a child ServiceBinding.
     */
    public void removeServiceBinding(ServiceBinding serviceBinding) 
	throws JAXRException {
            if (serviceBinding != null) {
                getObject();
                serviceBindings.remove(serviceBinding);
                setIsModified(true);
            }
    }

    /** 
     * Remove a Collection of children ServiceBindings. Treats
     * null param as an empty collection.
     */
    public void removeServiceBindings(Collection serviceBindings) 
	throws JAXRException {
	    if (serviceBindings != null) {
                getObject();
		this.serviceBindings.removeAll(serviceBindings);
                setIsModified(true);
	    }
    }
    
    /**
     * Get the service bindings
     */
    public Collection getServiceBindings() throws JAXRException {
        if (this.serviceBindings.size() == 0) {
	    getObject();
	}
        return (Collection) serviceBindings.clone();
    }
    
}
