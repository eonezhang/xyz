/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.ResultMonitor;

/**
 * A ResultMonitor specifically for Snapshots
 *
 * @version $Revision: 1.10 $, $Date: 2006/01/18 14:40:06 $
 * @author bill
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.8
 */
public class StatisticsResultMonitor extends ResultMonitor {

    private static final Log LOG = LogFactory.getLog(StatisticsResultMonitor.class);

    private StatisticsIF statistics;

    private StatisticsIF oldStatistics;

    private String alias;

    private String token;

    /**
     * @param alias so we can lookup the latest {@link StatisticsIF statistics}
     * @param token so we can lookup the latest {@link StatisticsIF statistics}
     */
    public StatisticsResultMonitor(String alias, String token) {
        this.alias = alias;
        this.token = token;
        setDelay(2000);
    }

    /**
     * waits for statistics
     * @return {@link #SUCCESS} or {@link #TIMEOUT}
     * @throws Exception if anything goes wrong
     */
    public boolean check() throws Exception {
        statistics = ProxoolFacade.getStatistics(alias, token);
        if (statistics == null) {
            return false;
        } else if (oldStatistics == null) {
            return check(statistics);
        } else {
            if (!statistics.getStartDate().equals(oldStatistics.getStartDate())) {
                return check(statistics);
            } else {
                return false;
            }
        }
    }

    /**
     * This gets called when we get new statistics. By overriding this
     * method you get the option of rejecting these new statistics and
     * waiting for the next set
     * @param statistics the newly created statistics
     * @return true if we accept them, false if we want to wait for the next set
     * (if you don't override this it will return true)
     */
    protected boolean check(StatisticsIF statistics) {
        return true;
    }

    public int getResult() throws ProxoolException {
        oldStatistics = statistics;
        return super.getResult();
    }

    /**
     * Get the statistics used in the most recent {@link #check check}
     * @return snapshot
     */
    public StatisticsIF getStatistics() {
        return statistics;
    }
}


/*
 Revision history:
 $Log: StatisticsResultMonitor.java,v $
 Revision 1.10  2006/01/18 14:40:06  billhorsman
 Unbundled Jakarta's Commons Logging.

 Revision 1.9  2003/03/06 22:39:05  billhorsman
 fix

 Revision 1.8  2003/03/06 22:28:32  billhorsman
 another go at statistics threading (in tests)

 Revision 1.7  2003/03/04 10:24:41  billhorsman
 removed try blocks around each test

 Revision 1.6  2003/03/03 11:12:06  billhorsman
 fixed licence

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