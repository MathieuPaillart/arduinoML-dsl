package fr.unice.polytech.arduinoml.kernel.generator.imports;

import fr.unice.polytech.arduinoml.kernel.App;
import fr.unice.polytech.arduinoml.kernel.structural.components.bus.LCD;

import java.util.HashMap;
import java.util.Map;

/**
 * Import code generator visitor abstract class.
 *
 * @param <T> the output.
 */
public abstract class ImportCodeGenVisitor<T> {

    /**
     * Helpful context.
     */
    protected Map<String, Object> context = new HashMap<>();

    /**
     * The Code generation result.
     */
    protected T result;

    /**
     * Visits the app.
     *
     * @param app the app
     */
    public abstract void visitApp(App app);

    /**
     * Visits a lcd.
     *
     * @param lcd the lcd
     */
    public abstract void visitLCD(LCD lcd);

    /**
     * Get the result.
     *
     * @return the result.
     */
    public T getResult() {
        return result;
    }

}

