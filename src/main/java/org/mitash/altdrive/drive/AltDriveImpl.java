package org.mitash.altdrive.drive;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ChangeList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mitash.altdrive.logger.ADLogger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Acts as a normal Google Drive.
 * @author jacob
 */
@Singleton
public class AltDriveImpl implements AltDrive {

    @ADLogger
    private Logger logger;

    @Inject
    private GoogleDriveBuilder googleDriveBuilder;

    private Drive drive;

    @Override
    public void initialize() throws IOException {
        drive = googleDriveBuilder.build();
        logger.info("Google Drive built");
    }

    @Override
    public String getStartPageToken() {
        try {
            String startPageToken = drive.changes().getStartPageToken().execute().getStartPageToken();
            logger.info("Fetched start page token");
            return startPageToken;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not get the start page token", e);
            //TODO: publish failure
            return ERROR_STRING;
        }
    }

    @Override
    public ChangeList getChanges(String pageToken) {
        try {
            ChangeList changeList = drive.changes().list(pageToken).execute();
            logger.finer("Fetched Drive change list");
            return changeList;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not get the change list", e);
            //TODO: publish failure
            return null;
        }

    }


}
