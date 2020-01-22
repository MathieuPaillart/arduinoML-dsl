package fr.unice.polytech.arduinoml.kernel.behavioral;


import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;

public class ActionLcd extends Action {
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void accept(CodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visit(this);
    }
}
