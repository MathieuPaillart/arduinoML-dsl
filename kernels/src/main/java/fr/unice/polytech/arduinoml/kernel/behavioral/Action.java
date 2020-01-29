package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.arduinoml.kernel.structural.Assignable;
import lombok.Data;

/**
 * Arduino action that modify an actuator value.
 */
@Data
public abstract class Action<T> implements Visitable {

    /**
     * A value filled by user.
     */
    T value;

    /**
     * A assignable assignableComponent
     */
    private Assignable assignableComponent;

    /**
     * Setter value.
     *
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }

}
