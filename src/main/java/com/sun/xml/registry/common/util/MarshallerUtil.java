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

import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Declaration.
 * @see
 * @author Farrukh S. Najmi
 * @author Kathy Walsh
 * @version   1.2, 05/02/00
 */
public class MarshallerUtil {

    Logger logger = Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".common");

    private static MarshallerUtil  instance = null;
    private String jaxrHome=null;
    private JAXBContext jc;

    /**
     * Class Constructor.
     *
     *
     * @see
     */
    protected MarshallerUtil() throws JAXBException{
        // create a JAXBContext
       jc =
          JAXBContext.newInstance( "com.sun.xml.registry.uddi.bindings_v2_2" );
    }

    /**
     * Method Declaration.
     *
     *
     * @return
     *
     * @see
     */
    public static MarshallerUtil getInstance() throws JAXBException{
        if (instance == null) {
            synchronized (MarshallerUtil.class) {
                if (instance == null) {
                    instance = new MarshallerUtil();
                }
            }
        }
        return instance;
    }

     // create a Marshaller and marshal to System.out
     //public Document jaxbMarshalObject(Object obj)
     public SOAPMessage jaxbMarshalObject(Object obj)
             throws JAXBException {

         SOAPMessage msg = null;
         Marshaller m = jc.createMarshaller();

         try {
             MessageFactory msgFactory = MessageFactory.newInstance();
             msg = msgFactory.createMessage();
			 msg.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
             m.marshal(obj, msg.getSOAPBody());
         } catch (SOAPException se) {
             throw new JAXBException(se);
         }
         return  msg;
     }

   public Object jaxbUnmarshalInputStream(InputStream result)throws JAXBException{

       Unmarshaller u = jc.createUnmarshaller();
       Object o = u.unmarshal( result );
       return o;
    }

    public OutputStream jaxbMarshalOutStream(Object result)throws JAXBException{

       ByteArrayOutputStream outStream = new ByteArrayOutputStream();
       Marshaller u = jc.createMarshaller();
       u.marshal( result, outStream);
 
       return outStream;
    }

     public Object jaxbUnmarshalObject(Node result)throws JAXBException{

       Unmarshaller u = jc.createUnmarshaller();
       Object o = u.unmarshal(result );
       return o;
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

    public void log(SOAPMessage msg) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            msg.writeTo(outStream);
        } catch (SOAPException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        log(outStream);
		
    }

    private  void log(ByteArrayOutputStream outStream){
        String cmd = outStream.toString();
        if (logger.isLoggable(Level.FINEST)){
            if (cmd.indexOf("get_authToken") != -1)
                logger.finest(XMLUtil.getInstance().authToken2XXX(cmd));
            else if (cmd.indexOf("authInfo") != -1)
                logger.finest(XMLUtil.getInstance().authInfo2XXX(cmd));
            else
                logger.finest(cmd);
        }
    }

}
