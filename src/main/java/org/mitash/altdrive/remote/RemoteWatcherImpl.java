package org.mitash.altdrive.remote;

import com.google.inject.Inject;
import org.mitash.altdrive.drive.AltDrive;

/**
 * @author jacob
 */
public class RemoteWatcherImpl implements RemoteWatcher {

    private final AltDrive altDrive;
    private RemoteWatcherThread watcherThread;

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
