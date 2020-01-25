package fr.unice.polytech.arduinoml.kernel.behavioral;


import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;

public class ActionLcd<T> extends Action<T> {

    @Override
    public void accept(CodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visitActionLCD(this);
    }
}
