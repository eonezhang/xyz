/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.dbscript;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Allows you to perform tasks just {@link #beforeCommand before} and just
 * {@link #afterCommand after} a {@link CommandIF command} is executed.
 * You can also {@link #catchException catch} exceptions that happen.
 *
 * @version $Revision: 1.5 $, $Date: 2003/03/03 11:12:03 $
 * @author Bill Horsman (bill@logicalcobwebs.co.uk)
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.5
 */
public interface CommandFilterIF {

    /**
     * Implement this if you want to do something special before each command is run.
     * @param connection the connection being used
     * @param command the command about to be run
     * @return true if the command should be executed, or false to skip the command
     * @throws SQLException if anything goes wrong. This will terminate the script.
     */
    boolean beforeCommand(Connection connection, CommandIF command) throws SQLException;

    /**
     * Implement this if you want to do something special after each command is run
     * but before the connection is closed
     * @param connection the connection being used
     * @param command the command that was run
     * @throws SQLException if anything goes wrong. This will terminate the script.
     */
    void afterCommand(Connection connection, CommandIF command) throws SQLException;

    /**
     * Any SQLException will be passed to this method.
     * @param e the exception
     * @return true if execution should continue, false if it should stop (including any remaining executions in the loop)
     */
    boolean catchException(CommandIF command, SQLException e);

}

/*
 Revision history:
 $Log: CommandFilterIF.java,v $
 Revision 1.5  2003/03/03 11:12:03  billhorsman
 fixed licence

 Revision 1.4  2003/02/19 15:14:19  billhorsman
 fixed copyright (copy and paste error,
 not copyright change)

 Revision 1.3  2002/11/09 15:58:37  billhorsman
 fix and added doc

 Revision 1.2  2002/11/09 14:45:07  billhorsman
 now threaded and better exception handling

 Revision 1.1  2002/11/06 21:07:42  billhorsman
 New interfaces to allow filtering of commands

*/
