package fr.unice.polytech.arduinoml.kernel.behavioral;


import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;

public class ActionLcd<T> extends Action<T> {

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
        codeGenVisitor.visitActionLCD(this);
    }
}
