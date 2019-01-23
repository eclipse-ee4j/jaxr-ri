This is the JAXR-RI'Reference Implementation' project.



Tasks required before building the project.

    1)User should have jdk 1.8 or above on this machine.
    2)user should have Maven 3 installed on his machine.
 

  Run the following Ant task in the following order:
    
    mvn clean           Clean away generated or copied files by removing build dir.

    mvn install        It will compile the source code and generate the jaxr-Impl.jar file in
                    ./build/lib dir.
    
    mvn install -Poss-release         It will generate the javadocs and sources along with binary for this project.


