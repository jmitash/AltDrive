package org.mitash.altdrive.drive;

import java.io.IOException;

/**
 * AltDrive interface to represent the remote Drive. The purpose of this interface is to separate the Google Drive
 * implementation from the interface so that a test Drive can be injected instead.
 *
 * @author jacob
 */
public interface AltDrive {

    String ERROR_STRING = "-1";

    /**
     * Prepares the <code>AltDrive</code> for use. Must be called before using the <code>AltDrive</code>
     * @throws IOException if an IO error occurred while initializing
     */
    void initialize() throws IOException;

    /**
     * Gets a token that represents the current state of the Drive. This is used for capturing changes within the remote
     * drive. It serves as a reference so that Google knows which changes were not yet captured.
     * @return a token representing the current state of the Drive, or <code>ERROR_STRING</code> if an error occurred
     */
    String getStartPageToken();

    /**
     * Gets the changes from the state represented by <code>pageToken</code>.
     * @param pageToken the token representing the previous state of the drive
     * @return a list of changes that occurred, or null if an error occurred
     */
    AltChangeList getChanges(String pageToken);
}
