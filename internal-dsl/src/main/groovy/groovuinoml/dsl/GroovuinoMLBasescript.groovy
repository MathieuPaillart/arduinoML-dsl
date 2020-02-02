package main.groovy.groovuinoml.dsl

import fr.unice.polytech.arduinoml.kernel.behavioral.*
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.Keyboard
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.RemoteComponent
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Actuator
import fr.unice.polytech.arduinoml.kernel.structural.components.simple.Sensor

/**
 * List the available methods for the groovy scripts.
 *
 * The methods use method chaining to populate the model.
 */
abstract class GroovuinoMLBasescript extends Script {
    // sensor "name" pin n
    def sensor(String name) {
        [pin  : { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createSensor(name, n) },
         onPin: { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
    }

    // actuator "name" pin n
    def actuator(String name) {
        [pin: { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }]
    }

    // lcd "name" bus n
    def lcd(String name) {
        [bus  : { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createLCD(name, n) },
         onBus: { n -> ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createLCD(name, n) }]
    }

    // keyboard "name" values high low
    def keyboard(String name) {
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createKeyboard(name)
        [values: { high, low ->
            Keyboard keyboard = ((Keyboard) ((GroovuinoMLBinding) this.getBinding()).getVariable(name))
            keyboard.setValueHigh(high)
            keyboard.setValueLow(low)
        }]
    }

    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        List<Action> actions = new ArrayList<Action>()
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name, actions)
        // recursive closure to allow multiple and statements
        def closure
        closure = { actuator ->
            [becomes: { signal ->
                // Need to separate behavior between numeric and remote assignment
                if(signal instanceof RemoteComponent) {
                    Action action = new ActionAssignmentFromRemote();
                    action.setAssignableComponent(actuator instanceof String ? (Actuator) ((GroovuinoMLBinding) this.getBinding()).getVariable(actuator) : (Actuator) actuator)
                    action.setValue((Keyboard) signal)
                    actions.add(action)
                } else {
                    Action action = new ActionAssignmentFromNumeric()
                    action.setAssignableComponent(actuator instanceof String ? (Actuator) ((GroovuinoMLBinding) this.getBinding()).getVariable(actuator) : (Actuator) actuator)
                    action.setValue(signal instanceof String ? (SIGNAL) ((GroovuinoMLBinding) this.getBinding()).getVariable(signal) : (SIGNAL) signal)
                    actions.add(action)
                }
                [and: closure]
            },
             prints : { text ->
                 // Need to separate behavior between print input
                 Action action = new ActionDisplay()
                 action.setAssignableComponent(actuator instanceof String ? (LCD) ((GroovuinoMLBinding) this.getBinding()).getVariable(actuator) : (LCD) actuator)
                 if(text instanceof Sensor) {
                     action.setValue(((Sensor) text));
                 } else if (text instanceof Keyboard) {
                     action.setValue(((Keyboard) text));
                 } else {
                     action.setValue(text);
                 }
                 actions.add(action)
                 [and: closure]
             }]
        }
        [means: closure]
    }

    // initial state
    def initial(state) {
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().setInitialState(state instanceof String ? (State) ((GroovuinoMLBinding) this.getBinding()).getVariable(state) : (State) state)
    }

    // from state1 to state2 when sensor becomes signal
    def from(state1) {
        [to: { state2 ->
            Transition t = new Transition()
            t.setNext(state2 instanceof String ? (State) ((GroovuinoMLBinding) this.getBinding()).getVariable(state2) : (State) state2)
            closure = { sensor ->
                t.setSensor(sensor instanceof String ? (Sensor) ((GroovuinoMLBinding) this.getBinding()).getVariable(sensor) : (Sensor) sensor)
                [becomes: { signal ->
                    t.setValue(signal instanceof String ? (SIGNAL) ((GroovuinoMLBinding) this.getBinding()).getVariable(signal) : (SIGNAL) signal)
                }]
            }
            ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(
                    state1 instanceof String ? (State) ((GroovuinoMLBinding) this.getBinding()).getVariable(state1) : (State) state1,
                    t)
            [when: closure]
        }]
    }

    // export name
    def export(String name) {
        println(((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
    }

    // disable run method while running
    int count = 0

    abstract void scriptBody()

    def run() {
        if (count == 0) {
            count++
            scriptBody()
        } else {
            println "Run method is disabled"
        }
    }
}
