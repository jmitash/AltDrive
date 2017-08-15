package org.mitash.altdrive.event;

import org.mitash.altdrive.logger.ADLogger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Default implementation of {@link EventPublisher}.
 * @author jacob
 */
public class EventPublisherImpl implements EventPublisher {

    @ADLogger
    private Logger logger;

    private Map<Class, List<Listener<Event>>> listenerMap = new HashMap<>();

    @Override
    public void publishEvent(Event event) {
        logger.finer("Publishing event: " + event.toString());
        List<Listener<Event>> listeners = listenerMap.get(event.getClass());
        for(Listener<Event> listener : listeners) {
            logger.finest("To: " + listener.toString());
            listener.eventOccurred(event);
        }
    }

    @Override
    public void addListener(Listener<Event> listener) {
        logger.finer("Adding listener: " + listener.toString());
        Class<?> eventClass = listener.getEventClass();
        List<Listener<Event>> listeners = listenerMap.get(listener.getEventClass());
        if(listeners == null) {
            listeners = new ArrayList<>();
            listenerMap.put(eventClass, listeners);
        }
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<Event> listener) {
        logger.finer("Removing listener: " + listener.toString());
        List<Listener<Event>> listeners = listenerMap.get(listener.getEventClass());
        if(!listeners.remove(listener)) {
            throw new InvalidParameterException("Attempted to remove a listener that isn't tracked");
        }
    }
}
