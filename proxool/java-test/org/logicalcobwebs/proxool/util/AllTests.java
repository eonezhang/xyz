/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool.util;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.logicalcobwebs.proxool.GlobalTest;

/**
 * Run all tests in the util package.
 *
 * @version $Revision: 1.4 $, $Date: 2003/03/03 11:12:07 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.7
 */
public class AllTests {

    /**
     * Create a composite test of all util package tests
     * @return test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ListenerContainerTest.class);

        // create a wrapper for global initialization code.
        TestSetup wrapper = new TestSetup(suite) {
            public void setUp() throws Exception {
                GlobalTest.globalSetup();
            }
        };
        return wrapper;
    }

}

/*
 Revision history:
 $Log: AllTests.java,v $
 Revision 1.4  2003/03/03 11:12:07  billhorsman
 fixed licence

 Revision 1.3  2003/02/19 23:49:16  billhorsman
 fixed doc

 Revision 1.2  2003/02/19 15:14:32  billhorsman
 fixed copyright (copy and paste error,
 not copyright change)

 */
