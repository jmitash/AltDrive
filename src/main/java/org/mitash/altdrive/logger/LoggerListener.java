package org.mitash.altdrive.logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * Listens for objects with the {@link ADLogger} annotation to inject a customized logger.
 * @author jacob
 */
public class LoggerListener implements TypeListener {
    @Override
    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        Class<?> clazz = typeLiteral.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Logger.class &&
                        field.isAnnotationPresent(ADLogger.class)) {
                    typeEncounter.register(new ADLoggerInjector<>(field));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
