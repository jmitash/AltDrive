package org.mitash.altdrive.drive;

import com.google.api.services.drive.Drive;
import com.google.inject.Inject;
import org.mitash.altdrive.logger.ADLogger;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Acts as a normal Google Drive.
 * @author jacob
 */
public class AltDriveImpl implements AltDrive {

    private Drive drive;

    @Inject
    private GoogleDriveBuilder googleDriveBuilder;

    @ADLogger
    private Logger logger;

    @Override
    public void initialize() throws IOException {
        drive = googleDriveBuilder.build();
    }


}
