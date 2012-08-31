/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Test whether the {@link ConnectionResetter} works.
 *
 * @version $Revision: 1.16 $, $Date: 2006/01/18 14:40:06 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.5
 */
public class ConnectionResetterTest extends AbstractProxoolTest {

    private static final Log LOG = LogFactory.getLog(ConnectionResetterTest.class);

    /**
     * @see junit.framework.TestCase#TestCase
     */
    public ConnectionResetterTest(String s) {
        super(s);
    }

    /**
     * Test whether autoCommit is correctly reset when a connection is
     * returned to the pool.
     */
    public void testAutoCommit() throws Exception {

        String testName = "autoCommit";
        String alias = testName;

        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        ProxoolFacade.registerConnectionPool(url, info);

        Connection c1 = DriverManager.getConnection(url);

        Connection c2 = DriverManager.getConnection(url);


        c1.setAutoCommit(false);
        c1.close();

        c1 = DriverManager.getConnection(url);
        assertTrue("c1.getAutoCommit", c1.getAutoCommit());

        c2.close();
        c1.close();
        assertEquals("connectionCount", 2, ProxoolFacade.getSnapshot(alias).getConnectionCount());

    }

    /**
     * Test connectionCount when we deliberately introduce an exception during connection reset.
     */
    public void testFailedReset() throws Exception {

        try {
            // This is a bit of a hack to force an exception during reset
            ConnectionResetter.setTriggerResetException(true);

            String testName = "failedReset";
            String alias = testName;

            String url = TestHelper.buildProxoolUrl(alias,
                    TestConstants.HYPERSONIC_DRIVER,
                    TestConstants.HYPERSONIC_TEST_URL);
            Properties info = new Properties();
            info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
            info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
            ProxoolFacade.registerConnectionPool(url, info);

            Connection c1 = DriverManager.getConnection(url);
            c1.setAutoCommit(false);
            c1.close();

            assertEquals("connectionCount", 0, ProxoolFacade.getSnapshot(alias).getConnectionCount());
        } finally {
            // Back to normal
            ConnectionResetter.setTriggerResetException(false);
        }

    }

    /**
     * Test whether autoCommit is correctly reset when a connection is
     * returned to the pool.
     */
    public void testReadOnly() throws Exception {

        String testName = "readOnly";
        String alias = testName;

        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        info.setProperty(ProxoolConstants.MAXIMUM_CONNECTION_COUNT_PROPERTY, "2");
        ProxoolFacade.registerConnectionPool(url, info);

        Connection c1 = DriverManager.getConnection(url);
        ;
        Connection c2 = DriverManager.getConnection(url);
        ;

        boolean originalReadOnly = c1.isReadOnly();
        c1.setReadOnly(true);
        c1.close();

        c1 = DriverManager.getConnection(url);
        ;
        assertTrue("readOnly", c1.isReadOnly() == originalReadOnly);

        c2.close();
        c1.close();

    }

}

/*
 Revision history:
 $Log: ConnectionResetterTest.java,v $
 Revision 1.16  2006/01/18 14:40:06  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.15  2005/10/07 08:12:58  billhorsman
 Test deliberate exception durinng reset

 Revision 1.14  2003/03/04 10:58:43  billhorsman
 checkstyle

 Revision 1.13  2003/03/04 10:24:40  billhorsman
 removed try blocks around each test

 Revision 1.12  2003/03/03 17:08:56  billhorsman
 all tests now extend AbstractProxoolTest

 Revision 1.11  2003/03/03 11:12:04  billhorsman
 fixed licence

 Revision 1.10  2003/02/27 18:01:47  billhorsman
 completely rethought the test structure. it's now
 more obvious. no new tests yet though.

 Revision 1.9  2003/02/19 15:14:22  billhorsman
 fixed copyright (copy and paste error,
 not copyright change)

 Revision 1.8  2003/02/06 17:41:02  billhorsman
 now uses imported logging

 Revision 1.7  2002/12/16 17:05:38  billhorsman
 new test structure

 Revision 1.6  2002/12/03 10:53:08  billhorsman
 checkstyle

 Revision 1.5  2002/11/13 20:53:30  billhorsman
 new tests for autoCommit and readOnly

 Revision 1.4  2002/11/12 20:24:12  billhorsman
 checkstyle

 Revision 1.3  2002/11/12 20:18:26  billhorsman
 Made connection resetter a bit more friendly. Now, if it encounters any problems during
 reset then that connection is thrown away. This is going to cause you problems if you
 always close connections in an unstable state (e.g. with transactions open. But then
 again, it's better to know about that as soon as possible, right?

 Revision 1.2  2002/11/09 16:01:21  billhorsman
 fixed CommandFilterIF implementation

 Revision 1.1  2002/11/06 21:08:02  billhorsman
 new ConnectionResetter test

*/
