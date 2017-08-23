package org.mitash.altdrive.test;

import org.mitash.altdrive.logger.ADLogger;

import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * This class provides utility methods for test cases.
 * @author jacob
 */
public abstract class TestHelper {

    /**
     * Injects a logger into fields that have the {@link ADLogger} annotation.
     * @param object the object to inject into
     */
    protected void injectLogger(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(field.getAnnotation(ADLogger.class) != null) {
                Logger logger = Logger.getLogger(object.getClass().getName());
                field.setAccessible(true);
                try {
                    field.set(object, logger);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
