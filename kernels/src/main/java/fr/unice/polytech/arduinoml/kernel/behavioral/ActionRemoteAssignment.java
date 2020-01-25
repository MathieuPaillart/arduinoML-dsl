package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.Remote;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;

public class ActionRemoteAssignment extends Action<Remote> {

    @Override
    public void accept(CodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visitActionRemote(this);
    }
}
