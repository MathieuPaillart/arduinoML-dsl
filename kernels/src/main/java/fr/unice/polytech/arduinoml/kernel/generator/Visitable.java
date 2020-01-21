package fr.unice.polytech.arduinoml.kernel.generator;

/**
 * Visitable Interface.
 */
public interface Visitable {

	/**
	 * Accept a code generator visitor.
	 *
	 * @param codeGenVisitor the code generator visitor
	 */
	void accept(CodeGenVisitor codeGenVisitor);

}
