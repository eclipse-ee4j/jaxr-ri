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

/*
 * UserImpl.java
 *
 * Created on May 16, 2001, 10:39 AM
 */

package com.sun.xml.registry.uddi.infomodel;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import java.net.*;
import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  kwalsh
 * @author Bobby Bissett
 */
public class UserImpl extends RegistryObjectImpl implements User, Serializable {

    // these are set after the User is created
    Organization organization;
    PersonName personName;
    String type;

    ArrayList emailAddresses;
    ArrayList postalAddresses;
    ArrayList telephoneNumbers;
    
    /**
     * Default constructor
     */
    public UserImpl() {
	super();
	emailAddresses = new ArrayList();
        postalAddresses = new ArrayList();
        telephoneNumbers = new ArrayList();
    }

    /**
     * Gets the submitting organization
     */
    public Organization getOrganization() throws JAXRException{
        return organization;
    }

    /** 
     * Name of contact person  
     */
    public PersonName getPersonName() throws JAXRException {
        return personName;
    }
    
    /**
     * Sets Name of contact person.
     */
    public void setPersonName(PersonName personName) throws JAXRException {
        this.personName = personName;
        setIsModified(true);
    }    
    
    /**
     * The postal addresses for this Contact.
     */
    public Collection getPostalAddresses() throws JAXRException {
        return (Collection) postalAddresses.clone();
    }
    
    /**
     * Sets the addresses. Treat null parameter as empty collection.
     */
    public void setPostalAddresses(Collection addresses) throws JAXRException {
        postalAddresses.clear();
        if (addresses != null) {
            postalAddresses.addAll(addresses);
	}
        setIsModified(true);
    }
    
    /** 
     * Gets the telephone numbers for this User that match the specified
     * telephone number type. If phoneType is null return all telephoneNumbers 
     */
    public Collection getTelephoneNumbers(String phoneType) throws JAXRException{
        if (phoneType == null) {
            return (Collection) telephoneNumbers.clone();
        }
        Collection numbers = new ArrayList();
        Iterator iter = telephoneNumbers.iterator();
	while (iter.hasNext()) {
	    TelephoneNumber number = (TelephoneNumber) iter.next();
	    if (number.getType().equals(phoneType)) {
		numbers.add(number);
	    }
	}
        return numbers;
    }
	
    /** 
     * Set the various telephone numbers for this user. Treat null param
     * as an empty collection.
     */
    public void setTelephoneNumbers(Collection phoneNumbers) throws JAXRException {
	telephoneNumbers.clear();
	if (phoneNumbers != null) {
	    telephoneNumbers.addAll(phoneNumbers);
	}
        setIsModified(true);
    }

    /**
     * Get the user email addresses
     */	
    public Collection getEmailAddresses() throws JAXRException {
        return (Collection) emailAddresses.clone();
    }
    
    /**
     * Set the user email addresses. Treat null param as
     * an empty collection.
     */
    public void setEmailAddresses(Collection addresses) throws JAXRException {
	emailAddresses.clear();
	if (addresses != null) {
	    emailAddresses.addAll(addresses);
	}
        setIsModified(true);
    }
    
    /**
     * Get user type
     */
    public String getType() throws JAXRException {
        return type;
    }
	
    /**
     * Set user type
     */
    public void setType(String type) throws JAXRException {
	this.type = type;
        setIsModified(true);
    }
	
    /** 
     * Level 1 method
     */
    public URL getUrl() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Sets the URL to the web page for this contact.
     */
    public void setUrl(URL url) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
     /**
     * Internal method for setting user organization
     */
    public void setOrganization(Organization org) throws JAXRException {
        this.organization = org;
    }

}
