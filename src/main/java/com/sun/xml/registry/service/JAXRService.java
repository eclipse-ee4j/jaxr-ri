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

/*
 * JAXRService.java
 *
 * Created on November 9, 2001, 2:25 PM
 */
package com.sun.xml.registry.service;

import com.sun.xml.registry.common.*;
import com.sun.xml.registry.common.util.*;
import java.util.*;
import javax.naming.*;
import javax.xml.registry.*;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class simply instantiates a jaxr ConnectionFactoryImpl
 * and binds it in a namespace.
 *
 * @author  bbissett
 */
public class JAXRService {
    
    Logger logger = Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".service");
    private static JAXRService instance;
    
    private JAXRService() {
        // nothing needed
    }
    
    /**
     * This is a singleton class.
     *
     * @return The single instance of the JAXRService class
     */
    public static JAXRService getInstance() {
        if (instance == null) {
            instance = new JAXRService();
        }
        return instance;
    }

    /**
     * 
     */
    void startService() {
        try {
            ConnectionFactoryImpl factory = new ConnectionFactoryImpl();
            Context ctx = new InitialContext();
            ctx.rebind("javax.xml.registry.ConnectionFactory", factory);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    void stopService() {
        try {
            Context ctx = new InitialContext();
            ctx.unbind("JAXRConnectionFactory");
            ctx.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String args[]) {
        if (args.length == 0) {
            showUsage();
        }

        JAXRService service = JAXRService.getInstance();
        String command = args[0];
        
        if (command.equals("-startService")) {
            service.startService();
        } else if (command.equals("-stopService")) {
            service.stopService();
        } else {
            showUsage();
        }
    }

    private static void showUsage() {
        System.err.println("Must specify -startService or -stopService");
        System.exit(-1);
    }
        
}
