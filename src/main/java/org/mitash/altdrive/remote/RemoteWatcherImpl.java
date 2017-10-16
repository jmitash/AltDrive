package org.mitash.altdrive.remote;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.logger.ADLogger;
import org.mitash.altdrive.properties.PropertyManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link RemoteWatcher}.
 * @author jacob
 */
@Singleton
public class RemoteWatcherImpl implements RemoteWatcher {

    @ADLogger
    private Logger logger;

    private final AltDrive altDrive;
    private final EventPublisher eventPublisher;
    private RemoteWatcherThread watcherThread;

    private final long defaultInterval;

    /**
     * Sets the <code>AltDrive</code> to be watched.
     * @param altDrive the <code>AltDrive</code> to watch
     * @param propertyManager the application's property manager
     * @param eventPublisher the event publisher to send events
     */
    @Inject
    public RemoteWatcherImpl(AltDrive altDrive, PropertyManager propertyManager, EventPublisher eventPublisher) {
        this.altDrive = altDrive;
        this.eventPublisher = eventPublisher;
        this.defaultInterval = propertyManager.getPropertyAsLong("remote.watcher.interval");
    }

    @Override
    public void startWatching() {
        this.watcherThread = new RemoteWatcherThread(altDrive, eventPublisher, defaultInterval);
        this.watcherThread.start();
    }

    @Override
    public void stopWatching() {
        this.watcherThread.interrupt();
        try {
            this.watcherThread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Interrupted while waiting for watcher thread to shutdown", e);
        }
    }
}
