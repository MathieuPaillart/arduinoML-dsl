package fr.unice.polytech.arduinoml.kernel;

import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.generator.core.ArduinoCoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ArduinoImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.ArduinoSetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.NamedElement;
import fr.unice.polytech.arduinoml.kernel.structural.components.Component;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Arduino Application.
 */
@Data
public class App implements NamedElement {

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

	/**
	 * Application's initial state.
	 */
	private State initial;


	public String generate() {
		final ImportCodeGenVisitor importCodeGenVisitor = new ArduinoImportCodeGenVisitor();
		final SetupCodeGenVisitor setupCodeGenVisitor = new ArduinoSetupCodeGenVisitor();
		final CoreCodeGenVisitor coreCodeGenVisitor = new ArduinoCoreCodeGenVisitor();

		final StringBuffer code = new StringBuffer();
		importCodeGenVisitor.visitApp(this);
		code.append(importCodeGenVisitor.getResult());

		setupCodeGenVisitor.visitApp(this);
		code.append(setupCodeGenVisitor.getResult());

		coreCodeGenVisitor.visitApp(this);
		code.append(coreCodeGenVisitor.getResult());

		return code.toString();
	}
}
