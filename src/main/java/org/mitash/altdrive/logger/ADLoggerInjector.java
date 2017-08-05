package org.mitash.altdrive.logger;

import com.google.inject.MembersInjector;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Injects a custom logger when a field or parameter has the {@link ADLogger} annotation.
 * @author jacob
 */
public class ADLoggerInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final Logger logger;

    private static Map<String, Logger> cachedLoggers = new HashMap<>();

    /**
     * Finds the respective logger in the cache, or builds and caches it if not available. Prepares the field to be
     * injected.
     * @param field the field to inject
     */
    ADLoggerInjector(Field field) {
        this.field = field;
        field.setAccessible(true);

        Logger logger;
        if((logger = cachedLoggers.get(field.getDeclaringClass().getName())) != null) {
            this.logger = logger;
        } else {
            this.logger = buildLogger(field.getDeclaringClass().getName());
            cachedLoggers.put(field.getDeclaringClass().getName(), this.logger);
        }
    }

    public void injectMembers(T t) {
        try {
            field.set(t, logger);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a logger with the pretty formatter and output stream to <code>System.out</code>.
     * @param className the name of the class to build the logger for
     * @return a logger with custom formatter, output stream, and name
     */
    private static Logger buildLogger(String className) {
        Logger logger = Logger.getLogger(className);
        logger.setUseParentHandlers(false);
        PrettyFormatter prettyFormatter = new PrettyFormatter();
        Handler handler = new ConsoleHandler() {
            @Override
            protected void setOutputStream(OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        };
        handler.setFormatter(prettyFormatter);
        logger.addHandler(handler);

        return logger;
    }
}
