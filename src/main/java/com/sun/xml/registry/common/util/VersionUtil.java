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

/**
 * 
 *
 * Provides some version utilities.
 */

public final class VersionUtil implements Version {
    
    /**
     * GetJAX-R full version, like: "JAX-R Reference Implementation 1.0.5 JAXR_RI_JWSDP13_SCF_b03 "
     * 
     * Method getJAXRCompleteVersion.
     * @return String
     */
    public static String getJAXRCompleteVersion() {
        return PRODUCT_NAME + " Version " + VERSION_NUMBER + " Build " + BUILD_TAG_NUMBER;

    }

    /**
     * Method getJAXRVersion.
     * @return String
     */
    public static String getJAXRVersion() {
        return VERSION_NUMBER;
    }

    /**
     * Method getJAXRBuildNumber.
     * @return String
     */
    public static String getJAXRBuildNumber() {
        return BUILD_TAG_NUMBER;
    }

    /**
     * Method getJAXRProductName.
     * @return String
     */
    public static String getJAXRProductName() {
        return PRODUCT_NAME;
    }

   
   
    
}
