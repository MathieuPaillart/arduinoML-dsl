package fr.unice.polytech.arduinoml.kernel.generator;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.Keyboard;
import fr.unice.polytech.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.Sensor;

import java.util.HashMap;
import java.util.Map;

/**
 * Code generator visitor abstract class.
 *
 * @param <T> the output.
 */
public abstract class CodeGenVisitor<T> {

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
     * Visits a FSM state.
     *
     * @param state the state
     */
    public abstract void visitState(State state);

    /**
     * Visits a lcd.
     *
     * @param lcd the transition
     */
    public abstract void visitLCD(LCD lcd);

    /**
     * Visits a FSM transition.
     *
     * @param transition the transition
     */
    public abstract void visitTransition(Transition transition);

    /**
     * Visits an Action containing a component that is a lcd.
     *
     * @param actionLcd the action
     */
    public abstract void visitActionLCD(ActionLcd actionLcd);

    /**
     * Visits an Action containing a component that is a actuator.
     *
     * @param actionNumericAssignment the action
     */
    public abstract void visit(ActionNumericAssignment actionNumericAssignment);

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
     * Visits a keyboard.
     *
     * @param keyboard the keyboard
     */
    public abstract void visitKeyboard(Keyboard keyboard);

    /**
     * Visits an Action of remote assignement.
     *
     * @param action the keyboard
     */
    public abstract void visitActionRemote(ActionRemoteAssignment action);

    /**
     * Get the result.
     *
     * @return the result.
     */
    public T getResult() {
        return result;
    }

}

