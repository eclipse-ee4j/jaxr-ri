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
 * TelephoneNumberImpl.java
 *
 * Created on May 16, 2001, 11:11 AM
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
public class TelephoneNumberImpl implements TelephoneNumber, Serializable {

    String number;
    String type;
    
    /**
     * Default constructor
     */
    public TelephoneNumberImpl() {
	number = new String();
	type = new String(); // default is "any String"
    }

    /**
     * The telephone number suffix not including the country or area code. 
     */
    public String getNumber() throws JAXRException {
        return number;
    }
    
    /**
     * The telephone number suffix not including the country or area code. 
     */
    public void setNumber(String number) throws JAXRException {
        this.number = number;
    }

    /**
     * The type of telephone number (e.g. fax etc.)
     */
    public String getType() throws JAXRException{
        return type;
    }
    
    /**
     * The type of telephone number (e.g. fax etc.) as a Concept
     */
    public void setType(String type) throws JAXRException {
        this.type = type;
    }

    /**
     * Level 1 method
     */
    public String getCountryCode() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setCountryCode(String countryCode) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public String getAreaCode() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setAreaCode(String areaCode) throws JAXRException {
        //this.areaCode = areaCode;
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public String getExtension() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setExtension(String extension) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public String getUrl() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setUrl(String url) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

}
