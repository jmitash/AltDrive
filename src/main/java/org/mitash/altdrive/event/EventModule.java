package org.mitash.altdrive.event;

import com.google.inject.AbstractModule;

/**
 * Binds the necessary classes for events.
 * @author jacob
 */
public class EventModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventPublisher.class).to(EventPublisherImpl.class);
    }
}
