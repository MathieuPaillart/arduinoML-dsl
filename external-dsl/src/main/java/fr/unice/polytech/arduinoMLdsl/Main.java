package fr.unice.polytech.arduinoMLdsl;

import dsl.ArduinoMLLexer;
import dsl.ArduinoMLParser;
import fr.unice.polytech.arduinoMLdsl.visitor.antlr.ModelBuilder;
import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws Exception {
        System.out.println("\n\nRunning the ANTLR compiler for ArduinoML");
        File file = new File(Main.class.getClassLoader().getResource("scenario").getFile());
        List<Path> listOfFiles = Arrays.stream(file.listFiles()).map(File::toPath).collect(Collectors.toList());
        for (int i = 0; i < listOfFiles.size(); i++) {
            CharStream stream = getCharStream(listOfFiles.get(i));
            App theApp = buildModel(stream);
            //scenario begins at 1 and not 0
            exportToCode(theApp, i + 1);
        }

    }

    private static CharStream getCharStream(Path input) throws IOException {
        System.out.println("Using input file: " + input);
        return CharStreams.fromPath(input);
    }

    private static App buildModel(CharStream stream) {
        ArduinoMLLexer lexer = new ArduinoMLLexer(stream);
        lexer.removeErrorListeners();

        ArduinoMLParser parser = new ArduinoMLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();

        ParseTreeWalker walker = new ParseTreeWalker();
        ModelBuilder builder = new ModelBuilder();

        walker.walk(builder, parser.root()); // parser.root() is the entry point of the grammar

        return builder.retrieve();
    }

    private static void exportToCode(App theApp, int fileNumber) throws IOException {
        Visitor codeGenerator = new ToWiring();
        theApp.accept(codeGenerator);
        Object content = codeGenerator.getResult();
        File file = new File(Main.class.getClassLoader().getResource("result").getFile());
        Files.write(Paths.get(file.toPath().toString() + "/scenario" + fileNumber + ".txt"), content.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }

}
