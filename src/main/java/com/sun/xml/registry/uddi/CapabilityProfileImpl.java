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
public class CapabilityProfileImpl implements CapabilityProfile {
    protected CapabilityProfileImpl() { }

    public static CapabilityProfileImpl getInstance() {
        if (instance == null) {
            instance = new com.sun.xml.registry.uddi.CapabilityProfileImpl();
        }
        return instance;
    }

    public int getCapabilityLevel() throws JAXRException {
        return 0;
    }
    
    public java.lang.String getVersion() throws javax.xml.registry.JAXRException {
        return "JAXR Version 1.0";
    }
    
    /**
     * @link
     * @shapeType PatternLink
     * @pattern Singleton
     * @supplierRole Singleton factory
     */

    /*# private CapabilityProfileImpl _capabilityProfileImpl; */

    private static CapabilityProfileImpl instance = null;
}
