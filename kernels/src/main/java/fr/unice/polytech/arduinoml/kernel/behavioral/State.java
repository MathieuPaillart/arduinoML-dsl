package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.NamedElement;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Arduino FSM State.
 */
@Data
public class State implements Visitable, NamedElement {

	/**
	 * Element's name.
	 */
	protected String name;

	/**
	 * Actions of the state.
	 */
	private List<Action> actions = new ArrayList<>();

	/**
	 * Outgoing transitions of the state.
	 */
	private List<Transition> transitions = new ArrayList<>();

	/**
	 * Add a given transaction.
	 *
	 * @param transition
	 */
	public void addTransition(final Transition transition) {
		this.transitions.add(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptImportGen(ImportCodeGenVisitor codeGenVisitor) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptSetupGen(SetupCodeGenVisitor codeGenVisitor) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitState(this);
	}
}
