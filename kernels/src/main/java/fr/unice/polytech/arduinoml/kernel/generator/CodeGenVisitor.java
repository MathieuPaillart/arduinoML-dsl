package fr.unice.polytech.arduinoml.kernel.generator;

import fr.unice.polytech.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.Sensor;
import fr.unice.polytech.arduinoml.kernel.App;

import java.util.HashMap;
import java.util.Map;

/**
 * Code generator visitor abstract class.
 * @param <T> the output.
 */
public abstract class CodeGenVisitor<T> {

	/**
	 * Helpful context.
	 */
	protected Map<String,Object> context = new HashMap<>();

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
	 * Visits an Action.
	 *
	 * @param action the action
	 */
	public abstract void visitAction(Action action);

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

