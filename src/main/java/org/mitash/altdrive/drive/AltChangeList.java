package org.mitash.altdrive.drive;

import java.util.List;

/**
 * Holds a list of {@link AltChange} and their pagination tokens.
 * @author Jacob Mitash
 */
public interface AltChangeList {

    /**
     * Gets the list of changes that occurred
     * @return list of changes
     */
    List<AltChange> getChanges();

    /**
     * Gets the starting page token for the next set of changes. This will be null if there are more next page tokens.
     * @return starting page token
     */
    String getNewStartPageToken();

    /**
     * Gets the next page token of the current set of changes.
     * @return next page token
     */
    String getNextPageToken();
}
