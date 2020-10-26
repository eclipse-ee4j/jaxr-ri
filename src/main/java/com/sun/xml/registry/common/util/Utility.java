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

package com.sun.xml.registry.common.util;

import java.util.*;
import javax.naming.*;
import java.net.*;


import java.util.logging.Logger;

/**
 * Class Declaration.
 * @see
 * @author Farrukh S. Najmi
 * @author Kathy Walsh
 * @version   1.2, 05/02/00
 */
public class Utility {

    public static final String LOGGING_DOMAIN = "javax.enterprise.resource.webservices.registry";
    
    Logger logger = Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".common");

    private static Utility  instance = null;
    private String jaxrHome=null;
    
    /**
     * Class Constructor.
     *
     *
     * @see
     */
    protected Utility() {}
    
    public void setJAXRHome(String jaxrHome) {
        this.jaxrHome = jaxrHome;
    }
    
    public String getJAXRHome() {
	logger.finest("getJAXRHome() called");
        if (jaxrHome == null) {
            try {
                // can throw exception or return null
                jaxrHome = System.getProperty("JAXR_HOME");
                if (jaxrHome == null) { // still
                    throw new NullPointerException();
                }
            } catch (NullPointerException npe) {
                throw new RuntimeException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("Utility:JAXR_HOME_must_be_set"), npe);
            }
        }
        
        return jaxrHome;
    }
    
    /**
     *
     * @return The root content for JAXR client or service
     * @see
     */
    public String getContextRoot() /* throws MessengerException */ {
        //??
        String  contextRoot = "c:/jaxr";        // default
        
        try {
            Context context = new InitialContext();
            
            contextRoot =
		(String) context.lookup("java:comp/env/jaxr-service/contextRoot");
        }
        catch (NamingException e) {
            System.getProperty("JAXR_HOME", "c:/jaxr");
        }
        return contextRoot;
    }
    
    /**
     * Method Declaration.
     *
     *
     * @return
     *
     * @see
     */
    public String getContextRootURLString() {
        String  contextRoot = getContextRoot();
        
        if ((!(contextRoot.startsWith("http", 0)))
	    && (!(contextRoot.startsWith("file", 0)))) {
            contextRoot = "file:///" + contextRoot;
        }
        return contextRoot;
    }
    
    /**
     * Method Declaration.
     *
     *
     * @param urlSuffix
     *
     * @return
     *
     * @exception MalformedURLException
     *
     * @see
     */
    public URL getURL(String urlSuffix) throws MalformedURLException {
        URL     url = null;
        String  contextRootURLString = getContextRootURLString() + urlSuffix;
        
        url = new URL(contextRootURLString);
        return url;
    }
    
    /**
     * Method Declaration.
     *
     *
     * @return
     *
     * @see
     */
    public static Utility getInstance() {
        if (instance == null) {
            synchronized (Utility.class) {
                if (instance == null) {
                    instance = new Utility();
                }
            }
        }
        return instance;
    }
    
    
    
    public static String generateUUID() {
        String uuid=null;
        
        try  {
            uuid = InetAddress.getLocalHost() + (new java.rmi.server.UID()).toString();
        }
        catch (UnknownHostException e)  {
            e.printStackTrace();
            //??
        }
        
        return uuid;
    }
    
}
