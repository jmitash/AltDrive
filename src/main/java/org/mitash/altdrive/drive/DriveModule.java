package org.mitash.altdrive.drive;

import com.google.inject.AbstractModule;

/**
 * Module with bindings necessary for the Google Drive, AltDrive, and their implementations
 * @author jacob
 */
public class DriveModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AltDrive.class).to(GoogleDrive.class);
    }

}
