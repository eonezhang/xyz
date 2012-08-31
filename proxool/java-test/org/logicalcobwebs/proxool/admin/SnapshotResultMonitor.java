/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool.admin;

import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.ResultMonitor;

/**
 * A ResultMonitor specifically for Snapshots
 *
 * @version $Revision: 1.4 $, $Date: 2003/03/03 11:12:05 $
 * @author bill
 * @author $Author: billhorsman $ (current maintainer)
 * @since Proxool 0.8
 */
public abstract class SnapshotResultMonitor extends ResultMonitor {

    private SnapshotIF snapshot;

    private String alias;

    /**
     * @param alias so we can lookup the latest {@link SnapshotIF snapshot}
     */
    public SnapshotResultMonitor(String alias) {
        this.alias = alias;
    }

    /**
     * Passes the latest snapshot to {@link #check(org.logicalcobwebs.proxool.admin.SnapshotIF) check}.
     * @return {@link #SUCCESS} or {@link #TIMEOUT}
     * @throws Exception if anything goes wrong
     */
    public boolean check() throws Exception {
        snapshot = ProxoolFacade.getSnapshot(alias);
        return check(snapshot);
    }

    /**
     * Override this with your specific check
     * @param snapshot the latest snapshot
     * @return true if the result has happened, else false
     * @throws Exception if anything goes wrong
     */
    public abstract boolean check(SnapshotIF snapshot) throws Exception;

    /**
     * Get the snapshot used in the most recent {@link #check(org.logicalcobwebs.proxool.admin.SnapshotIF) check}
     * @return snapshot
     */
    public SnapshotIF getSnapshot() {
        return snapshot;
    }

}


/*
 Revision history:
 $Log: SnapshotResultMonitor.java,v $
 Revision 1.4  2003/03/03 11:12:05  billhorsman
 fixed licence

 Revision 1.3  2003/03/01 15:27:24  billhorsman
 checkstyle

 Revision 1.2  2003/03/01 15:22:50  billhorsman
 doc

 Revision 1.1  2003/03/01 15:14:14  billhorsman
 new ResultMonitor to help cope with test threads

 */