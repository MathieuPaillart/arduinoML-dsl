package fr.unice.polytech.arduinoml.kernel.generator.setup;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Sensor;
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.Keyboard;

import java.util.HashMap;
import java.util.Map;

/**
 * Setupt code generator visitor abstract class.
 *
 * @param <T> the output.
 */
public abstract class SetupCodeGenVisitor<T> {

    /**
     * Helpful context.
     */
    protected Map<String, Object> context = new HashMap<>();

    /**
     * The Code generation result.
     */
    protected T result;

    /**
     * Visits the app.
     *
     * @param app the app
     */
    public abstract void visitApp(App app);

    /**
     * Visits a lcd.
     *
     * @param lcd the transition
     */
    public abstract void visitLCD(LCD lcd);

    /**
     * Visits a keyboard.
     *
     * @param keyboard the keyboard
     */
    public abstract void visitKeyboard(Keyboard keyboard);

    /**
     * Visits an Actuator.
     *
     * @param actuator the actuator
     */
    public abstract void visitActuator(Actuator actuator);

    /**
     * Visits a Sensor.
     *
     * @param sensor the sensor
     */
    public abstract void visitSensor(Sensor sensor);

    /**
     * Get the result.
     *
     * @return the result.
     */
    public T getResult() {
        return result;
    }

}

