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

import java.util.Date;
import java.io.Serializable;

/**
 * Implementation of RegistryEntry interface
 *
 * @author Bobby Bissett
 */
public class RegistryEntryImpl extends RegistryObjectImpl implements RegistryEntry, Versionable, Serializable {

    /**
     * Default constructor
     */    
    public RegistryEntryImpl() {	
	super();        
    }

    /**
     * Constructor used by subclass to
     * initialize RegistryObject data
     */
    public RegistryEntryImpl(Key key, String description, String name) {
	super(key, description, name);
    }

    /**
     * Constructor used by subclass to
     * initialize RegistryObject data
     */
    public RegistryEntryImpl(Key key) {
	super(key);
    }

    /**
     * Level 1 method
     */
    public int getStatus() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public int getStability() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setStability(int stability) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public Date getExpiration() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setExpiration(Date date) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public int getMajorVersion() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setMajorVersion(int majorVersion) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public int getMinorVersion() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setMinorVersion(int minorVersion) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public String getUserVersion() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setUserVersion(String userVersion) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

}

