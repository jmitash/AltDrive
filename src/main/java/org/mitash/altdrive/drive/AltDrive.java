package org.mitash.altdrive.drive;

import java.io.IOException;

/**
 * AltDrive interface to represent the remote Drive. The purpose of this interface is to separate the Google Drive
 * implementation from the interface so that a test Drive can be injected instead.
 *
 * @author jacob
 */
public interface AltDrive {

    /**
     * Prepares the <code>AltDrive</code> for use. Must be called before using the <code>AltDrive</code>
     * @throws IOException if an IO error occurred while initializing
     */
    void initialize() throws IOException;
}
