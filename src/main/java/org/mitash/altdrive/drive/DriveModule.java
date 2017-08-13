package org.mitash.altdrive.drive;

import com.google.inject.AbstractModule;
import org.mitash.altdrive.logger.LoggerModule;

/**
 * Module with bindings necessary for the Google Drive, AltDrive, and their implementations
 * @author jacob
 */
public class DriveModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new LoggerModule());
        bind(AltDrive.class).to(AltDriveImpl.class);
    }

}
