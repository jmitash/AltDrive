package org.mitash.altdrive.properties;

/**
 * Acts as a centralized repository for properties.
 * @author Jacob Mitash
 */
public interface PropertyManager {

    Object getProperty(String property);

    int getPropertyAsInt(String property);

    long getPropertyAsLong(String property);

    boolean getPropertyAsBoolean(String property);

    double getPropertyAsDouble(String property);

    float getPropertyAsFloat(String property);

    void setProperty(String property, Object value);
}
