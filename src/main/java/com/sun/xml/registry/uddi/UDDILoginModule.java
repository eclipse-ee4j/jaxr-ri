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

import java.util.*;
import java.io.IOException;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import javax.xml.registry.*;

import java.util.logging.Logger;
import java.util.logging.Level;

public class UDDILoginModule implements LoginModule {

    Logger logger = (Logger)
	AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		return Logger.getLogger(com.sun.xml.registry.common.util.Utility.LOGGING_DOMAIN + ".uddi");
	    }
	});

    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;

    // configurable option
    private boolean debug = false;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    // username and password
    private String username;
    private char[] password;

    // testUser's SamplePrincipal
    private UDDIPrincipal userPrincipal;
    private PasswordAuthentication pa;
    private String authToken;
    private UDDIMapper mapper;

    public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map sharedState, Map options) {
 
	this.subject = subject;
	this.callbackHandler = callbackHandler;
	this.sharedState = sharedState;
	this.options = options;

	// initialize any configured options
	debug = "true".equalsIgnoreCase((String)options.get("debug"));
    }

    public boolean login() throws LoginException {

	// prompt for a username and password
	if (callbackHandler == null)
	    throw new LoginException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDILoginModule:Error:_no_CallbackHandler_available_to_garner_authentication_information_from_the_user"));

	Callback[] callbacks = new Callback[3];
	callbacks[0] = new NameCallback("UDDI Registry username: ");
	callbacks[1] = new PasswordCallback("UDDI Registry password: ", false);
        callbacks[2] = new UDDIMapperCallback();
 
	try {
	    callbackHandler.handle(callbacks);
	    username = ((NameCallback)callbacks[0]).getName();
	    char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
	    if (tmpPassword == null) {
		// treat a NULL password as an empty password
		tmpPassword = new char[0];
	    }
	    password = new char[tmpPassword.length];
	    System.arraycopy(tmpPassword, 0,
			password, 0, tmpPassword.length);
	    ((PasswordCallback)callbacks[1]).clearPassword();
 
            this.mapper = ((UDDIMapperCallback)callbacks[2]).getUDDIMapper();
            
	} catch (java.io.IOException ioe) {
            LoginException le = new LoginException(ioe.toString());
            le.initCause(ioe);
	    throw le;
	} catch (UnsupportedCallbackException uce) {
            LoginException le = new LoginException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDILoginModule:Error:_not_available_to_garner_authentication_information_from_the_user"));
            le.initCause(uce);
	    throw le;
	}

	// print debugging information
	if (debug) {
	    logger.finest("\t\t[SampleLoginModule] " +
				"user entered username: " +
				username);
	    logger.finest("\t\t[SampleLoginModule] " +
				"user entered password: ");
	    for (int i = 0; i < password.length; i++) {
		// append these to string buffer and log
		//logger.finest(password[i]);
	    }
	}

	// get an authentication token
       // UDDIMapper converter = UDDIMapper.getInstance();
        pa = new PasswordAuthentication(username, password);
        
        HashSet credentials = new HashSet();
        credentials.add(pa);
        try {
            authToken = mapper.getAuthorizationToken(credentials);

            succeeded = true;
            return true;
        } catch (JAXRException jex) {
            
	    // authentication failed -- clean out state
	    if (debug)
		logger.finest("\t\t[SampleLoginModule] " +
				"authentication failed");
	    succeeded = false;
	    username = null;
	    for (int i = 0; i < password.length; i++)
		password[i] = ' ';
	    password = null;
            pa = null;
	    throw new FailedLoginException("");
        }
    }

    public boolean commit() throws LoginException {
	if (succeeded == false) {
	    return false;
	} else {
	    // add a Principal (authenticated identity)
	    // to the Subject

	    // assume the user we authenticated is the SamplePrincipal
	    userPrincipal = new UDDIPrincipal(username);
	    if (!subject.getPrincipals().contains(userPrincipal))
		subject.getPrincipals().add(userPrincipal);

	    if (debug) {
		logger.finest("\t\t[SampleLoginModule] " +
				"added SamplePrincipal to Subject");
	    }

            subject.getPrivateCredentials().add(pa);
            subject.getPrivateCredentials().add(authToken);
            
	    // in any case, clean out state
	    username = null;
	    for (int i = 0; i < password.length; i++)
		password[i] = ' ';
	    password = null;

	    commitSucceeded = true;
	    return true;
	}
    }

    public boolean abort() throws LoginException {
	if (succeeded == false) {
	    return false;
	} else if (succeeded == true && commitSucceeded == false) {
	    // login succeeded but overall authentication failed
	    succeeded = false;
	    username = null;
	    if (password != null) {
		for (int i = 0; i < password.length; i++)
		    password[i] = ' ';
		password = null;
	    }
	    userPrincipal = null;
            pa = null;
	} else {
	    // overall authentication succeeded and commit succeeded,
	    // but someone else's commit failed
	    logout();
	}
	return true;
    }

    public boolean logout() throws LoginException {

	subject.getPrincipals().remove(userPrincipal);
	succeeded = false;
	succeeded = commitSucceeded;
	username = null;
	if (password != null) {
	    for (int i = 0; i < password.length; i++)
		password[i] = ' ';
	    password = null;
	}
	userPrincipal = null;
	return true;
    }
}
