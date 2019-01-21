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

import javax.activation.DataHandler;
import java.io.Serializable;

/**
 * Implementation of ExtrinsicObject. All levels are level 1 methods
 */
public class ExtrinsicObjectImpl extends RegistryEntryImpl implements ExtrinsicObject, Serializable {

    /**
     * Default constructor
     */
    public ExtrinsicObjectImpl() {
        super();
    }

    /**
     * Level 1 method
     */
    public String getMimeType() throws JAXRException {
		throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public void setMimeType(String mimeType) throws JAXRException {
		throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public boolean isOpaque() throws javax.xml.registry.JAXRException {
		throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setOpaque(boolean isOpaque) throws JAXRException {
		throw new UnsupportedCapabilityException();
    }
    
    /**
     * Level 1 method
     */
    public DataHandler getRepositoryItem() throws JAXRException {
        throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method
     */
    public void setRepositoryItem(javax.activation.DataHandler repositoryItem) throws UnsupportedCapabilityException, JAXRException {
        throw new UnsupportedCapabilityException();
    }
    
}
