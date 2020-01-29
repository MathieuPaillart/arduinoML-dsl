package fr.unice.polytech.arduinoml.kernel.structural.components.remote;


import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;

/**
 * Representation of the keyboard.
 */
public class Keyboard extends RemoteComponent {

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
	public void acceptImportGen(ImportCodeGenVisitor codeGenVisitor) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptSetupGen(SetupCodeGenVisitor codeGenVisitor) {
		codeGenVisitor.visitKeyboard(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor) {

	}

}
