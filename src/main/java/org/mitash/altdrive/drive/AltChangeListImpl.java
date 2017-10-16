package org.mitash.altdrive.drive;

import com.google.api.services.drive.model.ChangeList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jacob Mitash
 */
public class AltChangeListImpl implements AltChangeList {

    private ChangeList changeList;

    AltChangeListImpl(ChangeList changeList) {
        this.changeList = changeList;
    }

    @Override
    public List<AltChange> getChanges() {
        return changeList.getChanges().stream().map(AltChangeImpl::new).collect(Collectors.toList());
    }

    @Override
    public String getNewStartPageToken() {
        return changeList.getNewStartPageToken();
    }

    @Override
    public String getNextPageToken() {
        return changeList.getNextPageToken();
    }
}
