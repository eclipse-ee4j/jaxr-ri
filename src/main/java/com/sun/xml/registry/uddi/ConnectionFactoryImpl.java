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

import java.util.Properties;
import java.util.Collection;

import javax.xml.registry.*;
import com.sun.xml.registry.common.util.*;

/**
 * Class Declaration for Class1
 * @see
 * @author Farrukh S. Najmi
 */
public class ConnectionFactoryImpl extends ConnectionFactory {

    private Properties properties;

    /**
     * Default constructor
     */
    public ConnectionFactoryImpl() {
    }

    public void setProperties(Properties properties)
	throws JAXRException {
	    this.properties = properties;
    }
        
    public Properties getProperties() throws JAXRException {
	return properties;
    }
        
    /**
     * Create a named connection. Such a connection can be used to
     * communicate with a JAXR provider.
     *
     * @link dependency
     * @label creates
     * @associates <{Connection}>
     */
    public Connection createConnection() throws JAXRException, InvalidRequestException {	
        return new ConnectionImpl(properties);
    }
	
    /**
     * Create a Federation.
     *
     * @param properties configuration properties that are either 
     * specified by JAXR or provider specific.
     *
     *
     * <p><DL><DT><B>Capability Level: 0 </B></DL> 	 
     *
     * @param connections Is a Collection of Connection objects. Note that
     * Connection objects may also be Federation objects.
     *
     * @link dependency
     * @label creates
     * @associates <{Federation}>
     */	 
    public FederatedConnection createFederatedConnection(Collection connections) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }	
	
}
