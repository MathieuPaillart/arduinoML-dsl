package main.groovy.groovuinoml.dsl

import fr.unice.polytech.arduinoml.kernel.behavioral.Action
import fr.unice.polytech.arduinoml.kernel.behavioral.ActionActuator
import fr.unice.polytech.arduinoml.kernel.behavioral.State
import fr.unice.polytech.arduinoml.kernel.behavioral.Transition
import fr.unice.polytech.arduinoml.kernel.structural.Actuator
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL
import fr.unice.polytech.arduinoml.kernel.structural.Sensor

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

    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        List<Action> actions = new ArrayList<Action>()
        ((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name, actions)
        // recursive closure to allow multiple and statements
        def closure
        closure = { actuator ->
            [becomes: { signal ->
                Action action = new ActionActuator();
                action.setComponent(actuator instanceof String ? (Actuator) ((GroovuinoMLBinding) this.getBinding()).getVariable(actuator) : (Actuator) actuator)
                action.setValue(signal instanceof String ? (SIGNAL) ((GroovuinoMLBinding) this.getBinding()).getVariable(signal) : (SIGNAL) signal)
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
