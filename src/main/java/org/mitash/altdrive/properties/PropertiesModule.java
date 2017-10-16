package org.mitash.altdrive.properties;

import com.google.inject.AbstractModule;
import org.mitash.altdrive.event.EventModule;
import org.mitash.altdrive.logger.LoggerModule;

/**
 * Binds classes necessary for the Properties Module.
 * @author Jacob Mitash
 */
public class PropertiesModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new LoggerModule());
        install(new EventModule());
        bind(PropertyManager.class).to(PropertyManagerImpl.class);
    }
}
