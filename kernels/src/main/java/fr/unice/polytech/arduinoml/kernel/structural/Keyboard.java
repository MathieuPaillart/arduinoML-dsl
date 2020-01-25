package fr.unice.polytech.arduinoml.kernel.structural;


import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;

import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Representation of the keyboard.
 */
public class Keyboard extends Remote {

	public Keyboard(final String valueHigh, final String valueLow) {
		this.valueHigh = valueHigh;
		this.valueLow = valueLow;
	}

	public Keyboard() {
		this.valueHigh = "1";
		this.valueLow = "0";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(CodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitKeyboard(this);
	}

}
