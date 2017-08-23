package org.mitash.altdrive.event;

import org.junit.Test;
import org.mitash.altdrive.test.TestHelper;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

/**
 * @author jacob
 */
public class EventPublisherImplTest extends TestHelper {

    @Test
    public void addAndListen() {
        EventPublisher eventPublisher = getEventPublisher();

        Event event = new TestEvent();
        TestListener listener = new TestListener();
        eventPublisher.addListener(listener);
        eventPublisher.publishEvent(event);

        assertEquals(event, listener.receivedEvent);
    }

    @Test
    public void addAndRemove() {
        EventPublisher eventPublisher = getEventPublisher();

        Event event = new TestEvent();
        TestListener listener = new TestListener();
        eventPublisher.addListener(listener);
        eventPublisher.removeListener(listener);

        //Publish the event (nothing should happen)
        eventPublisher.publishEvent(event);

        //Remove, attempting to get exception
        boolean exception = false;
        try {
            eventPublisher.removeListener(listener);
        } catch (InvalidParameterException e) {
            exception = true;
        }

        assertTrue(exception);
        assertNull(listener.receivedEvent);
    }

    @Test
    public void queueAndDequeue() {
        EventPublisher eventPublisher = getEventPublisher();

        Event event = new TestEvent();

        TestThread thread = new TestThread(eventPublisher);

        thread.start();

        eventPublisher.queueEvent(event);
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail(e.toString());
        }
        assertFalse("The thread timed out while dequeueing", thread.isAlive());

        assertEquals(thread.dequeueEvent, event);
    }

    private EventPublisherImpl getEventPublisher() {
        EventPublisherImpl eventPublisher = new EventPublisherImpl();
        injectLogger(eventPublisher);
        return eventPublisher;
    }

    private class TestEvent extends Event {

    }

    private class TestListener implements Listener {
        private Event receivedEvent;

        @Override
        public void eventOccurred(Event event) {
            receivedEvent = event;
        }

        @Override
        public boolean canHandleEvent(Event event) {
            return event.getClass().equals(TestEvent.class);
        }
    }

    private class TestThread extends Thread {
        private final EventPublisher eventPublisher;
        private Event dequeueEvent;

        TestThread(EventPublisher eventPublisher) {
            this.eventPublisher = eventPublisher;
        }

        @Override
        public void run() {
            try {
                dequeueEvent = eventPublisher.dequeueEvent();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}