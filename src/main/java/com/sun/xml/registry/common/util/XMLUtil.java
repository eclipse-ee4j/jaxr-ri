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

package com.sun.xml.registry.common.util;

import java.util.*;
import java.io.*;
import javax.naming.*;
import java.net.*;
import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import com.sun.xml.registry.uddi.*;

import com.sun.xml.registry.common.*;
import com.sun.xml.registry.uddi.infomodel.*;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Class Declaration.
 * @see
 * @author Farrukh S. Najmi
 * @author Kathy Walsh
 * @version   1.2, 05/02/00
 */
public class XMLUtil {

    Logger logger = Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".common");

    private static XMLUtil instance = null;

    /**
     * Class Constructor.
     *
     *
     * @see
     */
    protected XMLUtil() {
    }


    /**
     * Method Declaration.
     *
     *
     * @return
     *
     * @see
     */
    public static XMLUtil getInstance() {
        if (instance == null) {
            synchronized (XMLUtil.class) {
                if (instance == null) {
                    instance = new XMLUtil();
                }
            }
        }
        return instance;
    }


    public static String authToken2XXX(String xml) {

        StringBuffer buff = new StringBuffer(150);
        StringTokenizer tokenizer = new StringTokenizer(xml);
        while (tokenizer.hasMoreTokens()) {
            String token = (String) tokenizer.nextToken();
            if (token.indexOf("userID") != -1)
                token = "userID=\"********\"";

            if (token.indexOf("cred") != -1)
                token = "cred=\"********\"";

            buff.append(token);
            buff.append(" ");
        }
        return buff.toString();
    }

    public static String authInfo2XXX(String xml) {

        StringBuffer buff = new StringBuffer(150);
        StringTokenizer tokenizer = new StringTokenizer(xml, "<>", true);
        boolean found = false;
        while (tokenizer.hasMoreTokens()) {
            String token = (String) tokenizer.nextToken();
            if (!found) {
                if (token.indexOf("authInfo") != -1) {
                    buff.append(token);
                    token = tokenizer.nextToken();
                    if (token.equals(">")) {
                        buff.append(token);
                        token = tokenizer.nextToken();
                        token = "****************************";
                    }
                    found = true;
                }
            }
            buff.append(token);
        }
        return buff.toString();
    }


    public static String generateUUID() {
        String uuid = null;

        try {
            uuid = InetAddress.getLocalHost() + (new java.rmi.server.UID()).toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            //??
        }

        return uuid;
    }


}
