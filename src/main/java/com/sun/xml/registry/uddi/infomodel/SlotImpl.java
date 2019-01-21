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

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import java.io.Serializable;

/**
 * Implementation of JAXR Slot.
 *
 * @author Bobby Bissett
 */
public class SlotImpl implements Slot, Serializable {

    // defaults are null for the strings
    private String name;
    private String slotType;
    private ArrayList values;

    /**
     * Default constructor
     */
    public SlotImpl() {
	values = new ArrayList();
    }
	
    /**
     * Utility constructor when name, type, and one value
     * are known.
     */
    public SlotImpl(String name, String value, String type) {
	this();
	this.name = name;
	slotType = type;
	values.add(value);
    }
	
    /**
     * Utility constructor when name, type, and values
     * are known.
     */
    public SlotImpl(String name, Collection values, String type) {
	this();
	this.name = name;
	slotType = type;
	this.values.addAll(values);
    }
    
    /**
     * Getter for Slot name. Default is a null string.
     */
    public String getName() throws JAXRException {
        return name;
    }
    
    /**
     * Setter for Slot name
     */
    public void setName(String name) throws JAXRException {
        this.name = name;
    }
    
    /**
     * Getter for Slot type. Default is a null string.
     */
    public String getSlotType() throws JAXRException {
        return slotType;
    }
    
    /**
     * Setter for Slot type
     */
    public void setSlotType(String slotType) throws JAXRException {
        this.slotType = slotType;
    }
    
    /**
     * Getter for the Slot values
     */
    public Collection getValues() throws JAXRException {
	return (Collection) values.clone();
    }

    /**
     * Setter for values. Null param will be treated as
     * an empty collection.
     */    
    public void setValues(Collection values) throws JAXRException {
	this.values = new ArrayList();
	if (values != null) {
	    this.values.addAll(values);
	}
    }  
    
}
