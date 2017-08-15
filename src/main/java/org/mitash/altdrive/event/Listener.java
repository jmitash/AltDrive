package org.mitash.altdrive.event;

import java.lang.reflect.ParameterizedType;

/**
 * Represents an application listener that listens for {@link Event} of type <code>T</code>.
 * @author jacob
 */
public interface Listener<T extends Event> {

    /**
     * Handle the event occurring.
     * @param event the event that occurred
     */
    void eventOccurred(T event);

    /**
     * Gets the class of the event the listener was generated with.
     * @return class of the event
     */
    default Class<?> getEventClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            return Class.forName(className);
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
