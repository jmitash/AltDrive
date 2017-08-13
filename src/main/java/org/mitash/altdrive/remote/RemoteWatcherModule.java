package org.mitash.altdrive.remote;

import com.google.inject.AbstractModule;
import org.mitash.altdrive.logger.LoggerModule;

/**
 * @author jacob
 */
public class RemoteWatcherModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new LoggerModule());
        bind(RemoteWatcher.class).to(RemoteWatcherImpl.class);
    }
}
