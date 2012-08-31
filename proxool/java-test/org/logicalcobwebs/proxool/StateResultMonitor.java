/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A ResultMonitor specifically for State
 *
 * @version $Revision: 1.4 $, $Date: 2006/01/18 14:40:06 $
 * @author bill
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.8
 */
public class StateResultMonitor extends ResultMonitor implements StateListenerIF {

    private static final Log LOG = LogFactory.getLog(StateResultMonitor.class);

    private int upState;

    private int expectedUpState;

    /**
     * waits for statistics
     * @return {@link #SUCCESS} or {@link #TIMEOUT}
     * @throws Exception if anything goes wrong
     */
    public boolean check() throws Exception {
        return (upState == expectedUpState);
    }

    public void upStateChanged(int upState) {
        this.upState = upState;
    }

    public void setExpectedUpState(int expectedUpState) {
        this.expectedUpState = expectedUpState;
    }

}


/*
 Revision history:
 $Log: StateResultMonitor.java,v $
 Revision 1.4  2006/01/18 14:40:06  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.3  2003/03/04 10:24:40  billhorsman
 removed try blocks around each test

 Revision 1.2  2003/03/03 11:12:05  billhorsman
 fixed licence

 Revision 1.1  2003/03/02 00:37:23  billhorsman
 more robust

 Revision 1.5  2003/03/01 18:17:50  billhorsman
 arrffgh. fix,

 Revision 1.4  2003/03/01 16:53:07  billhorsman
 fix

 Revision 1.3  2003/03/01 16:38:40  billhorsman
 fix

 Revision 1.2  2003/03/01 16:18:31  billhorsman
 fix

 Revision 1.1  2003/03/01 16:07:26  billhorsman
 helper

 Revision 1.3  2003/03/01 15:27:24  billhorsman
 checkstyle

 Revision 1.2  2003/03/01 15:22:50  billhorsman
 doc

 Revision 1.1  2003/03/01 15:14:14  billhorsman
 new ResultMonitor to help cope with test threads

 */