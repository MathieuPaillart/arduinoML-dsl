package main.groovy.groovuinoml.dsl;

import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;
import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

/**
 * Binding that store the current model and used by the script to reference it
 */
public class GroovuinoMLBinding extends Binding {
	// can be useful to return the script in case of syntax trick
	private Script script;

	private main.groovy.groovuinoml.dsl.GroovuinoMLModel model;

	public GroovuinoMLBinding() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public GroovuinoMLBinding(Map variables) {
		super(variables);
	}

	public GroovuinoMLBinding(Script script) {
		super();
		this.script = script;
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public void setGroovuinoMLModel(main.groovy.groovuinoml.dsl.GroovuinoMLModel model) {
		this.model = model;
	}

	public Object getVariable(String name) {
		if (name.equals("HIGH")) {
			return SIGNAL.HIGH;
		} else if (name.equals("LOW")) {
			return SIGNAL.LOW;
		}
		return super.getVariable(name);
	}

	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}

	public GroovuinoMLModel getGroovuinoMLModel() {
		return this.model;
	}
}
