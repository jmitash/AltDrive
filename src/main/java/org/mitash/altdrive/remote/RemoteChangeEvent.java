package org.mitash.altdrive.remote;

import org.mitash.altdrive.event.Event;

/**
 * This event is fired when a change in the remote drive is detected.
 * @author jacob
 */
public class RemoteChangeEvent extends Event {

    private final String fileId;

    RemoteChangeEvent(String fileId) {
        this.fileId = fileId;
    }

    /**
     * Gets the file ID of the file that was changed.
     * @return the file ID
     */
    public String getFileId() {
        return fileId;
    }

    @Override
    public String toString() {
        return String.format("RemoteChangeEvent: {fileId:'%s'}", getFileId());
    }
}
