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
 * Implementation of ExternalIdentifier interface
 *
 * @author Farrukh S. Najmi
 */
public class ExternalIdentifierImpl extends RegistryObjectImpl implements ExternalIdentifier, Serializable {
    
    private String value;
    private RegistryObject registryObject;
    private ClassificationScheme identificationScheme;
    
    public ExternalIdentifierImpl() {
        super();
    }
    
    public ExternalIdentifierImpl(Key key, String name, String value) {
        this();
        this.key = key;
        this.name = new InternationalStringImpl(name);
        this.value = value;
        try {
            if (keyFieldsSet())
                createKey();
        } catch (JAXRException ex){
               //do nothing 
       }
        
    }
    
    public ExternalIdentifierImpl(ClassificationScheme identificationScheme,
        String name, String value) {
            this();
            this.identificationScheme = identificationScheme;
            this.name = new InternationalStringImpl(name);
            this.value = value;
            try {
            if (keyFieldsSet())
                createKey();
            } catch (JAXRException ex){
               //do nothing 
            }
    }
    
    /**
     * Gets the parent registry object
     */
    public RegistryObject getRegistryObject() throws JAXRException {
        return registryObject;
    }

    /**
     * Internal method to set the registry object
     */
    public void setRegistryObject(RegistryObject registryObject) throws JAXRException {
        this.registryObject = registryObject;
        if (keyFieldsSet())
            createKey();
    }
    
    /**
     * Gets the value of an ExternalIdentifier
     */
    public String getValue() throws JAXRException {
        return value;
    }
    
    /**
     * Sets the value of an ExternalIdentifier
     */
    public void setValue(String value) throws JAXRException {
        this.value = value;
        setIsModified(true);
        if (keyFieldsSet())
            createKey();
    }

    /**
     * Gets the ClassificationScheme that is used as the identification scheme
     * for identifying this object.
     */
    public ClassificationScheme getIdentificationScheme() throws JAXRException {
        return identificationScheme;
    }
    
    /**
     * Sets the ClassificationScheme that is used as the identification scheme
     * for identifying this object.
     */
    public void setIdentificationScheme(ClassificationScheme classificationScheme)
        throws JAXRException {
            identificationScheme = classificationScheme;
            setIsModified(true);
            if (keyFieldsSet())
            createKey();
    }
    
    boolean keyFieldsSet()  throws JAXRException {
        boolean set = true;
        if (isRetrieved()) {
            if ((registryObject == null) || (identificationScheme == null) || (value == null)) {
                set = false;
                if ((registryObject.getKey() == null) || (identificationScheme.getKey() == null))
                    set = false;
            }
        }
        return set;
    }
    
    Key createKey() throws JAXRException {
        String registryObjectId = null;
        String identificationSchemeId = null;
        if (isRetrieved()) {
            if (registryObject != null)
                registryObjectId = registryObject.getKey().getId();
            if (identificationScheme != null)
                identificationSchemeId = identificationScheme.getKey().getId();
        }
        
        StringBuffer buf = new StringBuffer(400);
        buf.append(registryObjectId);
        buf.append(":");
        buf.append(identificationSchemeId);
        buf.append(":");
        buf.append(value);
        
        this.key = new KeyImpl(buf.toString());
        return this.key;
    }
    
}
