package org.mitash.altdrive.remote;

import com.google.api.services.drive.model.Change;
import com.google.api.services.drive.model.ChangeList;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.logger.ADLoggerInjector;

import java.util.logging.Logger;

/**
 * @author jacob
 */
public class RemoteWatcherThread extends Thread {

    private final static Logger LOGGER = ADLoggerInjector.buildLogger(RemoteWatcherThread.class.getName());

    private final AltDrive altDrive;

    /**
     * Initializes the thread as a daemon and set name
     * @param altDrive the Drive to watch
     */
    RemoteWatcherThread(AltDrive altDrive) {
        super("RemoteWatcher");
        this.setDaemon(true);

        this.altDrive = altDrive;
    }

    /**
     * Starts polling the Drive for changes
     */
    @Override
    public void run() {
        String savedStartPageToken = altDrive.getStartPageToken();

        if(AltDrive.ERROR_STRING.equals(savedStartPageToken)) {
            //Die quietly
            return;
        }

        while (!this.isInterrupted()) {
            LOGGER.fine("Pulling changes");

            String pageToken = savedStartPageToken;
            while (pageToken != null) {
                ChangeList changes = altDrive.getChanges(pageToken);
                for (Change change : changes.getChanges()) {
                    LOGGER.finer("Received change: " + change.toString());
                    //TODO: publish changes
                }
                if (changes.getNewStartPageToken() != null) {
                    // Last page, save token for next poll
                    savedStartPageToken = changes.getNewStartPageToken();
                }
                pageToken = changes.getNextPageToken();
            }

            try {
                sleep(5000);
                //TODO: system property
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
