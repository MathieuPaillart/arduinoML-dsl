package fr.unice.polytech.arduinomldsl.visitor.antlr;

import dsl.ArduinoMLBaseListener;
import dsl.ArduinoMLParser;
import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.*;
import fr.unice.polytech.arduinoml.kernel.exception.BusNonExistentException;
import fr.unice.polytech.arduinoml.kernel.exception.MissingDeclarationOfComponent;
import fr.unice.polytech.arduinoml.kernel.exception.OnlyOneKeyboardException;
import fr.unice.polytech.arduinoml.kernel.exception.PinAlreadyAssignedException;
import fr.unice.polytech.arduinoml.kernel.structural.Assignable;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.Keyboard;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelBuilder extends ArduinoMLBaseListener {

	/********************
	 ** Business Logic **
	 ********************/

	private App theApp = null;
	private boolean built = false;
	/*******************
	 ** Symbol tables **
	 *******************/

	private Map<String, Sensor> sensors = new HashMap<>();
	private Map<String, Actuator> actuators = new HashMap<>();
	private Map<String, LCD> lcds = new HashMap<>();
	private Keyboard keyboard = null;
	private List<Integer> pins = new ArrayList<>();
	private Map<String, State> states = new HashMap<>();
	private Map<String, List<Binding>> bindings = new HashMap<>();
	private List<Binding> currentBindings = new ArrayList<>();
	private State currentState = null;

	public App retrieve() {
		if (built) {
			resolve();
			return theApp;
		}
		throw new RuntimeException("Cannot retrieve a model that was not created!");
	}

	/**************************
	 ** Listening mechanisms **
	 **************************/

	@Override
	public void enterRoot(ArduinoMLParser.RootContext ctx) {
		System.out.println("------------------- enterRoot --------------------");
		built = false;
		theApp = new App();
	}

	@Override
	public void exitRoot(ArduinoMLParser.RootContext ctx) {
		System.out.println("------------------- exitRoot --------------------");
		// Resolving states in transitions
		bindings.forEach((key, bindings) -> bindings.forEach(binding -> {
			Transition t = new Transition();
			t.setSensor(binding.trigger);
			t.setValue(binding.value);
			t.setNext(states.get(binding.to));
			states.get(key).addTransition(t);
		}));
		this.built = true;
	}

	@Override
	public void enterDeclaration(ArduinoMLParser.DeclarationContext ctx) {
		System.out.println("------------------- enterDeclaration --------------------");
		System.out.println(ctx.name.getText());
		theApp.setName(ctx.name.getText());
	}

	@Override
	public void enterSensor(ArduinoMLParser.SensorContext ctx) {
		System.out.println("------------------- enterSensor --------------------");
		System.out.println("sensor " + ctx.location().id.getText() + " " + ctx.location().port.getText());
		Sensor sensor = new Sensor();
		sensor.setName(ctx.location().id.getText());
		sensor.setPin(Integer.parseInt(ctx.location().port.getText()));
		this.theApp.getComponents().add(sensor);
		sensors.put(sensor.getName(), sensor);
	}

	@Override
	public void enterActuator(ArduinoMLParser.ActuatorContext ctx) {
		System.out.println("------------------- enterActuator --------------------");
		System.out.println("actuator " + ctx.location().id.getText() + " " + ctx.location().port.getText());
		Actuator actuator = new Actuator();
		actuator.setName(ctx.location().id.getText());
		actuator.setPin(Integer.parseInt(ctx.location().port.getText()));
		this.theApp.getComponents().add(actuator);
		actuators.put(actuator.getName(), actuator);
	}

	@Override
	public void enterLcd(ArduinoMLParser.LcdContext ctx) {
		System.out.println("------------------- enterLcd --------------------");
		System.out.println("lcd " + ctx.location().id.getText() + " " + ctx.location().port.getText());

		int busNumber = Integer.parseInt(ctx.location().port.getText());
		if (busNumber > 2)
			throw new BusNonExistentException(String.format("The bus number %s specified isn't supported in arduino!", busNumber));
		LCD lcd = new LCD(busNumber);
		lcd.setName(ctx.location().id.getText());
		this.theApp.getComponents().add(lcd);
		lcds.put(lcd.getName(), lcd);
	}

	@Override
	public void enterKeyboard(ArduinoMLParser.KeyboardContext ctx) {
		if (keyboard == null) {
			if (ctx.STRING().isEmpty()) {
				keyboard = new Keyboard();
			} else {
				if (ctx.STRING().size() < 2) {
					throw new IllegalArgumentException("Keyboard requires two arguments in this format : Keyboard kb :\"HIGH\":\"BAD\"");
				}
				keyboard = new Keyboard(ctx.STRING().get(0).getText().replace("\"", ""), ctx.STRING().get(1).getText().replace("\"", ""));
			}
			keyboard.setName(ctx.id.getText());
			theApp.getComponents().add(keyboard);
		} else {
			throw new OnlyOneKeyboardException("You have declared more than one keyboard, it is not supported");
		}
	}


	@Override
	public void enterState(ArduinoMLParser.StateContext ctx) {
		System.out.println("------------------- enterState --------------------");
		System.out.println("state " + ctx.name.getText());
		State local = new State();
		local.setName(ctx.name.getText());
		this.currentState = local;
		this.states.put(local.getName(), local);
	}

	@Override
	public void exitState(ArduinoMLParser.StateContext ctx) {
		System.out.println("------------------- exitState --------------------");
		this.bindings.put(currentState.getName(), this.currentBindings);
		this.theApp.getStates().add(this.currentState);
		this.currentBindings = new ArrayList<>();
		this.currentState = null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void enterAction(ArduinoMLParser.ActionContext ctx) {
		System.out.println("------------------- enterAction --------------------");
		boolean isLcdAction = ctx.actionLCD() != null;
		String value = isLcdAction ? ctx.actionLCD().value.getText() : ctx.actionAssignment().value.getText();
		boolean isActionWithKeyboard = isKeyboardReference(value);
		String receiver = ctx.receiver.getText();

		System.out.println("action " + receiver + " " + value);

		Assignable assignable = actuators.containsKey(receiver) ? actuators.get(receiver) : lcds.get(receiver);
		Action action;
		if (isLcdAction) {
			action = new ActionLcd();
			if (isSensorReference(value)) {
				action.setValue(sensors.get(value));
			} else if (isActionWithKeyboard) {
				action.setValue(keyboard);
			} else {
				action.setValue(value);
			}
		} else {
			if (isActionWithKeyboard) {
				action = new ActionAssignmentFromRemote();
				action.setValue(keyboard);
			} else {
				action = new ActionAssignmentFromNumeric();
				action.setValue(value);
			}
		}
		action.setAssignableComponent(assignable);
		if (action.getAssignableComponent() == null) {
			throw new MissingDeclarationOfComponent(receiver + "is missing from assignableComponent declaration");
		}
		currentState.getActions().add(action);
	}

	private boolean isKeyboardReference(String value) {
		return keyboard != null && keyboard.getName().equals(value);
	}

	private boolean isSensorReference(String name) {
		return sensors.containsKey(name);
	}

	@Override
	public void enterTransition(ArduinoMLParser.TransitionContext ctx) {
		System.out.println("------------------- enterTransition --------------------");
		// Creating a placeholder as the next state might not have been compiled yet.
		Binding toBeResolvedLater = new Binding();
		toBeResolvedLater.to = ctx.next.getText();
		if (ctx.trigger != null) toBeResolvedLater.trigger = sensors.get(ctx.trigger.getText());
		if (ctx.value != null) toBeResolvedLater.value = SIGNAL.valueOf(ctx.value.getText());
		currentBindings.add(toBeResolvedLater);
	}

	@Override
	public void enterInitial(ArduinoMLParser.InitialContext ctx) {
		System.out.println("------------------- enterInitial --------------------");
		this.theApp.setInitial(this.currentState);
	}

	/**
	 * This method checks at the end of the parsing if all the pin from the different actuator/sensor/lcd are unique
	 * This method is quite long because the error message is meaningful, we know exactly which assignableComponent's pin is in fault
	 * <p>
	 * We could have done it inside the listener method but it would have added unnecessary behavior in those methods.
	 */
	public void resolve() {
		for (Actuator actuator : actuators.values()) {
			if (pins.contains(actuator.getPin())) {
				throw new PinAlreadyAssignedException(String.format("This actuator %s is using an already assigned pin : %s", actuator.getName(), actuator.getPin()));
			}
			pins.add(actuator.getPin());
		}
		for (Sensor sensor : sensors.values()) {
			if (pins.contains(sensor.getPin())) {
				throw new PinAlreadyAssignedException(String.format("This sensor %s is using an already assigned pin : %s", sensor.getName(), sensor.getPin()));
			}
			pins.add(sensor.getPin());
		}
		for (LCD lcd : lcds.values()) {
			for (Integer pin : lcd.getPins()) {
				if (pins.contains(pin)) {
					throw new PinAlreadyAssignedException(String.format("This lcd %s is using an already assigned pin : %s", lcd.getName(), pin));
				}
			}
			pins.addAll(lcd.getPins());
		}
	}

	private class Binding { // used to support state resolution for transitions
		String to; // name of the next state, as its instance might not have been compiled yet
		Sensor trigger;
		SIGNAL value;
	}

}

