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

import java.io.*;
import java.util.*;
import java.security.Principal;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;

import java.util.logging.Logger;
import java.util.logging.Level;

public class ProtoCallbackHandler implements CallbackHandler {

    Logger logger = (Logger)
	AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		return Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".uddi");
	    }
	});

    UDDIMapper mapper;
    
    public ProtoCallbackHandler(UDDIMapper mapper) {
        this.mapper = mapper;
    }
    
    public UDDIMapper getUDDIMapper() {
        return this.mapper;
    }
    
    public void handle(Callback[] callbacks)
    throws IOException, UnsupportedCallbackException {
      
	for (int i = 0; i < callbacks.length; i++) {
	    if (callbacks[i] instanceof TextOutputCallback) {
      
		// display the message according to the specified type
		TextOutputCallback toc = (TextOutputCallback)callbacks[i];
		switch (toc.getMessageType()) {
		case TextOutputCallback.INFORMATION:
 		    logger.finest(toc.getMessage());
 		    break;
 		case TextOutputCallback.ERROR:
 		    logger.log(Level.SEVERE, ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:ERROR:_") + toc.getMessage());
 		    break;
 		case TextOutputCallback.WARNING:
 		    logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:WARNING:_") + toc.getMessage());
 		    break;
 		default:
 		    throw new IOException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:Unsupported_message_type:_") +
 					toc.getMessageType());
 		}
 
 	    } else if (callbacks[i] instanceof NameCallback) {
  
 		// prompt the user for a username
 		NameCallback nc = (NameCallback)callbacks[i];
  
 		// ignore the provided defaultName
 		System.err.print(nc.getPrompt());
 		System.err.flush();
 		nc.setName((new BufferedReader
			(new InputStreamReader(System.in))).readLine());
 
 	    } else if (callbacks[i] instanceof PasswordCallback) {
  
 		// prompt the user for sensitive information
 		PasswordCallback pc = (PasswordCallback)callbacks[i];
 		System.err.print(pc.getPrompt());
 		System.err.flush();
 		pc.setPassword(readPassword(System.in));
  
            } else if (callbacks[i] instanceof UDDIMapperCallback) {
                UDDIMapperCallback mc = (UDDIMapperCallback)callbacks[i];
                mc.setUDDIMapper(this.mapper);
 	    } else {
 		throw new UnsupportedCallbackException
 			(callbacks[i], ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:Unrecognized_Callback"));
 	    }
	}
    }
   
    // Reads user password from given input stream.
    private char[] readPassword(InputStream in) throws IOException {
	
	char[] lineBuffer;
	char[] buf;
	int i;

	buf = lineBuffer = new char[128];

	int room = buf.length;
	int offset = 0;
	int c;

loop:	while (true) {
 	    switch (c = in.read()) {
 	    case -1:
 	    case '\n':
		break loop;

 	    case '\r':
 		int c2 = in.read();
 		if ((c2 != '\n') && (c2 != -1)) {
 		    if (!(in instanceof PushbackInputStream)) {
 			in = new PushbackInputStream(in);
 		    }
 		    ((PushbackInputStream)in).unread(c2);
 		} else
 		    break loop;

 	    default:
 		if (--room < 0) {
 		    buf = new char[offset + 128];
 		    room = buf.length - offset - 1;
 		    System.arraycopy(lineBuffer, 0, buf, 0, offset);
 		    Arrays.fill(lineBuffer, ' ');
 		    lineBuffer = buf;
 		}
 		buf[offset++] = (char) c;
 		break;
 	    }
 	}

 	if (offset == 0) {
 	    return null;
 	}

 	char[] ret = new char[offset];
 	System.arraycopy(buf, 0, ret, 0, offset);
 	Arrays.fill(buf, ' ');

 	return ret;
    }
}
