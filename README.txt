#
# Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#

            This is the JAXR-RI'Reference Implementation' project.



Tasks required before building the project.

    1)User should have jdk 1.8 or above on this machine.
    2)user should have Maven 3 installed on his machine.
 

  Run the following Ant task in the following order:
    
    mvn clean           Clean away generated or copied files by removing build dir.

    mvn install        It will compile the source code and generate the jaxr-Impl.jar file in
                    ./build/lib dir.
    
    mvn install -Poss-release         It will generate the javadocs and sources along with binary for this project.


