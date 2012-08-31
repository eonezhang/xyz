/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.dbscript;

import org.logicalcobwebs.proxool.ProxoolException;

import java.util.Properties;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * An interface that will provide connections. It differs from a real
 * {@link java.sql.Driver Driver} because it has {@link #setup} and
 * {@link #tearDown} methods.
 *
 * @version $Revision: 1.8 $, $Date: 2003/03/03 11:12:03 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.5
 */
public interface ConnectionAdapterIF {

    /**
     * Setup the adapter. Define the connection. Prototype any connections
     * as necessary.
     *
     * @param driver the name of the class
     * @param url the url to pass to the driver
     * @param info the properties to pass to the driver
     * @throws SQLException if anything goes wrong
     */
    void setup(String driver, String url, Properties info) throws SQLException, ProxoolException;

    /**
     * Simply get a connection (using the definitions defined in {@link #setup}
     * @return the connection
     * @throws SQLException if anything goes wrong
     */
    Connection getConnection()
            throws SQLException;

    /**
     * This gives the adapter the flexibilty of closing the connection for real
     * or just putting it back in a pool.
     * @param connection the connection to "close"
     * @throws SQLException if anything goes wrong
     */
    void closeConnection(Connection connection) throws SQLException;

    /**
     * Reclaim resources used by the adapter (for instance, close any
     * open connections)
     */
    void tearDown() throws ProxoolException;

    /**
     * Convenient name so we can identify this adapter in logs.
     * @return name
     */
    String getName();

}

/*
 Revision history:
 $Log: ConnectionAdapterIF.java,v $
 Revision 1.8  2003/03/03 11:12:03  billhorsman
 fixed licence

 Revision 1.7  2003/02/19 15:14:21  billhorsman
 fixed copyright (copy and paste error,
 not copyright change)

 Revision 1.6  2003/01/17 00:38:12  billhorsman
 wide ranging changes to clarify use of alias and url -
 this has led to some signature changes (new exceptions
 thrown) on the ProxoolFacade API.

 Revision 1.5  2002/11/13 20:23:34  billhorsman
 change method name, throw exceptions differently, trivial changes

 Revision 1.4  2002/11/09 15:59:34  billhorsman
 fix doc

 Revision 1.3  2002/11/02 14:22:16  billhorsman
 Documentation

 Revision 1.2  2002/11/02 12:46:42  billhorsman
 improved debug

 Revision 1.1  2002/11/02 11:29:53  billhorsman
 new script runner for testing

*/
