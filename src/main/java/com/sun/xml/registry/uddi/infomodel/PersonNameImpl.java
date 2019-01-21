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
 * Implementation of PersonName interface
 *
 * @author Bobby Bissett
 */
public class PersonNameImpl implements PersonName, Serializable {
	
    private String fullName;

    /**
     * Default constructor
     */    
    public PersonNameImpl() {   
	fullName = new String();
    }
	
    /**
     * Utility constructor given the person's name
     */
    public PersonNameImpl(String fullName){
	this.fullName = fullName;
    }

    /**
     * The fully formatted name for this Person.
     */
    public String getFullName() throws JAXRException {
        return fullName;
    }
    
    /**
     * Sets the fully formatted name for this Person.
     */
    public void setFullName(String fullName) throws JAXRException {
        this.fullName = fullName;
    }
    
    /**
     * Level 1 method
     */
    public String getFirstName() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public String getLastName() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public String getMiddleName() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setFirstName(String firstName) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /** 
     * Level 1 method
     */
    public void setLastName(String lastName) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setMiddleName(String middleName) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
}
