/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.dbscript;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A thread that can run a single command many times.
 *
 * @version $Revision: 1.7 $, $Date: 2006/01/18 14:40:05 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.5
 */
class Commander implements Runnable {

    private static final Log LOG = LogFactory.getLog(Commander.class);

    private ConnectionAdapterIF adapter;

    private CommandIF command;

    private CommandFilterIF commandFilter;

    private boolean finished = false;

    public Commander(ConnectionAdapterIF adapter, CommandIF commandIF, CommandFilterIF commandFilter) {
        this.adapter = adapter;
        this.command = commandIF;
        this.commandFilter = commandFilter;
    }

    public void run() {

        try {
            boolean keepGoing = true;
            for (int i = 0; i < command.getLoops(); i++) {

                Connection connection = null;

                try {
                    connection = adapter.getConnection();
                    boolean executeCommand = true;
                    if (commandFilter != null) {
                            executeCommand = commandFilter.beforeCommand(connection, command);
                    }

                    if (executeCommand) {
                        execute(connection, command.getSql());
                    }

                    if (commandFilter != null) {
                            commandFilter.afterCommand(connection, command);
                    }

                } catch (SQLException e) {
                    if (commandFilter != null && !commandFilter.catchException(command, e)) {
                        keepGoing = false;
                    }
                    if (command.isIgnoreException()) {
                        // Silent
                    } else if (command.isLogException()) {
                        LOG.debug("Ignoring exception in " + command.getName(), e);
                    } else {
                        LOG.error("Stopping command " + command.getName(), e);
                        keepGoing = false;
                    }
                } finally {
                    try {
                        adapter.closeConnection(connection);
                    } catch (SQLException e) {
                        LOG.error("Closing connection for " + Thread.currentThread().getName(), e);
                    }
                }

                if (!keepGoing) {
                    break;
                }

                // Give other threads a chance. (Hoping this will increase the chances
                // of some sort of conflict)
                Thread.yield();
            }
        } finally {
            finished = true;
        }

    }

    /**
     * Is the thread running
     * @return true if it's finished, else false
     */
    protected boolean isFinished() {
        return finished;
    }

    /**
     * Execute and SQL statement
     * @param connection used to execute statement
     * @param sql the SQL to perform
     * @throws java.sql.SQLException if anything goes wrong
     */
    private static final void execute(Connection connection, String sql) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } finally {
            if (statement != null) {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    LOG.error("Couldn't close statement", e);
                }
            }
        }
    }

}

/*
 Revision history:
 $Log: Commander.java,v $
 Revision 1.7  2006/01/18 14:40:05  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.6  2003/03/03 11:12:03  billhorsman
 fixed licence

 Revision 1.5  2003/02/19 15:14:20  billhorsman
 fixed copyright (copy and paste error,
 not copyright change)

 Revision 1.4  2003/02/06 17:41:02  billhorsman
 now uses imported logging

 Revision 1.3  2003/01/15 00:08:15  billhorsman
 check for commandFilter existence to avoid unnecessary
 log clutter

 Revision 1.2  2002/11/09 15:59:18  billhorsman
 fix doc

 Revision 1.1  2002/11/09 14:45:07  billhorsman
 now threaded and better exception handling

*/
