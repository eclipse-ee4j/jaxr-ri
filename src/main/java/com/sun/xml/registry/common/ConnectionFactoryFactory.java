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

/*
 * ConnectionFactoryFactory.java
 *
 * Created on November 12, 2001, 3:17 PM
 */

package com.sun.xml.registry.common;

import java.util.Hashtable;
import javax.naming.*;
import javax.naming.spi.*;

/**
 *
 * @author  Forte 4 Java
 */
public class ConnectionFactoryFactory implements ObjectFactory {

    public Object getObjectInstance(Object obj, Name name, Context context,
        Hashtable hashtable) throws Exception {
            if (obj instanceof Reference) {
                
                // use this later if conn factory has state
                Reference ref = (Reference) obj;
                return new ConnectionFactoryImpl();
            } else {
                return null;
            }
    }    
    
}
