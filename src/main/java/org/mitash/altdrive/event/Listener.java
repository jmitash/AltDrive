package org.mitash.altdrive.event;

/**
 * Represents an application listener that listens for an {@link Event} type.
 * @author jacob
 */
public interface Listener {

    /**
     * Handle the event occurring.
     * @param event the event that occurred
     */
    void eventOccurred(Event event);

    /**
     * Tells if the listener can handle the event.
     * @param event the event to potentially handle
     * @return class of the event
     */
    boolean canHandleEvent(Event event);
}
