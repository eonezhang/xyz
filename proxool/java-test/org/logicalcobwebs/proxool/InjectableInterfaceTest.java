/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;

/**
 * Tests whether we can inject a new interface into one of the proxy objects
 * @version $Revision: 1.3 $, $Date: 2006/01/18 14:40:06 $
 * @author <a href="mailto:bill@logicalcobwebs.co.uk">Bill Horsman</a>
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.9
 */
public class InjectableInterfaceTest extends AbstractProxoolTest {

    private static final Log LOG = LogFactory.getLog(InjectableInterfaceTest.class);

    /**
     * @see AbstractProxoolTest
     */
    public InjectableInterfaceTest(String alias) {
        super(alias);
    }

    /**
     * Get a connection and cast it into the appropriate interface
     */
    public void testInjectableConnectionInterface() throws Exception {
        String alias = "injectableConnectionInterface";
        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        info.setProperty(ProxoolConstants.INJECTABLE_CONNECTION_INTERFACE_NAME_PROPERTY, HsqlConnectionIF.class.getName());
        Connection c1 = DriverManager.getConnection(url, info);
        // Can we cast it?
        HsqlConnectionIF hc = (HsqlConnectionIF) c1;
        // TODO - need to test a vendor specific method?
        // Does close() still work?
        hc.close();
        assertTrue("c1.isClosed()", c1.isClosed());
    }

    /**
     * Get a statement and cast it into the appropriate interface
     */
    public void testInjectableStatementInterface() throws Exception {
        String alias = "injectableStatementInterface";
        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        info.setProperty(ProxoolConstants.INJECTABLE_STATEMENT_INTERFACE_NAME_PROPERTY, HsqlStatementIF.class.getName());
        Connection c1 = DriverManager.getConnection(url, info);
        Statement s = c1.createStatement();
        // Can we cast it?
        HsqlStatementIF hs = (HsqlStatementIF) s;
        // TODO : call a vendor specific method?
        // hs.checkClosed();
        // Does close() still work?
        hs.close();
        c1.close();
    }

    /**
     * Get a statement and cast it into the appropriate interface
     */
    public void testInjectablePreparedStatementInterface() throws Exception {
        String alias = "injectablePreparedStatementInterface";
        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        info.setProperty(ProxoolConstants.INJECTABLE_PREPARED_STATEMENT_INTERFACE_NAME_PROPERTY, HsqlPreparedStatementIF.class.getName());
        Connection c1 = DriverManager.getConnection(url, info);
        PreparedStatement ps = c1.prepareStatement(TestConstants.HYPERSONIC_TEST_SQL);
        // Can we cast it?
        HsqlPreparedStatementIF hps = (HsqlPreparedStatementIF) ps;
        // TODO : call a vendor specific method?
        // hps.build();
        // Does close() still work?
        hps.close();
        c1.close();
    }

    /**
     * Get a statement and cast it into the appropriate interface
     */
    public void testInjectableCallableStatementInterface() throws Exception {
        String alias = "injectableCallableStatementInterface";
        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        info.setProperty(ProxoolConstants.INJECTABLE_CALLABLE_STATEMENT_INTERFACE_NAME_PROPERTY, HsqlPreparedStatementIF.class.getName());
        Connection c1 = DriverManager.getConnection(url, info);
        CallableStatement cs = c1.prepareCall(TestConstants.HYPERSONIC_TEST_SQL);
        // Can we cast it? (Note: HSQLDB uses the same class for  both Prepared and Callable statements)
        HsqlPreparedStatementIF hps = (HsqlPreparedStatementIF) cs;
        // TODO - call a vendor specific method?
        // Does close() still work?
        hps.close();
        c1.close();
    }

}
/*
 Revision history:
 $Log: InjectableInterfaceTest.java,v $
 Revision 1.3  2006/01/18 14:40:06  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.2  2004/06/17 21:36:39  billhorsman
 Removed call to private methods. They're going to fail anyway.

 Revision 1.1  2004/06/02 20:59:52  billhorsman
 New injectable interface tests

 */