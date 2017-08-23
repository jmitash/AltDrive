package org.mitash.altdrive.remote;

import com.google.inject.Inject;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.logger.ADLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link RemoteWatcher}.
 * @author jacob
 */
public class RemoteWatcherImpl implements RemoteWatcher {

    @ADLogger
    private Logger logger;

    private final AltDrive altDrive;
    private final EventPublisher eventPublisher;
    private RemoteWatcherThread watcherThread;

    /**
     * Sets the <code>AltDrive</code> to be watched.
     * @param altDrive the <code>AltDrive</code> to watch
     */
    @Inject
    public RemoteWatcherImpl(AltDrive altDrive, EventPublisher eventPublisher) {
        this.altDrive = altDrive;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void startWatching() {
        this.watcherThread = new RemoteWatcherThread(altDrive, eventPublisher);
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
