package fr.unice.polytech.arduinoml.kernel.behavioral;


import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;

/**
 * Action to display something on an assignable component.
 *
 * @param <T> the value origin
 */
public class ActionDisplay<T> extends Action<T> {

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
		codeGenVisitor.visitActionDisplay(this);
	}
}
