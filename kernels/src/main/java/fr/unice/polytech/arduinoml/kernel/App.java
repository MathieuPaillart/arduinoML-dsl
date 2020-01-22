package fr.unice.polytech.arduinoml.kernel;

import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import fr.unice.polytech.arduinoml.kernel.structural.Component;
import fr.unice.polytech.arduinoml.kernel.structural.LCD;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Arduino Application.
 */
@Data
public class App extends NamedElement implements Visitable {

	/**
	 * Application's name.
	 */
	private String name;

	/**
	 * Application's components.
	 */
	private List<Component> components = new ArrayList<Component>();

	/**
	 * Application's states.
	 */
	private List<State> states = new ArrayList<State>();

    private List<LCD> lcds = new ArrayList<>();

	/**
	 * Application's initial state.
	 */
	private State initial;

	/**
	 * {@inheritDoc}
	 */
    public void addLCD(LCD lcd) {
        components.add(lcd);
        lcds.add(lcd);
    }
	@Override
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitApp(this);
	}
}
