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
 * The Query interface encapsulates a query in a declarative query language.
 * Currently a Query can be an SQL query only.
 * In future support for other query languages such as XQL query may be added. 
 * The query must conform to a fixed schema as defined by the JAXR specification.
 *
 * @author Farrukh S. Najmi
 */
public class QueryImpl {

    /**
	 * Gets the type of Query (e.g. QUERY_TYPE_SQL)
	 *
	 *
	 * <p><DL><DT><B>Capability Level: 1 </B></DL> 	 
	 *
	 * @see Query#QUERY_TYPE_SQL
	 * @see Query#QUERY_TYPE_XQUERY
	 * @return the type of query
	 */
	public int getType() throws JAXRException {
		return 0;
	}

	/**
	 * Must print the String representing the query. For example
	 * in case of SQL query prints the SQL query as a string.
	 *
	 * <p><DL><DT><B>Capability Level: 1 </B></DL> 	 
	 *
	 */	
	public String toString() {
		return null;
	}
}
