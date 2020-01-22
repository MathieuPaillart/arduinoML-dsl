package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.arduinoml.kernel.structural.Component;
import lombok.Data;

/**
 * Arduino action that modify an actuator value.
 */
@Data
public abstract class Action implements Visitable {

    /**
     * A value filled by user.
     */
    String value;
    /**
     * A component (can be LCD or Actuator)
     */
    private Component component;

    public abstract void setValue(String value);

}
