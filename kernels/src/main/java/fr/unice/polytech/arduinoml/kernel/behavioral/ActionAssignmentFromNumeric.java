package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;

/**
 * Assignment from Numeric.
 */
public class ActionAssignmentFromNumeric extends Action<SIGNAL> {

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
        codeGenVisitor.visitActionNumeric(this);
    }
}
