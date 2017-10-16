package org.mitash.altdrive.properties;

import org.mitash.altdrive.event.Event;

/**
 * This event occurs when a property is changed in the {@link PropertyManager}. It holds the property and it's old and new values.
 * @author Jacob Mitash
 */
public class PropertyChangedEvent extends Event {

    private String property;
    private Object previousValue;
    private Object newValue;

    /**
     * Creates a <code>PropertyChangedEvent</code>.
     * @param property the property name that has had its value changed
     * @param previousValue the value of the property before it was changed
     * @param newValue the value of the property after it was changed
     */
    PropertyChangedEvent(String property, Object previousValue, Object newValue) {
        this.property = property;
        this.previousValue = previousValue;
        this.newValue = newValue;
    }

    /**
     * Gets the name of the property that changed.
     * @return the name of the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Gets the value of the property before it was changed
     * @return the previous value of the property
     */
    public Object getPreviousValue() {
        return previousValue;
    }

    /**
     * Gets the value of the property after it was changed
     * @return the new value of the property
     */
    public Object getNewValue() {
        return newValue;
    }
}
