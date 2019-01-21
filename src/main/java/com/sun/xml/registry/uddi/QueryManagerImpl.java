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

package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.*;
import com.sun.xml.registry.common.util.*;
import java.util.Collection;
import java.util.Locale;
import javax.xml.registry.*;
import javax.xml.registry.infomodel.RegistryObject;


/**
 * Class Declaration for Class1
 * @see
 * @author 
 */
public class QueryManagerImpl implements QueryManager {
    
    RegistryServiceImpl service;
    UDDIMapper uddi;
    
    //for testing
    public QueryManagerImpl() {
        // Fix for CRs 6520297 and 6520354
        String country = Locale.getDefault().getCountry();        
        // Default country is 'null' on Solaris.  Use 'US' as default country
        // and 'en' as default languge.
        if (country == null || country == "") {
            Locale.setDefault(Locale.US);
        }
        System.out.println("Default locale: "+Locale.getDefault().toString());
    }
    
    public QueryManagerImpl(RegistryServiceImpl service) {
        this();
        this.service = service;
        uddi = service.getUDDIMapper();
    }
    /**
     * gets the specified RegistryObjects
     *
     * @return BulkResponse containing Collection of RegistryObjects.
     */
    public BulkResponse getRegistryObjects(Collection objectKeys)  throws JAXRException{
        throw new UnsupportedCapabilityException();
    }
    
    /**
     * Gets the RegistryObjects owned by the caller.
     * The objects are returned as their concrete type (e.g. Organization, User etc.).
     *
     *
     * @return BulkResponse containing a hetrogeneous Collection of RegistryObjects (e.g. Organization, User etc.).
     */
    public BulkResponse getRegistryObjects()  throws JAXRException {
        if (!service.getConnection().isSynchronous()) {
            BulkResponseImpl response = new BulkResponseImpl();
            response.setStatus(JAXRResponse.STATUS_UNAVAILABLE);
            response.setRequestId(Utility.getInstance().generateUUID());
            service.storeBulkResponse(response);
            FuturesRequestManager.invokeCommand(
                new JAXRCommand.GetRegistryObjectsCommand(service,
                    response));
            return response;
        } else {
            return uddi.getRegistryObjects();
        }
    }
    
  
    
    /**
     * Gets the RegistryObject specified by the Id.
     *
     * @return RegistryObject Is the object is returned as their concrete type (e.g. Organization, User etc.).
     */
    public RegistryObject getRegistryObject(String id)  throws JAXRException{
        throw new UnsupportedCapabilityException();
    }
    
    /**
     * Gets the RegistryObject specified by the Id.
     *
     * @return RegistryObject Is the object is returned as their concrete type (e.g. Organization, User etc.).
     */
    public BulkResponse getRegistryObjects(String objectType)  throws JAXRException{
        if (!service.getConnection().isSynchronous()) {
            BulkResponseImpl response = new BulkResponseImpl();
            response.setStatus(JAXRResponse.STATUS_UNAVAILABLE);
            response.setRequestId(Utility.getInstance().generateUUID());
            service.storeBulkResponse(response);
            FuturesRequestManager.invokeCommand(
                new JAXRCommand.GetRegistryObjectsByTypeCommand(service,
                    response, objectType));
            return response;
        } else {
            return uddi.getRegistryObjects(objectType);
        }
    }
    
    public RegistryObject getRegistryObject(String id, String type) throws JAXRException {
         return uddi.getRegistryObject(id, type); 
    }
    
    public BulkResponse getRegistryObjects(Collection ids, String type) throws JAXRException {
        if (!service.getConnection().isSynchronous()) {
            BulkResponseImpl response = new BulkResponseImpl();
            response.setStatus(JAXRResponse.STATUS_UNAVAILABLE);
            response.setRequestId(Utility.getInstance().generateUUID());
            service.storeBulkResponse(response);
            FuturesRequestManager.invokeCommand(
                new JAXRCommand.GetRegistryObjectsByKeysCommand(service,
                    response, ids, type));
            return response;
        } else {
            return uddi.getRegistryObjects(ids, type);
        }       
    }
    
    public RegistryService getRegistryService(){
        return service;
    }
}
