package org.mitash.altdrive.event;

import com.google.inject.AbstractModule;
import org.mitash.altdrive.logger.LoggerModule;

/**
 * Binds the necessary classes for events.
 * @author jacob
 */
public class EventModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new LoggerModule());
        bind(EventPublisher.class).to(EventPublisherImpl.class);
    }
}
