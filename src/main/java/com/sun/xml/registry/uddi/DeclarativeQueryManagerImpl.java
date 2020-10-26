/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Payara Services Ltd.
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
 * This interface provides the ability to execute declarative queries (e.g. SQL)
 *
 * @author Farrukh S. Najmi
 */
public class DeclarativeQueryManagerImpl extends QueryManagerImpl {
    
    /**
     * Creates a Query object given a queryType (e.g. SQL) and a String
     * that represents a query in the syntax appropriate for queryType.
     * Must throw and InvalidRequestException if the sqlQuery is not valid.
     *
     *
     * <p><DL><DT><B>Capability Level: 1 </B></DL>
     *
     * @see Query#QUERY_TYPE_SQL
     * @see Query#QUERY_TYPE_XQUERY
     */
    public Query createQuery(int queryType, String queryString)
        throws InvalidRequestException, JAXRException {
            throw new UnsupportedCapabilityException();
    }
    
    /**
     * Execute a query as specified by query paramater.
     *
     * <p><DL><DT><B>Capability Level: 1 </B></DL>
     *
     */
    public BulkResponse executeQuery(Query query) throws JAXRException {
        throw new UnsupportedCapabilityException();
    }
    
    /** @link dependency
     * @label uses*/
    /*#CataloguedObject lnkCataloguedObject;*/
    
    /** @link dependency
     * @label processes*/
    /*#Query lnkQuery;*/
}
