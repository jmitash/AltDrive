//package org.mitash.altdrive.event;
//
//import org.junit.jupiter.api.Test;
//
//import java.security.InvalidParameterException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author jacob
// */
//class EventPublisherImplTest {
//
//    @Test
//    void addAndListen() {
//        EventPublisher eventPublisher = new EventPublisherImpl();
//
//        Event event = new TestEvent();
//        TestListener listener = new TestListener();
//        eventPublisher.addListener(listener);
//        eventPublisher.publishEvent(event);
//
//        assertEquals(event, listener.receivedEvent);
//    }
//
//    @Test
//    void addAndRemove() {
//        EventPublisher eventPublisher = new EventPublisherImpl();
//
//        Event event = new TestEvent();
//        TestListener listener = new TestListener();
//        eventPublisher.addListener(listener);
//        eventPublisher.removeListener(listener);
//
//        //Publish the event (nothing should happen)
//        eventPublisher.publishEvent(event);
//
//        //Remove, attempting to get exception
//        boolean exception = false;
//        try {
//            eventPublisher.removeListener(listener);
//        } catch (InvalidParameterException e) {
//            exception = true;
//        }
//
//        assertTrue(exception);
//        assertNull(listener.receivedEvent);
//    }
//
//    private class TestEvent extends Event {
//
//    }
//
//    private class TestListener implements Listener {
//        private Event receivedEvent;
//
//        @Override
//        public void eventOccurred(Event event) {
//            receivedEvent = event;
//        }
//
//        @Override
//        public boolean canHandleEvent(Event event) {
//            return event.getClass().equals(TestEvent.class);
//        }
//    }
//}