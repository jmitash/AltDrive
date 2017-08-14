package org.mitash.altdrive.remote;

import com.google.inject.Inject;
import org.mitash.altdrive.drive.AltDrive;

/**
 * Default implementation of {@link RemoteWatcher}.
 * @author jacob
 */
public class RemoteWatcherImpl implements RemoteWatcher {

    private final AltDrive altDrive;
    private RemoteWatcherThread watcherThread;

    /**
     * Sets the <code>AltDrive</code> to be watched.
     * @param altDrive the <code>AltDrive</code> to watch
     */
    @Inject
    public RemoteWatcherImpl(AltDrive altDrive) {
        this.altDrive = altDrive;
    }

    @Override
    public void startWatching() {
        this.watcherThread = new RemoteWatcherThread(altDrive);
        this.watcherThread.start();
    }
}
