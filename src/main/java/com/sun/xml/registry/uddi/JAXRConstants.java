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
import javax.xml.registry.infomodel.*;

import java.util.*;

import com.sun.xml.registry.uddi.infomodel.*;
import com.sun.xml.registry.uddi.*;

/**
 * 
 * @version 1.4, 01/11/00
 * 
 * 
 */

public class JAXRConstants {

    final static String UDDIVERSION = "2.0";
    final static int MAXROWS = 150;
    
    public static final String AUTHORIZED_NAME ="authorizedName";
    static final String OPERATOR = "operator";

     //it really makes sense to search in the registry for this
    static final String RELATIONSHIPS = "uuid:807a2c6a-ee22-470d-adc7-e0424a337c03";
    static final String PARENT_TO_CHILD = "parent-child";
    static final String PEER_TO_PEER = "peer-peer";
    static final String IDENTITY = "identity";
    
    static final String EQUIVALENT_TO = "EquivalentTo";
    static final String RELATES_TO = "RelatedTo";
    static final String HAS_CHILD = "HasChild";
       
    final static String COMPLETE = "status:complete";
    static final String TO_KEY_INCOMPLETE = "status:toKey_incomplete";
    static final String FROM_KEY_INCOMPLETE = "status:fromKey_incomplete";
    
    final static int SOURCE_KEY_MUST_MATCH_SOURCE = 100;
    final static int TARGET_KEY_MUST_MATCH_TARGET = 200;
    final static int SOURCE_KEY_MUST_MATCH_SOURCE_AND_TARGET_KEY_MUST_MATCH_TARGET = 300;
    
    static final String STREETNUMBER = "StreetNumber";
    static final String STREET = "Street";
    static final String CITY = "City";
    static final String STATE = "State";
    static final String POSTALCODE = "PostalCode";
    static final String COUNTRY = "Country";
    
    static final String SAVE = "save";
    static final String FIND = "find";
    static final String DELETE = "delete";
    static final String FAULT = "fault";

    final static int BUFFER_SIZE = 400; //buzkey 41 x2 + keyValue of relationship 255
    
    static final String UDDI_ORG_TYPES_KEY = "uuid:c1acf26d-9672-4404-9d70-39b756e62ab4";
    static final String UDDI_ORG_TYPES_NAME = "uddi-org:types";
    static final String UDDI_CATEGORIZATION = "categorization";    
    static final String UDDI_SPECIFICATION = "specification";   
    static final String UDDI_NAMESPACE = "namespace";  
    static final String UDDI_POSTALADDRESS = "postalAddress";
    static final String UDDI_IDENTIFIER = "identifier";



    static final int MAX_CACHE_SIZE = 150;
    //private static final int MAX_CACHE_SIZE = 2;	

    public JAXRConstants(){	
        
    }

   

}
