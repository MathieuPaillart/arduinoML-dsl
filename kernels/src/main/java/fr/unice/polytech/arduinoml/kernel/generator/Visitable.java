package fr.unice.polytech.arduinoml.kernel.generator;


import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;

/**
 * Visitable Interface.
 */
public interface Visitable {

	/**
	 * Accept a code generator visitor to generate imports part.
	 *
	 * @param codeGenVisitor the code generator visitor
	 */
	void acceptImportGen(ImportCodeGenVisitor codeGenVisitor);

	/**
	 * Accept a code generator visitor to generate setup part.
	 *
	 * @param codeGenVisitor the code generator visitor
	 */
	void acceptSetupGen(SetupCodeGenVisitor codeGenVisitor);

	/**
	 * Accept a code generator visitor to generate core part.
	 *
	 * @param codeGenVisitor the code generator visitor
	 */
	void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor);

}
