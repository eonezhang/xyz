/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Waits for a set of results to become true with timeout
 * functionality
 *
 * @version $Revision: 1.10 $, $Date: 2006/01/18 14:40:06 $
 * @author bill
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.8
 */
public abstract class ResultMonitor {

    private static final Log LOG = LogFactory.getLog(ResultMonitor.class);

    /**
     * This monitor is still waiting for the result to come true
     */
    public static final int WAITING = 0;

    /**
     * The result has happened
     */
    public static final int SUCCESS = 1;

    /**
     * There was a timeout waiting for the result to happen
     * @see #setTimeout
     */
    public static final int TIMEOUT = 3;

    /**
     * Seems awfully long, but it seems to need it. Sometimes.
     */
    private long timeout = 60000;

    private int result = WAITING;

    private int delay = 500;

    /**
     * Override this with your specific check
     * @return true if the result has happened, else false
     * @throws Exception if anything goes wrong
     */
    public abstract boolean check() throws Exception;

    /**
     * Wait for the result to happen, or for a timeout
     * @return {@link #SUCCESS} or {@link #TIMEOUT}
     * @throws ProxoolException if the {@link #check} threw an exception
     * @see #setTimeout
     */
    public int getResult() throws ProxoolException {

        try {
            long startTime = System.currentTimeMillis();
            if (check()) {
                result = SUCCESS;
            }
            while (true) {
                if (System.currentTimeMillis() - startTime > timeout) {
                    result = TIMEOUT;
                    LOG.debug("Timeout");
                    break;
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    LOG.error("Awoken", e);
                }
                if (check()) {
                    result = SUCCESS;
                    LOG.debug("Success");
                    break;
                }
            }
            return result;
        } catch (Exception e) {
            throw new ProxoolException("Problem monitoring result", e);
        }
    }

    /**
     * Set the timeout
     * @param timeout milliseconds
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}


/*
 Revision history:
 $Log: ResultMonitor.java,v $
 Revision 1.10  2006/01/18 14:40:06  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.9  2003/03/05 18:44:10  billhorsman
 fix delay and timeout

 Revision 1.8  2003/03/04 10:24:40  billhorsman
 removed try blocks around each test

 Revision 1.7  2003/03/03 11:12:05  billhorsman
 fixed licence

 Revision 1.6  2003/03/02 00:53:38  billhorsman
 increased timeout to 60 sec!

 Revision 1.5  2003/03/01 18:17:51  billhorsman
 arrffgh. fix,

 Revision 1.4  2003/03/01 16:54:20  billhorsman
 fix

 Revision 1.3  2003/03/01 15:27:24  billhorsman
 checkstyle

 Revision 1.2  2003/03/01 15:22:50  billhorsman
 doc

 Revision 1.1  2003/03/01 15:14:15  billhorsman
 new ResultMonitor to help cope with test threads

 */