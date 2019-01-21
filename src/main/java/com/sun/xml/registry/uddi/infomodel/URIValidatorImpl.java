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

package com.sun.xml.registry.uddi.infomodel;

import java.net.*;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

/**
 * Implementation of URIValidator. This class is used as a
 * delegate in other classes that implement URIValidator.
 * Declared Serializable here so that it can be serialized
 * with the objects that use it.
 *
 * @author Bobby Bissett
 */
public class URIValidatorImpl implements URIValidator, Serializable {

	private boolean validateURI = false;

    /**
     * Getter for validateURI
     */
    public boolean getValidateURI() {
	return validateURI;
    }

    /**
     * Setter for validateURI
     */
    public void setValidateURI(boolean validate) {
	validateURI = validate;
    }

    /**
     * Attemptes to validate a given uri. Throws exception
     * if invalid.
     */
    void validate(String uri) throws InvalidRequestException {
	if (validateURI == false) {
	    return;
	}

	URL url = null;
	try {
	    url = new URL(uri);
	}
	catch(MalformedURLException e) {
	    throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Malformed_URL_Exception:_") + uri, e);
	}

	// only try to resolve http urls
	if (url.getProtocol().equalsIgnoreCase("http")) {

	    try {
		HttpURLConnection connection =
		    (HttpURLConnection) url.openConnection();
		int responseCode = connection.getResponseCode();
                
                if ( responseCode == 404 ) {
                    throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Received_response_code_") + responseCode + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:_for_uri_") + uri);
                }
                
		if ((responseCode < 200) || (responseCode > 302) && (responseCode != 400)) {
		    throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Received_response_code_") + responseCode + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:_for_uri_") + uri);
		}
	    } catch (UnknownHostException uhe) {
		throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Make_sure_your_proxies_are_set._Received_error:_") + uhe, uhe);
	    } catch (Exception e) {
		throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Could_not_validate_") + uri + ":" + e, e);
	    }
	} 
    }
}
