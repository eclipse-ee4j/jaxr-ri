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
 * This interface holds version information for the whole JAX-R RI.
 *
 * @author JAX-R RI Development Team
 */

public interface Version {

    /**
     * JAX-R RI product name
     */
    public static final String PRODUCT_NAME = "JAXR Standard Implementation";

    /**
     * JAX-R RI version number
     */
    public static final String VERSION_NUMBER = "1.0.5";

    /**
     * JAX-R RI build number
     */
    public static final String BUILD_TAG_NUMBER = "JAXR_RI_JWSDP13_HCF_b07";
}
