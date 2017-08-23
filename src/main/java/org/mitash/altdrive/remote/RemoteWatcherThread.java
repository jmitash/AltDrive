package org.mitash.altdrive.remote;

import com.google.api.services.drive.model.Change;
import com.google.api.services.drive.model.ChangeList;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.logger.ADLoggerInjector;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Waits for changes in the remote Drive and fires events when they occur.
 * @author jacob
 */
public class RemoteWatcherThread extends Thread {

    private final static Logger LOGGER = ADLoggerInjector.buildLogger(RemoteWatcherThread.class.getName());

    private final AltDrive altDrive;
    private final EventPublisher eventPublisher;

    private final static long DEFAULT_INTERVAL = 5000;
    private static String previousIntervalString = String.valueOf(DEFAULT_INTERVAL);
    private static long previousInterval = DEFAULT_INTERVAL;

    /**
     * Initializes the thread as a daemon and sets the thread name.
     * @param altDrive the Drive to watch
     */
    RemoteWatcherThread(AltDrive altDrive, EventPublisher eventPublisher) {
        super("RemoteWatcher");
        this.setDaemon(true);

        this.altDrive = altDrive;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Starts polling the Drive for changes.
     */
    @Override
    public void run() {
        String savedStartPageToken = altDrive.getStartPageToken();

        if(AltDrive.ERROR_STRING.equals(savedStartPageToken)) {
            //Die quietly
            return;
        }

        while (!this.isInterrupted()) {
            LOGGER.finer("Pulling changes");

            String pageToken = savedStartPageToken;
            while (pageToken != null) {
                ChangeList changes = altDrive.getChanges(pageToken);
                for (Change change : changes.getChanges()) {
                    LOGGER.finer("Received change: " + change.toString());
                    RemoteChangeEvent event = new RemoteChangeEvent(change.getFileId());
                    eventPublisher.queueEvent(event);
                }
                if (changes.getNewStartPageToken() != null) {
                    // Last page, save token for next poll
                    savedStartPageToken = changes.getNewStartPageToken();
                }
                pageToken = changes.getNextPageToken();
            }

            try {
                sleep(getInterval());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private static long getInterval() {
        String interval = System.getProperty("remote.watcher.interval", String.valueOf(DEFAULT_INTERVAL));
        if(interval.equals(previousIntervalString)) {
            return previousInterval;
        }

        //Otherwise we need to process the new value
        long newInterval;
        try {
            newInterval = Long.valueOf(interval);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Could not parse Remote Watcher Interval", e);
            LOGGER.warning("Using default interval: " + DEFAULT_INTERVAL);
            newInterval = DEFAULT_INTERVAL;
        }

        previousIntervalString = interval;
        previousInterval = newInterval;
        return newInterval;
    }
}
