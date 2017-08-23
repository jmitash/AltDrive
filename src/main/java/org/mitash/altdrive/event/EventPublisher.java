package org.mitash.altdrive.event;

/**
 * Defines an EventPublisher capable of holding listeners, removing listeners, and publishing events to them. Classes
 * implementing this interface must be thread-safe in regards to event queueing and publishing.
 * @author jacob
 */
public interface EventPublisher {

    /**
     * Publishes an event to all the listeners that are listening for the specific event type. This should only be
     * called from the main thread.
     * @param event the event to be published
     */
    void publishEvent(Event event);

    /**
     * Queue an event to be executed on the main thread.
     * @param event the event to be queued and eventually published
     */
    void queueEvent(Event event);

    /**
     * Dequeue an event to be published on the main thread. Blocks if no event is available.
     * @throws InterruptedException thrown when interrupted while waiting for event.
     * @return the event to dequeue and publish
     */
    Event dequeueEvent() throws InterruptedException;

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
