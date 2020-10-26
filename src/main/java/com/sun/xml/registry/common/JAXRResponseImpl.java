/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Payara Services Ltd.
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
 * JAXRResponseImpl.java
 *
 * Created on May 17, 2001, 1:13 PM
 */

package com.sun.xml.registry.common;

import javax.xml.registry.*;

/**
 *
 * @author  kwalsh
 * @version
 */
public class JAXRResponseImpl implements javax.xml.registry.JAXRResponse {
    
    protected String requestId;
    protected int status;
    
    /**
     * Creates new JAXRResponseImpl
     */
    public JAXRResponseImpl() {
        status = STATUS_SUCCESS;
    }
    
    /**
     * Returns the request id of the bulk response.
     */
    public String getRequestId() throws JAXRException {
        return requestId;
    }
    
    /**
     * Sets the request id of the response. This is set before
     * returning the response to the client.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    /**
     * Returns the status of the response. Once delivered to a client
     * the status may still be updated by the provider in the case
     * of an asynchronous connection.
     */
    public int getStatus() throws JAXRException {
	synchronized (this) {
	    return status;
	}
    }

    /**
     * Sets the status of the message. This is set before returning
     * the response to the client, except when asynchronous connections
     * are used. Then the status may be changed from STATUS_UNAVAILABLE
     * to the final status.
     */
    public void setStatus(int status)  {
	synchronized (this) {
	    this.status = status;
	}
    }
	
    /**
     * Returns true if a response is available, false otherwise.
     * This is a polling method and must not block.
     */
    public boolean isAvailable() throws JAXRException{
	return (getStatus() != STATUS_UNAVAILABLE);
    }
    
}


