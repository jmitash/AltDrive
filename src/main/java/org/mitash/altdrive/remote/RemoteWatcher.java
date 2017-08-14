package org.mitash.altdrive.remote;

/**
 * <code>RemoteWatcher</code> is used for watching the remote Drive.
 * @author jacob
 */
public interface RemoteWatcher {

    /**
     * Starts the {@link RemoteWatcherThread} to listen for Drive changes.
     */
    void startWatching();

}
