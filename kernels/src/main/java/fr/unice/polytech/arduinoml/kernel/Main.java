package fr.unice.polytech.arduinoml.kernel;

import fr.unice.polytech.arduinoml.kernel.behavioral.ActionLcd;
import fr.unice.polytech.arduinoml.kernel.behavioral.ActionNumericAssignment;
import fr.unice.polytech.arduinoml.kernel.behavioral.ActionRemoteAssignment;
import fr.unice.polytech.arduinoml.kernel.behavioral.State;
import fr.unice.polytech.arduinoml.kernel.generator.ArduinoCodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.generator.CodeGenVisitor;
import fr.unice.polytech.arduinoml.kernel.structural.Actuator;
import fr.unice.polytech.arduinoml.kernel.structural.Keyboard;
import fr.unice.polytech.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.arduinoml.kernel.structural.Remote;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws Exception {
		App app = new App();
		State read = new State();

		LCD lcd = new LCD();
		lcd.setName("lcd");
		lcd.setPin(1);
		Actuator led = new Actuator();
		led.setPin(12);

		Keyboard keyboard = new Keyboard("1", "2");
		/*
		ActionLcd<String> printString = new ActionLcd();
		printString.setValue("SALUT");
		printString.setComponent(lcd);
		 */
		ActionLcd<Keyboard> printString = new ActionLcd();
		printString.setValue(keyboard);
		printString.setComponent(lcd);

		ActionRemoteAssignment ledKeyboard = new ActionRemoteAssignment();
		ledKeyboard.setValue(keyboard);
		ledKeyboard.setComponent(led);

		read.setActions(Stream.of(ledKeyboard).collect(Collectors.toList()));
		app.setLcds(Stream.of(lcd).collect(Collectors.toList()));
		app.setStates(Stream.of(read).collect(Collectors.toList()));
		app.setComponents(Stream.of(lcd, led).collect(Collectors.toList()));
		app.setRemotes(Stream.of(keyboard).collect(Collectors.toList()));
		app.setInitial(read);

		CodeGenVisitor visitor = new ArduinoCodeGenVisitor();
		app.accept(visitor);
		System.out.println(visitor.getResult().toString());
	}
}
