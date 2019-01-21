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

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

import java.io.Serializable;

/**
 * Implementation of Versionable interface. All methods are
 * level 1 methods.
 *
 * @author Bobby Bissett
 */
public class VersionableImpl implements Versionable, Serializable {

    /**
     * Level 1 method, throws UnsupportedCapabilityException
     */
    public int getMajorVersion() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method, throws UnsupportedCapabilityException
     */
    public void setMajorVersion(int majorVersion) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method, throws UnsupportedCapabilityException
     */
    public int getMinorVersion() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method, throws UnsupportedCapabilityException
     */
    public void setMinorVersion(int minorVersion) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method, throws UnsupportedCapabilityException
     */
    public String getUserVersion() throws JAXRException {
	throw new UnsupportedCapabilityException();
    }

    /**
     * Level 1 method, throws UnsupportedCapabilityException
     */
    public void setUserVersion(String userVersion) throws JAXRException {
	throw new UnsupportedCapabilityException();
    }
}
