package org.mitash.altdrive;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.drive.DriveModule;
import org.mitash.altdrive.event.Event;
import org.mitash.altdrive.event.EventModule;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.logger.ADLoggerInjector;
import org.mitash.altdrive.remote.RemoteWatcher;
import org.mitash.altdrive.remote.RemoteWatcherModule;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AltDriveApplication {

    public static final String APPLICATION_NAME = "AltDrive";

    private final static Logger LOGGER = ADLoggerInjector.buildLogger(AltDriveApplication.class.getName());

    private void start() {
        Injector injector = Guice.createInjector(
                new DriveModule(),
                new EventModule(),
                new RemoteWatcherModule());

        try {
            injector.getInstance(AltDrive.class).initialize();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not initialize Drive", e);
            return;
        }

        injector.getInstance(RemoteWatcher.class).startWatching();
        EventPublisher eventPublisher = injector.getInstance(EventPublisher.class);

        while (!Thread.interrupted()) {
            Event event;
            try {
                event = eventPublisher.dequeueEvent();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Interrupted while waiting for next event", e);
                return;
            }

            eventPublisher.publishEvent(event);
        }
    }

    public static void main(String[] args) {
        new AltDriveApplication().start();
    }
}