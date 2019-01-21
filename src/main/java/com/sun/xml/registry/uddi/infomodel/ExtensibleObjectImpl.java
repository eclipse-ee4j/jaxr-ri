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

import java.util.*;
import java.io.Serializable;

/**
 * Implementation of ExtensibleObject interface.
 *
 * Class contains a java.util.HashMap that maps names to
 * slots. Unless specified for a given method, the behavior for 
 * null names and slots is the same as for
 * null keys and objects in HashMap.
 *
 * @author Bobby Bissett
 */
public class ExtensibleObjectImpl implements ExtensibleObject, Serializable {

    private HashMap slots;
    
    /**
     * Creates new ExtensibleObjectImpl
     */
    public ExtensibleObjectImpl() {
        slots = new HashMap();
    }

    /**
     * Add a single slot to the map. The map key is slot.getName()
     * The slot cannot be null.
     */
    public void addSlot(Slot slot) throws JAXRException {
	if (slot == null) {
	    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ExtensibleObjectImpl:Slot_cannot_be_null"));
	}
	slots.put(slot.getName(), slot);
    }

    /**
     * Add multiple slots. Null param is treated as an
     * empty collection.
     */
    public void addSlots(Collection slots) throws JAXRException {
	if (slots == null) {
	    return;
	}
	Iterator iter = slots.iterator();
	try {
	    while (iter.hasNext()) {
		addSlot((Slot) iter.next());
	    }
	} catch (ClassCastException e) {
	    throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ExtensibleObjectImpl:Objects_in_collection_must_be_Slots"), e);
	}
    }
    
    /**
     * Get single slot from map.
     */
    public Slot getSlot(String slotName) throws JAXRException {
	return (Slot) slots.get(slotName);
    }

    /**
     * Returns all slots.
     */
    public Collection getSlots() throws JAXRException {
	return new ArrayList(slots.values());
    }
    
    /**
     * Remove single slot from the map.
     */
    public void removeSlot(java.lang.String slotName) throws JAXRException {
	slots.remove(slotName);
    }
    
    /**
     * Remove all slots with the given names. Null param is treated
     * as an empty collection.
     */
    public void removeSlots(Collection slotNames) throws JAXRException {
	if (slotNames == null) {
	    return;
	}
	Iterator iter = slotNames.iterator();
	try {
	    while (iter.hasNext()) {
		removeSlot((String) iter.next());
	    }
	} catch (ClassCastException e) {
	    throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ExtensibleObjectImpl:Objects_in_collection_must_be_Strings"), e);
	}
    }
    
}
