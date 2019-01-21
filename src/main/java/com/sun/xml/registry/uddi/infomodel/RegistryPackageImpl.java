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

import java.util.*;
import java.io.Serializable;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;


/**
 * Implementation of RegistryPackage interface. All methods
 * are level 1 methods.
 *
 * @author Bobby Bissett
 */
public class RegistryPackageImpl extends RegistryEntryImpl implements RegistryPackage, Serializable {

    /**
     * Default constructor
     */
    public RegistryPackageImpl() {
	super();
    }

    /**
     * Level 1 method
     */
    public void addRegistryObject(RegistryObject registryObject) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void addRegistryObjects(Collection registryObjects) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void removeRegistryObject(RegistryObject registryObject) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void removeRegistryObjects(Collection registryEntries) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public Set getRegistryObjects() throws JAXRException {
	throw new UnsupportedCapabilityException();		
    }
    
}
