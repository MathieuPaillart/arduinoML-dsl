package fr.unice.polytech.arduinoml.kernel.generator;


import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;

/**
 * Visitable Interface.
 */
public interface Visitable {

	/**
	 * Accept a code generator visitor to generate the imports part.
	 *
	 * @param codeGenVisitor the import code generator visitor
	 */
	void acceptImportGen(ImportCodeGenVisitor codeGenVisitor);

	/**
	 * Accept a code generator visitor to generate the setup part.
	 *
	 * @param codeGenVisitor the setup code generator visitor
	 */
	void acceptSetupGen(SetupCodeGenVisitor codeGenVisitor);

	/**
	 * Accept a code generator visitor to generate the core part.
	 *
	 * @param codeGenVisitor the core code generator visitor
	 */
	void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor);

}
