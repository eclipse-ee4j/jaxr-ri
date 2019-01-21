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

import javax.xml.registry.*;

/**
 * Class Declaration for Class1
 * @see
 * @author Farrukh S. Najmi
 */
public class SecurityManagerImpl {
    /**
     * Adds the authentication tokens (information) to the specified request
	 * as required by the spefic target registry provider. The request is a 
	 * registry provider specific object.
	 *
	 * @return Object that is the modified request with the authentication info added.
     */
    public Object addAuthenticationTokens(Object request) throws JAXRException{
        // Write your code here
        return null;
    }

    /**
     * Digitally sign the specified request
     * as required by the spefic target registry provider. The request is a 
     * registry provider specific object.
     *
     * @return Object that is the modified request with the digital signature added.
     */
    public Object signRequest(Object request) throws JAXRException{
        // Write your code here
        return null;
    }

    /**
     * Validate the digitally signature in the specified response.
     * The reponse is a registry provider specific object.
	 * Throws an InvalidObjectException??
     *
     */
    public void validateRequest(Object response) throws JAXRException{
        // Write your code here
    }

    /**
     * Encrypt the specified request
     * as required by the spefic target registry provider. The request is a 
     * registry provider specific object.
     *
     * @return Object that is the encrypted request.
     */
    public Object encryptRequest(Object request) throws JAXRException{
        // Write your code here
        return null;
    }

    /**
     * Decrypt the specified response
     * The response is a registry provider specific object.
     *
     * @return Object that is the decrypted response.
     */
    public Object decryptRequest(Object request) throws JAXRException{
        // Write your code here
        return null;
    }
}
