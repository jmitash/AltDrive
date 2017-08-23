package org.mitash.altdrive.event;

import com.google.inject.Singleton;
import org.mitash.altdrive.logger.ADLogger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of {@link EventPublisher}.
 * @author jacob
 */
@Singleton
public class EventPublisherImpl implements EventPublisher {

    @ADLogger
    private Logger logger;

    private List<Listener> allListeners = Collections.synchronizedList(new ArrayList<>());

    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    @Override
    public void publishEvent(Event event) {
        logger.finer("Publishing event: " + event.toString());
        allListeners.stream()
                .filter(listener -> listener.canHandleEvent(event))
                .forEach(listener -> {
            logger.finest("To: " + listener.toString());
            listener.eventOccurred(event);
        });
    }

    @Override
    public void queueEvent(Event event) {
        try {
            eventQueue.put(event);
            logger.finer("Event queued: " + event);
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "Interrupted while trying to queue event", e);
        }
    }

    @Override
    public Event dequeueEvent() throws InterruptedException {
        return eventQueue.take();
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
