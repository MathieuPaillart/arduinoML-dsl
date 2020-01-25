package fr.unice.polytech.arduinoml.kernel.structural;

import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;

/**
 * LCD representation.
 */
public class LCD extends Component {

    @Override
    public void accept(CodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visitLCD(this);
    }
}

