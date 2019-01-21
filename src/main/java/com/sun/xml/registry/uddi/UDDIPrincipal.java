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

import java.security.Principal;
import java.util.ResourceBundle;

/**
 * <p> This class implements the <code>Principal</code> interface
 * and represents a Sample user.
 *
 * <p> Principals such as this <code>SamplePrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon 
 * the Principals associated with a <code>Subject</code>.
 * 
 * @version 1.4, 01/11/00
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
public class UDDIPrincipal implements Principal, java.io.Serializable {

    /**
     * @serial
     */
    private String name;

    /**
     * Create a UDDIPrincipal with a Sample username.
     *
     * <p>
     *
     * @param name the Sample username for this user.
     *
     * @exception NullPointerException if the <code>name</code>
     *			is <code>null</code>.
     */
    public UDDIPrincipal(String name) {
	if (name == null)
	    throw new NullPointerException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIPrincipal:illegal_null_input"));

	this.name = name;
    }

    /**
     * Return the Sample username for this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return the Sample username for this <code>SamplePrincipal</code>
     */
    public String getName() {
	return name;
    }

    /**
     * Return a string representation of this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return a string representation of this <code>SamplePrincipal</code>.
     */
    public String toString() {
	return("SamplePrincipal:  " + name);
    }

    /**
     * Compares the specified Object with this <code>SamplePrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>SamplePrincipal</code> and the two SamplePrincipals
     * have the same username.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     *		<code>SamplePrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *		<code>SamplePrincipal</code>.
     */
    public boolean equals(Object o) {
	if (o == null)
	    return false;

        if (this == o)
            return true;
 
        if (!(o instanceof UDDIPrincipal))
            return false;
        UDDIPrincipal that = (UDDIPrincipal)o;

	if (this.getName().equals(that.getName()))
	    return true;
	return false;
    }
 
    /**
     * Return a hash code for this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>SamplePrincipal</code>.
     */
    public int hashCode() {
	return name.hashCode();
    }
}
