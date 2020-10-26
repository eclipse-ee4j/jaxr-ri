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

package com.sun.xml.registry.common;

import javax.xml.registry.*;
import java.util.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Implementation of BulkResponse.
 *
 * @see
 * @author Farrukh S. Najmi
 */
public class BulkResponseImpl extends JAXRResponseImpl implements BulkResponse {
    
    static final Logger logger = (Logger)
	AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		return Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".common");
	    }
	});

    private ArrayList collection;
    private ArrayList exceptions;
    private boolean isPartial;
    
    public BulkResponseImpl(){
        super();
        isPartial = false;
        collection = new ArrayList();
    }
    
    /**
     * Get the Collection of of objects returned as a response of a
     * bulk operation. This method should block when the response
     * content is not yet available.
     */
    public Collection getCollection() throws JAXRException {
        synchronized (this) {
            while (!isAvailable()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {}
            }
	    return (Collection) collection.clone();
        }
    }
    
    /**
     * Set the collection of the bulk response. This should only
     * be set before sending to the client. Setting the collection
     * after returning to the client should be done through the
     * updateResponse() method.
     */
    public void setCollection(Collection collection) {
        this.collection = new ArrayList(collection);
        if (isPartial) {
            setStatus(STATUS_WARNING);
        }
    }
    
    /**
     * Helper method for adding to the response's already existing
     * collection. This method should only be called before returning
     * the response to the client.
     */
    public void addCollection(Collection bCollection) {
        if (bCollection != null) {
            Iterator iter = bCollection.iterator();
            while( iter.hasNext()) {
                collection.add(iter.next());
            }
        }
    }
    
    /**
     * Get the JAXRException in case of partial commit. Return null if none.
     * This method should block when the response content is not yet
     * available.
     */
    public Collection getExceptions() throws JAXRException {
        synchronized (this) {
            while (!isAvailable()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {}
            }
            
            // exceptions collection is null if there are no errors
            if (exceptions != null) {
                return (Collection) exceptions.clone();
            } else {
                return null;
            }
	}
    }
    
    /**
     * Add multiple exceptions to exception collection. This method should
     * only be called before returning the response to the client.
     */
    public void addException(Collection bException) {
        if (bException != null && bException.size() > 0) {
            setStatus(STATUS_FAILURE);
            initExceptions();
            Iterator iter = bException.iterator();
            while(iter.hasNext()) {
                exceptions.add(iter.next());
            }
        }
    }
	
	/**
     * Add multiple exceptions to exception collection. This method should
     * only be called before returning the response to the client.
     */
    public void setExceptions(Collection bException) {
        if (bException != null && bException.size() > 0) {
            setStatus(STATUS_FAILURE);
            initExceptions();
            exceptions = null;
			exceptions = new ArrayList(bException);
        }
    }

    /**
     * Add single exception to exceptions collection. This method should
     * only be called before returning the response to the client.
     */
    public void addException(JAXRException except) {
        initExceptions();
        exceptions.add(except);
        setStatus(STATUS_FAILURE);
    }
    
    /**
     * Returns true if the reponse is a partial response due to
     * a large result set.
     */
    public boolean isPartialResponse() throws JAXRException {
        return isPartial;
    }
    
    /**
     * Sets isPartial, which is true in the case of a large
     * result set. This should be set before setting the collection
     * when creating the bulk response.
     */
    public void setPartialResponse(boolean isPartial) throws JAXRException {
        if (collection.size() > 0) {
            throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("BulkResponseImpl:Cannot_set_isPartial_with_collection_already_set."));
        }
        this.isPartial = isPartial;
    }
    
    /**
     * Bindings classes return a string rather than boolean,
     * which is passed here to set isPartial. This should be set
     * before setting the collection when creating a bulk response.
     */
    public void setPartialResponse(String isPartial) throws JAXRException {
        if (collection.size() > 0) {
            throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("BulkResponseImpl:Cannot_set_isPartial_with_collection_already_set."));
        }
	this.isPartial = Boolean.valueOf(isPartial).booleanValue();
    }
    
    /**
     * Updates information in BulkResponse in the case of asynchronous
     * connection. If connection is asynchronous, the response should
     * be sent with status = STATUS_UNAVAILABLE. The provider calls
     * the update method to store the information, reset the status,
     * and notify() the thread in case a client has called a method
     * resulting in a wait() until the information is available.
     */
    public void updateResponse(BulkResponse update) throws JAXRException {
	synchronized (this) {
	    
	    // set content and status
            setPartialResponse(update.isPartialResponse());
	    collection = new ArrayList(update.getCollection());
            if (update.getExceptions() != null) {
                exceptions = new ArrayList(update.getExceptions());
            }
	    setStatus(update.getStatus());

	    // wake up thread if waiting
	    this.notify();
	}
    }

    /**
     * Utility method for combining the contents of many
     * bulk responses into one. This is useful for JAXR calls
     * that will include multiple calls to the registry for
     * information.
     *
     * If any of the given responses have isPartial set to true,
     * then the returned response will have isPartial set to
     * true as well. 
     *
     * This method does not set requestId on the returned
     * bulk response.
     *
     * Status for the returned response is determined after
     * the information has been filled in as follows:
     * 1. default = STATUS_SUCCESS
     * 2. if partial, STATUS_WARNING
     * 3. if exceptions.size() > 0, STATUS_FAILURE
     * 
     * @param responses A Collection of BulkResponses
     * @return A BulkResponse containing all the included information
     */
    public static BulkResponse combineBulkResponses(Collection responses) {
        BulkResponseImpl combinedResponse = new BulkResponseImpl();
        combinedResponse.setStatus(JAXRResponse.STATUS_SUCCESS);
        try {
            BulkResponseImpl response = null;
            Collection information = new ArrayList();
            Collection exceptions = new ArrayList();
            boolean isPartial = false;
            Iterator iter = responses.iterator();
            
            while (iter.hasNext()) {
                response = (BulkResponseImpl) iter.next();
                information.addAll(response.getCollection());
                if (response.getExceptions() != null) {
                    exceptions.addAll(response.getExceptions());
                }
                if (response.isPartialResponse() == true) {
                    isPartial = true;
                }
            }
            
            // set partial status before collection
            combinedResponse.setPartialResponse(isPartial);
            if (isPartial == true) {
                combinedResponse.setStatus(JAXRResponse.STATUS_WARNING);
            }
            combinedResponse.setCollection(information);
            if (exceptions.size() > 0) {
                combinedResponse.setExceptions(exceptions);
                combinedResponse.setStatus(JAXRResponse.STATUS_FAILURE);
            }
        } catch (JAXRException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return combinedResponse;
    }
    
    /*
     * The exceptions collection should only be non-null when it
     * contains exceptions.
     */
    private void initExceptions() {
        if (exceptions == null) {
            exceptions = new ArrayList();
        }
    }
    
}

