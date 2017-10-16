package org.mitash.altdrive.drive;

import com.google.api.services.drive.model.Change;

/**
 * Wrapper for Google's {@link Change}.
 * @author Jacob Mitash
 */
public class AltChangeImpl implements AltChange {

    private Change change;

    AltChangeImpl(Change change) {
        this.change = change;
    }

    @Override
    public String getFileId() {
        return change.getFileId();
    }

    @Override
    public Boolean isRemoved() {
        return change.getRemoved();
    }
}
