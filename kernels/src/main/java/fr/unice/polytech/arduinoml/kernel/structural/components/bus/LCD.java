package fr.unice.polytech.arduinoml.kernel.structural.components.bus;

import fr.unice.polytech.arduinoml.kernel.generator.core.CoreCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.imports.ImportCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.setup.SetupCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.Assignable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * LCD representation.
 */
public class LCD extends BusComponent implements Assignable {

    /**
     * Construct a LCD from a list of pins.
     *
     * @param pins the list of pins
     */
    public LCD(final List<Integer> pins) {
        super();
        this.pins = pins;
    }

    /**
     * Construct a LCD from bus configuration.
     *
     * @param bus the bus number
     */
    public LCD(final Integer bus) {
        if(bus == 1){
            this.pins = Stream.of(2, 3, 4, 5, 6, 7, 8).collect(Collectors.toList());
        } else {
            this.pins = Stream.of(10, 11, 12, 13, 14, 15, 16).collect(Collectors.toList());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptImportGen(ImportCodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visitLCD(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptSetupGen(SetupCodeGenVisitor codeGenVisitor) {
        codeGenVisitor.visitLCD(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptCoreGen(CoreCodeGenVisitor codeGenVisitor) {

    }
}

