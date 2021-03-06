package fr.unice.polytech.arduinoml.kernel.generator.core;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Core code generator visitor abstract class.
 *
 * @param <T> the output.
 */
public abstract class CoreCodeGenVisitor<T> {

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
     * Visits a FSM transition.
     *
     * @param transition the transition
     */
    public abstract void visitTransition(Transition transition);

    /**
     * Visits a display action containing an assignable component.
     *
     * @param actionDisplay the action
     */
    public abstract void visitActionDisplay(ActionDisplay actionDisplay);

    /**
     * Visits a numeric assignment action containing an assignable component.
     *
     * @param actionNumericAssignment the action
     */
    public abstract void visitActionNumeric(ActionAssignmentFromNumeric actionNumericAssignment);

    /**
     * Visits an Action of remote assignement.
     *
     * @param action the keyboard
     */
    public abstract void visitActionRemote(ActionAssignmentFromRemote action);

    /**
     * Get the result.
     *
     * @return the result.
     */
    public T getResult() {
        return result;
    }

}

