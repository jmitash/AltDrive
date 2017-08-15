package org.mitash.altdrive.event;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacob
 */
public class EventPublisher {

    private Map<Class, List<Listener<Event>>> listenerMap = new HashMap<>();

    /**
     * Publishes an event to all the listeners that are listening for the specific event type.
     * @param event the event to be published
     */
    public void publishEvent(Event event) {
        List<Listener<Event>> listeners = listenerMap.get(event.getClass());
        for(Listener<Event> listener : listeners) {
            listener.eventOccurred(event);
        }
    }

    /**
     * Adds a new listener to listen for events.
     * @param listener the listener that should be notified of events
     */
    public void addListener(Listener<Event> listener) {
        Class<?> eventClass = listener.getEventClass();
        List<Listener<Event>> listeners = listenerMap.get(listener.getEventClass());
        if(listeners == null) {
            listeners = new ArrayList<>();
            listenerMap.put(eventClass, listeners);
        }
        listeners.add(listener);
    }

    /**
     * Stops publishing an event to a listener.
     * @param listener the listener to stop listening
     */
    public void removeListener(Listener<Event> listener) {
        List<Listener<Event>> listeners = listenerMap.get(listener.getEventClass());
        if(!listeners.remove(listener)) {
            throw new InvalidParameterException("Attempted to remove a listener that isn't tracked");
        }
    }
}
