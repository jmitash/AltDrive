package org.mitash.altdrive.remote;

import com.google.inject.AbstractModule;
import org.mitash.altdrive.drive.DriveModule;
import org.mitash.altdrive.logger.LoggerModule;

/**
 * This module binds the necessary resources for remote watching.
 * @author jacob
 */
public class RemoteWatcherModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new LoggerModule());
        install(new DriveModule());
        bind(RemoteWatcher.class).to(RemoteWatcherImpl.class);
    }
}
