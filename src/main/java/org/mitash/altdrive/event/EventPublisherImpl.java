package org.mitash.altdrive.event;

import com.google.inject.Singleton;
import org.mitash.altdrive.logger.LoggerFactory;

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

    private final static Logger LOGGER = LoggerFactory.build(EventPublisherImpl.class);

    private List<Listener> allListeners = Collections.synchronizedList(new ArrayList<>());

    private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    @Override
    public void publishEvent(Event event) {
        LOGGER.finer("Publishing event: " + event.toString());
        allListeners.stream()
                .filter(listener -> listener.canHandleEvent(event))
                .forEach(listener -> {
            LOGGER.finest("To: " + listener.toString());
            listener.eventOccurred(event);
        });
    }

    @Override
    public void queueEvent(Event event) {
        try {
            eventQueue.put(event);
            LOGGER.finer("Event queued: " + event);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Interrupted while trying to queue event", e);
        }
    }

    @Override
    public Event dequeueEvent() throws InterruptedException {
        return eventQueue.take();
    }

    @Override
    public void addListener(Listener listener) {
        LOGGER.finer("Adding listener: " + listener.toString());
        allListeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        LOGGER.finer("Removing listener: " + listener.toString());
        if(!allListeners.remove(listener)) {
            throw new InvalidParameterException("Attempted to remove a listener that isn't tracked");
        }
    }
}
