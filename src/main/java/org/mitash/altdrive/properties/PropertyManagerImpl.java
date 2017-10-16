package org.mitash.altdrive.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mitash.altdrive.AltDriveApplication;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.logger.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Jacob Mitash
 */
@Singleton
public class PropertyManagerImpl implements PropertyManager {

    private final static Logger LOGGER = LoggerFactory.build(PropertyManagerImpl.class);

    private final EventPublisher eventPublisher;

    private Map<String, Object> properties;

    @Inject
    @SuppressWarnings("unchecked")
    public PropertyManagerImpl(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;

        InputStream stream = AltDriveApplication.class.getResourceAsStream("/common_properties.json");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            properties = objectMapper.readValue(stream, Map.class);
        } catch (IOException | ClassCastException e) {
            LOGGER.log(Level.SEVERE, "Could not read properties file", e);
        }
    }

    @Override
    public Object getProperty(String property) {
        return properties.get(property);
    }

    @Override
    public int getPropertyAsInt(String property) {
        return Integer.parseInt(properties.get(property).toString());
    }

    @Override
    public long getPropertyAsLong(String property) {
        return Long.parseLong(properties.get(property).toString());
    }

    @Override
    public boolean getPropertyAsBoolean(String property) {
        return Boolean.parseBoolean(properties.get(property).toString());
    }

    @Override
    public double getPropertyAsDouble(String property) {
        return Double.parseDouble(properties.get(property).toString());
    }

    @Override
    public float getPropertyAsFloat(String property) {
        return Float.parseFloat(properties.get(property).toString());
    }

    @Override
    public void setProperty(String property, Object value) {
        Object previousValue = properties.get(property);
        properties.put(property, value);
        eventPublisher.publishEvent(new PropertyChangedEvent(property, previousValue, value));
    }
}
