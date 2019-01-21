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
 *
 * @author  kwalsh
 * @author Bobby Bissett
 */
public class PostalAddressImpl extends ExtensibleObjectImpl implements PostalAddress, Serializable {

    private ClassificationScheme postalScheme;
    private String city;
    private String country;
    private String postalCode;
    private String stateOrProvince;
    private String street;
    private String streetNumber;
    private String addressType;
    
    /**
     * Creates new PostalAddressImpl. Sets
     * default empty strings.
     */
    public PostalAddressImpl() {
	super();
	addressType = new String();
	city = new String();
	country = new String();
	postalCode = new String();
	stateOrProvince = new String();
	street = new String();
	streetNumber = new String();
    }
	
    /** 
     * Utility constructor
     */
    public PostalAddressImpl(String streetNumber, String street, String city,
			     String stateOrProvince, String country,
			     String postalCode, String type) {
	this();
	this.streetNumber = streetNumber;
	this.street = street;
	this.city = city;
	this.stateOrProvince = stateOrProvince;
	this.country = country;
	this.postalCode = postalCode;
	addressType = type;
    }
	
   /**
     * The street address
     */
    public String getStreet() throws JAXRException {
        return street;
    }
    
    /**
     * Sets the street address
     */
    public void setStreet(String street) throws JAXRException {
	this.street = street;
    }
    
    /** 
     * The street number 
     */
    public String getStreetNumber() throws JAXRException {
	return streetNumber;
    }
	
    /**
     * Sets the street number
     */
    public void setStreetNumber(String streetNumber) throws JAXRException {
	this.streetNumber = streetNumber;
    }

    /**
     * The city
     */
    public String getCity() throws JAXRException {
        return city;
    }
    
    /**
     * Sets the city
     */
    public void setCity(String city) throws JAXRException {
        this.city = city;
    }

    /**
     * The state or province
     */
    public String getStateOrProvince() throws JAXRException {
        return stateOrProvince;
    }

    /**
     * Sets the state or province
     */
    public void setStateOrProvince(String stateOrProvince) throws JAXRException {
        this.stateOrProvince = stateOrProvince;
    }
    
    /**
     * The postal or zip code
     */
    public String getPostalCode() throws JAXRException {
        return postalCode;
    }
    
    /**
     * Sets the postal or zip code
     */
    public void setPostalCode(String postalCode) throws JAXRException {
        this.postalCode = postalCode;
    }

    /**
     * The country
     */
    public String getCountry() throws JAXRException {
        return country;
    }   

    /**
     * Sets the country
     */
    public void setCountry(String country) throws JAXRException {
        this.country = country;
    }

 
    /**
     * The type of address (e.g. "headquarters" etc.) as a String
     */
    public String getType() throws JAXRException {
        return addressType;
    }


	
    /**
     * Sets the type of address (e.g. "headquarters" etc.) as a Concept
     */
    public void setType(String type) throws JAXRException {
        addressType = type;
    }

    /** 
     * Get a user-defined postal scheme for codifying the attributes of PostalAddress
     * If none is defined for this object, then must rerturn the default value
     * returned by RegistryService#getDefaultPostalScheme()
     */ 
    public ClassificationScheme getPostalScheme() throws JAXRException {
	return postalScheme;
    }

    /** 
     * Set a user-defined postal scheme for codifying the attributes of PostalAddress
     */ 
    public void setPostalScheme(ClassificationScheme scheme) throws JAXRException {
	    postalScheme = scheme;	
    }

}
