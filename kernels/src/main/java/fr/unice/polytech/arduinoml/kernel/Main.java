package fr.unice.polytech.arduinoml.kernel;

public class Main {
	public static void main(String[] args) throws Exception {
		/*App app = new App();
		State read = new State();

		LCD lcd = new LCD();
		lcd.setName("lcd");
		lcd.setPin(1);
		Actuator led = new Actuator();
		led.setPin(12);

		Keyboard keyboard = new Keyboard("1", "2");
		/*
		ActionDisplay<String> printString = new ActionDisplay();
		printString.setValue("SALUT");
		printString.setAssignableComponent(lcd);

		ActionDisplay<Keyboard> printString = new ActionDisplay();
		printString.setValue(keyboard);
		printString.setAssignableComponent(lcd);

		ActionAssignmentFromRemote ledKeyboard = new ActionAssignmentFromRemote();
		ledKeyboard.setValue(keyboard);
		ledKeyboard.setAssignableComponent(led);

		read.setActions(Stream.of(ledKeyboard).collect(Collectors.toList()));
		app.setLcds(Stream.of(lcd).collect(Collectors.toList()));
		app.setStates(Stream.of(read).collect(Collectors.toList()));
		app.setComponents(Stream.of(lcd, led).collect(Collectors.toList()));
		app.setRemotes(Stream.of(keyboard).collect(Collectors.toList()));
		app.setInitial(read);

		ImportCodeGenVisitor visitor = new ArduinoCoreCodeGenVisitor();
		app.acceptCoreGen(visitor);
		System.out.println(visitor.getResult().toString());*/
	}
}
