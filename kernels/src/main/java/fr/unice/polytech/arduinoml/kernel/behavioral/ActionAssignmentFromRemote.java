package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.components.remote.RemoteComponent;

public class ActionAssignmentFromRemote extends Action<RemoteComponent> {

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
        codeGenVisitor.visitActionRemote(this);
    }
}
