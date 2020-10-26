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

package com.sun.xml.registry.uddi;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import java.util.*;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.xml.soap.*;

import com.sun.xml.registry.common.util.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Implementation class for RegistryService
 *
 * @see javax.xml.registry.RegistryService
 * @author Farrukh S. Najmi
 */
public class RegistryServiceImpl implements RegistryService {
    
    Logger logger = (Logger)
    AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() {
            return Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".uddi");
        }
    });
    
    private BusinessLifeCycleManager businessLCM;
    private BusinessQueryManager businessQM;
    private LifeCycleManager lcm;
    private ConnectionImpl connection;
    private BulkResponse bulkResponse;
    private UDDIMapper uddiMapper;
    private String serviceId;
    private UDDIObjectCache objectManager;
    private HashMap bulkResponses;
    private XMLUtil xmlUtil;
    
    private boolean securitySet;
    
    private HashMap equivalentConcepts;
    private String defaultPostalSchemeId;
    
    RegistryServiceImpl(ConnectionImpl connection) {
        securitySet = false;
        bulkResponses = new HashMap();
        equivalentConcepts = new HashMap();
        this.connection = connection;
        
        serviceId = Utility.generateUUID();
        xmlUtil = XMLUtil.getInstance();
        uddiMapper = new UDDIMapper(this); //needs to be initialized here
        objectManager = uddiMapper.getObjectManager();
        
    }
    
    public UDDIMapper getUDDIMapper() {
        if (uddiMapper == null)
            uddiMapper = new UDDIMapper(this);
        return uddiMapper;
    }
    
    public String getServiceId() {
        return serviceId;
    }
    
    public UDDIObjectCache getObjectManager() {
        if (objectManager == null)
            objectManager = uddiMapper.getObjectManager();
        return objectManager;
    }
    
    /**
     * Returns the CapabilityProfile for the JAXR provider
     * @associates <{CapabilityProfile}>
     * @see <{LifeCycleManager}>
     */
    public CapabilityProfile getCapabilityProfile() throws JAXRException{
        return CapabilityProfileImpl.getInstance();
    }
    
    /**
     * Returns the BusinessLifeCycleManager interface implemented by the JAXR provider
     * @associates <{BusinessLifeCycleManager}>
     * @see <{LifeCycleManager}>
     */
    public BusinessLifeCycleManager getBusinessLifeCycleManager() throws JAXRException{
        if (businessLCM == null) {
            businessLCM = new BusinessLifeCycleManagerImpl(this);
        }
        return businessLCM;
    }
    
    /**
     * Returns the BusinessQueryManager interface implemented by the JAXR provider
     * @associates <{BusinessQueryManager}>
     * @directed
     */
    public BusinessQueryManager getBusinessQueryManager() throws JAXRException{
        if (businessQM == null) {
            businessQM = new BusinessQueryManagerImpl(this);
        }
        
        return businessQM;
    }
    
    /**
     * Returns the basic LifeCycleManager interface implemented by the JAXR provider
     *
     * <p><DL><DT><B>Capability Level: 0 </B></DL>
     *
     * @associates <{javax.xml.registry.LifeCycleManager}>
     */
    public LifeCycleManager getLifeCycleManager() throws JAXRException {
        if (lcm == null) {
            lcm = new LifeCycleManagerImpl(this);
        }
        return lcm;
        
    }
    
    /**
     * Returns the DeclarativeQueryManager interface implemented by the JAXR provider
     * Capbility level 0 registries should throws UnsupportedCapabilityException.
     *
     * <p><DL><DT><B>Capability Level: 1 </B></DL>
     *
     * @associates <{DeclarativeQueryManager}>
     * @directed
     */
    public DeclarativeQueryManager getDeclarativeQueryManager()
    throws JAXRException, UnsupportedCapabilityException {
        throw new UnsupportedCapabilityException();
    }
    
    
    /**
     * This method takes a String that is an XML request in a registry specific
     * format. It sends the request to the registry and returns a String that is
     * the registry specific XML response.
     *
     * <p><DL><DT><B>Capability Level: 0 </B></DL>
     *
     * @return String that is the XML response in a registry specific manner.
     */
    public String makeRegistrySpecificRequest(String request) throws JAXRException {
        boolean secure = false;
        try {
            return uddiMapper.makeRegistrySpecificRequest(request, secure);
        } catch (JAXRException jex) {
            secure = true;
            return uddiMapper.makeRegistrySpecificRequest(request, secure);
        }
        
    }
    
    /**
     * Declare thetwo Concepts as being equivalent Often one is user defined concept
     * while the other is a pre-defined Concept.
     * Note that a provider may also allow static definition static of equivalences
     * in a provider-specific manner.
     */
    //package utility
    void addConceptMapping(String conceptId, String equivalentConceptId)
    throws JAXRException {
        if ((conceptId != null) && (equivalentConceptId != null))
            equivalentConcepts.put(conceptId, equivalentConceptId);
    }
    
    HashMap getEquivalentConcepts(){
        //getSemanticequivalence
        return equivalentConcepts;
    }
    
    /**
     * Get the default user-defined postal scheme for codifying the
     * attributes of PostalAddress.
     *
     * Default not here yet.
     *
     */
    void setDefaultPostalScheme(){
        if (defaultPostalSchemeId == null) {
            defaultPostalSchemeId = connection.getDefaultPostalAddressScheme();
        }
    }
    
    public ClassificationScheme getDefaultPostalScheme()
    throws JAXRException {
        defaultPostalSchemeId = connection.getDefaultPostalAddressScheme();
        if (defaultPostalSchemeId == null)
            logger.finest(" defaultPostalSchemeId is null");
        if (uddiMapper == null)
            getUDDIMapper();
        if (uddiMapper != null) {
            ClassificationScheme defaultScheme =
                    uddiMapper.getClassificationSchemeById(defaultPostalSchemeId);
            return defaultScheme;
        }
        return null;
    }
    
    
    /**
     * Store responses for asynchronous calls.
     *
     * @param response The BulkResponse to store.
     */
    void storeBulkResponse(BulkResponse response) {
        try {
            bulkResponses.put(response.getRequestId(), response);
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest("Storing response with id: " +
                        response.getRequestId());
            }
        } catch (JAXRException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    /**
     * Returns the BulkResponse associated with specified requestId.
     * Throws JAXRException if no responses exist for specified requestId.
     */
    public BulkResponse getBulkResponse(String requestId) throws JAXRException {
        Object response = bulkResponses.remove(requestId);
        if (response == null) {
            throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryServiceImpl:No_response_exists_for_specified_requestId"));
        }
        return (BulkResponse) response;
    }
    
    
    public ConnectionImpl getConnection() {
        return connection;
    }
    
    
    public SOAPBody jaxmSend(SOAPMessage msg, boolean secure) throws JAXRException {
        
        
        
        SOAPBody resultNode = null;
        try {
            
            // add proxy info if needed
            final String proxyHost = getConnection().getHttpProxyHost();
            final String proxyPort = getConnection().getHttpProxyPort();
            final String proxyUserName = getConnection().getProxyUserName();
            final String proxyPassword = getConnection().getProxyPassword();
            final String sslProxyHost = getConnection().getHttpsProxyHost();
            final String sslProxyPort = getConnection().getHttpsProxyPort();
            
            if ((proxyHost != null) && (!proxyHost.equals("")) &&
                    (proxyPort != null) && (!proxyPort.equals(""))) {
                
                AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        Properties sysProps = System.getProperties();
                        sysProps.put("http.proxyHost", proxyHost);
                        sysProps.put("http.proxyPort", proxyPort);
                        return null;
                    }
                });
                
                if (logger.isLoggable(Level.FINEST)) {
                    logger.finest("proxy host = " + proxyHost);
                    logger.finest("proxy port = " + proxyPort);
                }
            }
            
            
            if ((sslProxyHost != null) && (!sslProxyHost.equals("")) &&
                    (sslProxyPort != null) && (!sslProxyPort.equals(""))) {
                AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        Properties sysProps = System.getProperties();
                        sysProps.put("https.proxyHost", sslProxyHost);
                        sysProps.put("https.proxyPort", sslProxyPort);
                        return null;
                    }
                });
                
                if (logger.isLoggable(Level.FINEST)) {
                    logger.finest("https proxy host = " + sslProxyHost);
                    logger.finest("https proxy port = " + sslProxyPort);
                }
            }
            
            
            final SOAPConnectionFactory scf = (SOAPConnectionFactory)
            AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    try {
                        return SOAPConnectionFactory.newInstance();
                    } catch (javax.xml.soap.SOAPException se) {
                        se.printStackTrace();
                    }
                    return null;
                }
            });
            
            SOAPConnection soapConnection = (SOAPConnection)
            AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    try {
                        return scf.createConnection();
                    } catch (javax.xml.soap.SOAPException se) {
                        se.printStackTrace();
                    }
                    return null;
                }
            });
            
            // add proxy authentication info if needed
            if ((proxyUserName != null) && (proxyPassword != null)) {
                final String fProxyUserName = proxyUserName;
                final String fProxyPassword = proxyPassword;
                String authentication = (String)
                AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        return "Basic " +
                                Base64.getEncoder().encodeToString((fProxyUserName + ":" + fProxyPassword).getBytes());
                    }
                });
                msg.getMimeHeaders().setHeader("Proxy-Authorization", authentication);
            }
            
            // using 'secure' to determine url
            String urlString = null;
            if (secure) {
                if (!securitySet) {
                    securitySet = true;
                }
                
                urlString = connection.getLifeCycleManagerURL();
                //n"Using LifeCycle url");
            } else {
                urlString = connection.getQueryManagerURL();
                
            }
            msg.saveChanges();
            // make the call
            MarshallerUtil.getInstance().log(msg);
            URL url = new URL(urlString);
            SOAPMessage reply = soapConnection.call(msg, url);
            
            MarshallerUtil.getInstance().log(reply);
            resultNode = reply.getSOAPBody();
            soapConnection.close();
            
        } catch (Exception e) {
            
            logger.log(Level.FINEST, e.getMessage(), e);
            throw new JAXRException(e);
        }
        
        
        return resultNode;
    }
    
    
    /**
     * Currently, default is to use jaxm. Can use soap4j by
     * setting "useSOAP" property to true on connection
     * factory or with -DuseSOAP=true for all connections.
     * This is temporary while we're working on jaxm issues.
     */
    
    public Node send(SOAPMessage doc, boolean secure) throws JAXRException{
        
        boolean useSOAP = getConnection().useSOAP();
        try {
            String useSoapString = (String)
            AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    return System.getProperty("useSOAP");
                }
            });
            if ((useSoapString != null) && (useSoapString.equalsIgnoreCase("true"))) {
                useSOAP = true;
            }
        } catch (Throwable t) {
            logger.finest("Ignoring error checking for system useSOAP property: " + t);
        }
        if (useSOAP) { //if useSoap
            logger.fine("External Soap no longer used");
        }
        
        return jaxmSend(doc, secure);
        
    }
    
    /*
     * use for debugging -- pass it a node and it prints
     * all the contents
     */
    private void printNode(Node node) {
        
        NodeList list = node.getChildNodes();
        int length = list.getLength();
        for (int i=0; i<length; i++) {
            printNode(list.item(i));
        }
    }
    
    public String getCurrentUser(){
        ConnectionImpl con = this.getConnection();
        if (con != null)
            return con.getCurrentUser();
        return null;
    }
    
}
