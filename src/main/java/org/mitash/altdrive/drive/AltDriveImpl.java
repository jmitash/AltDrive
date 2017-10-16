package org.mitash.altdrive.drive;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ChangeList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mitash.altdrive.logger.LoggerFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Acts as a normal Google Drive.
 * @author jacob
 */
@Singleton
public class AltDriveImpl implements AltDrive {

    private final static Logger LOGGER = LoggerFactory.build(AltDriveImpl.class);

    @Inject
    private GoogleDriveBuilder googleDriveBuilder;

    private Drive drive;

    @Override
    public void initialize() throws IOException {
        drive = googleDriveBuilder.build();
        LOGGER.info("Google Drive built");
    }

    @Override
    public String getStartPageToken() {
        try {
            String startPageToken = drive.changes().getStartPageToken().execute().getStartPageToken();
            LOGGER.info("Fetched start page token");
            return startPageToken;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not get the start page token", e);
            //TODO: publish failure
            return ERROR_STRING;
        }
    }

    @Override
    public AltChangeList getChanges(String pageToken) {
        try {
            ChangeList changeList = drive.changes().list(pageToken).execute();
            LOGGER.finer("Fetched Drive change list");
            return new AltChangeListImpl(changeList);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not get the change list", e);
            //TODO: publish failure
            return null;
        }

    }


}
