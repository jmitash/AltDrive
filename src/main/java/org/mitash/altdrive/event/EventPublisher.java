package org.mitash.altdrive.event;

/**
 * @author jacob
 */
public interface EventPublisher {

    /**
     * Publishes an event to all the listeners that are listening for the specific event type.
     * @param event the event to be published
     */
    void publishEvent(Event event);

    /**
     * Adds a new listener to listen for events.
     * @param listener the listener that should be notified of events
     */
    void addListener(Listener listener);

    /**
     * Stops publishing an event to a listener.
     * @param listener the listener to stop listening
     */
    void removeListener(Listener listener);
}
