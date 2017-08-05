package org.mitash.altdrive.logger;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * Binds the {@link LoggerListener}.
 * @author jacob
 */
public class LoggerModule extends AbstractModule {
    @Override
    protected void configure() {
        bindListener(Matchers.any(), new LoggerListener());
    }

}
