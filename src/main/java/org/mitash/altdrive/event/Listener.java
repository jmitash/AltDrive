package org.mitash.altdrive.event;

import java.lang.reflect.ParameterizedType;

/**
 * @author jacob
 */
public interface Listener<T extends Event> {

    void eventOccurred(T event);

    default Class<?> getEventClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            return Class.forName(className);
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
