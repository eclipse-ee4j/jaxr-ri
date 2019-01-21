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

import java.util.Locale;
import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;
import java.io.Serializable;

/**
 * Implementation of JAXR LocalizedString.
 *
 * @author Bobby Bissett
 */
public class LocalizedStringImpl implements LocalizedString, Serializable {

    private String charsetName;
    private Locale locale;
    private String value;
    
    /**
     * Default constructor
     */
    public LocalizedStringImpl() {
	charsetName = LocalizedString.DEFAULT_CHARSET_NAME;
	locale = Locale.getDefault();
    }

    /**
     * Utility constructor sets locale and value
     *
     * @param locale The locale for this localized string
     * @param value The string value for this localized string
     */
    public LocalizedStringImpl(Locale locale, String value) {
	this();
        this.locale = locale;
        this.value = value;
    }

    /**
     * Get the locale
     */
    public Locale getLocale() throws JAXRException {
	return locale;
    }
    
    /**
     * Set the locale
     */
    public void setLocale(Locale locale) throws JAXRException {
        this.locale = locale;
    }
    
    /**
     * Get the value
     */
    public String getValue() throws JAXRException {
        return value;
    }
    
    /**
     * Set the value
     */
    public void setValue(String value) throws JAXRException {
        this.value = value;
    }
    
    /**
     * Get the charset name
     */
    public String getCharsetName() throws JAXRException {
	return charsetName;
    }
    
    /**
     * Set the charset name
     */
    public void setCharsetName(String charset) throws JAXRException {
        charsetName = charset;
    }
}
