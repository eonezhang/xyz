/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.AbstractProxoolTest;
import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolConstants;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.TestConstants;
import org.logicalcobwebs.proxool.TestHelper;

import java.util.Properties;

/**
 * Tests that the programatic configuration of Proxool works.
 *
 * @version $Revision: 1.16 $, $Date: 2006/01/18 14:40:06 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.5
 */
public class ConfiguratorTest extends AbstractProxoolTest {

    private static final Log LOG = LogFactory.getLog(ConfiguratorTest.class);

    private static final String TEST_TABLE = "test";

    /**
     * @see junit.framework.TestCase#TestCase
     */
    public ConfiguratorTest(String name) {
        super(name);
    }

    public void testConfigurator() throws Exception {

        String testName = "configurator";
        String alias = testName;

        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        ProxoolFacade.registerConnectionPool(url, info);

        Properties newInfo = new Properties();
        newInfo.setProperty(ProxoolConstants.PROTOTYPE_COUNT_PROPERTY, "3");
        ProxoolFacade.updateConnectionPool(url, newInfo);

        final ConnectionPoolDefinitionIF cpd = ProxoolFacade.getConnectionPoolDefinition(alias);
        assertNotNull("definition is null", cpd);
        assertEquals("prototypeCount", 3, cpd.getPrototypeCount());

    }

}

/*
 Revision history:
 $Log: ConfiguratorTest.java,v $
 Revision 1.16  2006/01/18 14:40:06  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.15  2003/03/04 10:24:41  billhorsman
 removed try blocks around each test

 Revision 1.14  2003/03/03 17:09:17  billhorsman
 all tests now extend AbstractProxoolTest

 Revision 1.13  2003/03/03 11:12:06  billhorsman
 fixed licence

 Revision 1.12  2003/02/27 18:01:49  billhorsman
 completely rethought the test structure. it's now
 more obvious. no new tests yet though.

 Revision 1.11  2003/02/19 15:14:26  billhorsman
 fixed copyright (copy and paste error,
 not copyright change)

 Revision 1.10  2003/02/07 10:11:35  billhorsman
 fix

 Revision 1.9  2003/02/07 09:35:52  billhorsman
 changed alias for testConfigurator

 Revision 1.8  2003/02/06 17:41:03  billhorsman
 now uses imported logging

 Revision 1.7  2003/01/22 17:35:03  billhorsman
 checkstyle

 Revision 1.6  2003/01/18 15:13:14  billhorsman
 Signature changes (new ProxoolException
 thrown) on the ProxoolFacade API.

 Revision 1.5  2003/01/17 00:38:12  billhorsman
 wide ranging changes to clarify use of alias and url -
 this has led to some signature changes (new exceptions
 thrown) on the ProxoolFacade API.

 Revision 1.4  2002/12/26 11:35:02  billhorsman
 Removed test regarding property configurator.

 Revision 1.3  2002/12/16 17:06:53  billhorsman
 new test structure

 Revision 1.2  2002/12/15 19:41:28  chr32
 Style fixes.

 Revision 1.1  2002/12/15 19:10:49  chr32
 Init rev.

 Revision 1.4  2002/12/04 13:20:11  billhorsman
 ConfigurationListenerIF test

 Revision 1.3  2002/11/09 16:00:45  billhorsman
 fix doc

 Revision 1.2  2002/11/02 13:57:34  billhorsman
 checkstyle

 Revision 1.1  2002/11/02 11:37:48  billhorsman
 New tests

 Revision 1.4  2002/10/29 23:17:38  billhorsman
 Cleaned up SQL stuff

 Revision 1.3  2002/10/27 13:05:02  billhorsman
 checkstyle

 Revision 1.2  2002/10/27 12:03:33  billhorsman
 clear up of tests

 Revision 1.1  2002/10/25 10:41:07  billhorsman
 draft changes to test globalSetup

*/
