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
 * Implementation of EmailAddress interface
 *
 * @see User
 * @author Farrukh S. Najmi
 */
public class EmailAddressImpl implements EmailAddress, Serializable {
    
    String address;
    String type;
    
    /**
     * Default constructor
     */
    public EmailAddressImpl() {
    }

    /**
     * Utility constructor
     */
    public EmailAddressImpl(String address) {
        this.address = address;
    }
    
    /**
     * Utility constructor
     */
    public EmailAddressImpl(String address, String type) {
        this(address);
        this.type = type;
    }
    
    /**
     * Returns the email address for this object.
     */
    public String getAddress() throws JAXRException{
        return address;
    }
    
    /**
     * Sets the email address for this object.
     */
    public void setAddress(String address) throws JAXRException {
        this.address = address;
    }
    
    /**
     * The type for this object.
     */
    public String getType() throws JAXRException {
        return type;
    }
    
    /**
     * Sets the type for this object.
     */
    public void setType(String type) throws JAXRException{
        this.type = type;
    }
}
