package main.groovy.groovuinoml.dsl;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.behavioral.Action;
import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.behavioral.Transition;
import fr.unice.polytech.arduinoml.kernel.exception.BusNonExistentException;
import fr.unice.polytech.arduinoml.kernel.exception.PinAlreadyAssignedException;
import fr.unice.polytech.arduinoml.kernel.generator.core.ArduinoCoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ArduinoImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.ArduinoSetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.Keyboard;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Sensor;
import groovy.lang.Binding;

import java.util.ArrayList;
import java.util.List;

public class GroovuinoMLModel {

    private List<Component> components;
    private List<State> states;
    private List<Integer> pins;
    private State initialState;

    private Binding binding;

    public GroovuinoMLModel(Binding binding) {
        this.components = new ArrayList<>();
        this.states = new ArrayList<>();
        this.pins = new ArrayList<>();
        this.binding = binding;
    }

    public void createSensor(String name, Integer pinNumber) {
        if (pins.contains(pinNumber)) {
            throw new PinAlreadyAssignedException(String.format("This sensor %s is using an already assigned pin : %s", name, pinNumber));
        }
        Sensor sensor = new Sensor();
        sensor.setName(name);
        sensor.setPin(pinNumber);
        this.components.add(sensor);
        this.pins.add(pinNumber);
        this.binding.setVariable(name, sensor);
    }

    public void createActuator(String name, Integer pinNumber) {
        if (pins.contains(pinNumber)) {
            throw new PinAlreadyAssignedException(String.format("This actuator %s is using an already assigned pin : %s", name, pinNumber));
        }
        Actuator actuator = new Actuator();
        actuator.setName(name);
        actuator.setPin(pinNumber);
        this.components.add(actuator);
        this.pins.add(pinNumber);
        this.binding.setVariable(name, actuator);
    }

    public void createLCD(String name, Integer busNumber) {
        if (busNumber > 3) {
            throw new BusNonExistentException(String.format("The bus number %s specified isn't supported in arduino!", busNumber));
        }
        LCD lcd = new LCD(busNumber);
        for (Integer pin : lcd.getPins()) {
            if (pins.contains(pin)) {
                throw new PinAlreadyAssignedException(String.format("This lcd %s is using an already assigned pin : %s", name, pin));
            }
        }
        lcd.setName(name);
        this.components.add(lcd);
        this.pins.addAll(lcd.getPins());
        this.binding.setVariable(name, lcd);
    }

    public void createKeyboard(String name) {
        final Keyboard keyboard = new Keyboard();
        keyboard.setName(name);
        this.components.add(keyboard);
        this.binding.setVariable(name, keyboard);
    }

    @SuppressWarnings("rawtypes")
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
        app.setStates(this.states);
        app.setInitial(this.initialState);

        ImportCodeGenVisitor importCodeGenVisitor = new ArduinoImportCodeGenVisitor();
        SetupCodeGenVisitor setupCodeGenVisitor = new ArduinoSetupCodeGenVisitor();
        CoreCodeGenVisitor coreCodeGenVisitor = new ArduinoCoreCodeGenVisitor();

        StringBuilder code = new StringBuilder();
        importCodeGenVisitor.visitApp(app);
        code.append(importCodeGenVisitor.getResult());

        setupCodeGenVisitor.visitApp(app);
        code.append(setupCodeGenVisitor.getResult());

        coreCodeGenVisitor.visitApp(app);
        code.append(coreCodeGenVisitor.getResult());

        return code.toString();
    }
}
