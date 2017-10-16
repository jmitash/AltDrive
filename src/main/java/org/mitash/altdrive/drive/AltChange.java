package org.mitash.altdrive.drive;

/**
 * Represents a single change in a change list.
 * @author Jacob Mitash
 */
public interface AltChange {

    /**
     * Gets the ID of the file that was changed
     * @return file ID
     */
    String getFileId();

    /**
     * Tells if the file was removed (deleted or loss of access).
     * @return true if removed, otherwise null or false
     * TODO: verify documentation is correct
     */
    Boolean isRemoved();
}
