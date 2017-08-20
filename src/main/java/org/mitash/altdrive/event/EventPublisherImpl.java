package org.mitash.altdrive.event;

import org.mitash.altdrive.logger.ADLogger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Default implementation of {@link EventPublisher}.
 * @author jacob
 */
public class EventPublisherImpl implements EventPublisher {

    @ADLogger
    private Logger logger;

    private List<Listener> allListeners = new ArrayList<>();

    @Override
    public void publishEvent(Event event) {
        logger.finer("Publishing event: " + event.toString());
        allListeners.stream().filter(listener -> listener.canHandleEvent(event)).forEach(listener -> {
            logger.finest("To: " + listener.toString());
            listener.eventOccurred(event);
        });
    }

    @Override
    public void addListener(Listener listener) {
        logger.finer("Adding listener: " + listener.toString());
        allListeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        logger.finer("Removing listener: " + listener.toString());
        if(!allListeners.remove(listener)) {
            throw new InvalidParameterException("Attempted to remove a listener that isn't tracked");
        }
    }
}
