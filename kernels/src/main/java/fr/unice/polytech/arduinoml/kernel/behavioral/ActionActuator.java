package fr.unice.polytech.arduinoml.kernel.behavioral;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.SIGNAL;

public class ActionActuator extends Action {
    @Override
    public void setValue(String value) {
        //seems overkill but we need to check that the value passed as a string is a SIGNAL value
        this.value = SIGNAL.valueOf(value).name();
    }

    @Override
    public void accept(CodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visit(this);
    }
}
