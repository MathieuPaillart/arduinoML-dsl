package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;

public class ActionNumericAssignment extends Action<SIGNAL> {
    @Override
    public void accept(CodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visit(this);
    }
}
