package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.NamedElement;
import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.Visitable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Arduino FSM State.
 */
@Data
public class State extends NamedElement implements Visitable {

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
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitState(this);
	}
}
