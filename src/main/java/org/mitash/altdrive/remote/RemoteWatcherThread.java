package org.mitash.altdrive.remote;

import com.google.api.services.drive.model.Change;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.logger.ADLoggerInjector;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author jacob
 */
public class RemoteWatcherThread extends Thread {

    private final static Logger LOGGER = ADLoggerInjector.buildLogger(RemoteWatcherThread.class.getName());

    private final AltDrive altDrive;

    RemoteWatcherThread(AltDrive altDrive) {
        super("RemoteWatcher");
        this.setDaemon(true);

        this.altDrive = altDrive;
    }

    @Override
    public void run() {
        String pageToken = altDrive.getStartPageToken();

        if(AltDrive.ERROR_STRING.equals(pageToken)) {
            //Die quietly
            return;
        }

        while (!this.isInterrupted()) {
            while (pageToken != null) {
                List<Change> changes = altDrive.getChangeList(pageToken);

                for(Change change : changes) {
                    LOGGER.info(change.toString());
                }
            }

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
