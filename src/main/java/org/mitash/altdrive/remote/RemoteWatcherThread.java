package org.mitash.altdrive.remote;

import org.mitash.altdrive.drive.AltChange;
import org.mitash.altdrive.drive.AltChangeList;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.event.Event;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.event.Listener;
import org.mitash.altdrive.logger.LoggerFactory;
import org.mitash.altdrive.properties.PropertyChangedEvent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Waits for changes in the remote Drive and fires events when they occur.
 * @author jacob
 */
public class RemoteWatcherThread extends Thread implements Listener {

    private final static Logger LOGGER = LoggerFactory.build(RemoteWatcherThread.class);

    private final AltDrive altDrive;
    private final EventPublisher eventPublisher;

    private final AtomicLong interval;

    /**
     * Initializes the thread as a daemon and sets the thread name.
     * @param altDrive the Drive to watch
     * @param eventPublisher an event publisher
     */
    RemoteWatcherThread(AltDrive altDrive, EventPublisher eventPublisher, long initialInterval) {
        super("RemoteWatcher");
        this.setDaemon(true);

        this.altDrive = altDrive;
        this.eventPublisher = eventPublisher;
        this.interval = new AtomicLong(initialInterval);
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

        while (!this.isInterrupted() && !AltDrive.ERROR_STRING.equals(savedStartPageToken)) {
            LOGGER.finest("Pulling changes");

            String pageToken = savedStartPageToken;
            while (pageToken != null) {
                AltChangeList changes = altDrive.getChanges(pageToken);
                for (AltChange change : changes.getChanges()) {
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
                sleep(interval.get());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * Reassigns the interval to the new value
     * @param event the event that occurred
     */
    @Override
    public void eventOccurred(Event event) {
        interval.set((long) ((PropertyChangedEvent) event).getNewValue());
    }

    /**
     * Tells if the event is the interval property change event
     * @param event the event to potentially handle
     * @return true if the event is a <code>PropertyChangedEvent</code> and is the interval property
     */
    @Override
    public boolean canHandleEvent(Event event) {
        return event instanceof PropertyChangedEvent && ((PropertyChangedEvent) event).getProperty().equals("remote.watcher.interval");
    }
}
