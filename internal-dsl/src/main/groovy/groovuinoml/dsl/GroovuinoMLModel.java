package main.groovy.groovuinoml.dsl;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.arduinoml.kernel.generator.ArduinoCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.Component;
import fr.unice.polytech.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.Sensor;
import groovy.lang.Binding;

import java.util.ArrayList;
import java.util.List;

public class GroovuinoMLModel {

	private List<Component> components;
	private List<LCD> lcds;
	private List<State> states;
	private State initialState;

	private Binding binding;

	public GroovuinoMLModel(Binding binding) {
		this.components = new ArrayList<Component>();
		this.states = new ArrayList<State>();
		this.lcds = new ArrayList<>();
		this.binding = binding;
	}
	
	public void createSensor(String name, Integer pinNumber) {
		Sensor sensor = new Sensor();
		sensor.setName(name);
		sensor.setPin(pinNumber);
		this.components.add(sensor);
		this.binding.setVariable(name, sensor);
//		System.out.println("> sensor " + name + " on pin " + pinNumber);
	}

	public void createActuator(String name, Integer pinNumber) {
		Actuator actuator = new Actuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);
		this.components.add(actuator);
		this.binding.setVariable(name, actuator);
	}

	public void createLCD(String name, Integer busNumber) {
		if (busNumber > 3) {
			throw new IllegalArgumentException(String.format("Bus number %s specified isn't supported in Arduino for the LCD component", busNumber));
		}
		LCD lcd = new LCD();
		lcd.setName(name);
		lcd.setPin(busNumber);
		this.components.add(lcd);
		this.lcds.add(lcd);
		this.binding.setVariable(name, lcd);
	}

	public void createState(String name, List<Action> actions) {
		State state = new State();
		state.setName(name);
		state.setActions(actions);
		this.states.add(state);
		this.binding.setVariable(name, state);
	}

	public void createTransition(State from, Transition transition) {
		// Add transition because before we couldn't add several conditions.
		from.addTransition(transition);
	}

	public void setInitialState(State state) {
		this.initialState = state;
	}

	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		app.setComponents(this.components);
		app.setLcds(this.lcds);
		app.setStates(this.states);
		app.setInitial(this.initialState);
		CodeGenVisitor codeGenerator = new ArduinoCodeGenVisitor();
		app.accept(codeGenerator);

		return codeGenerator.getResult();
	}
}
