package fr.unice.polytech.arduinoMLdsl;

import dsl.ArduinoMLLexer;
import dsl.ArduinoMLParser;
import fr.unice.polytech.arduinoMLdsl.visitor.antlr.MyOwnVisitor;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Runner {

    public static void main(String[] args) throws IOException {
        List<File> files = Files.walk(Paths.get("./src/main/resources"))
                .filter(Files::isRegularFile)
                //.filter(path -> ! isTestPath(path))
                .map(Path::toFile)
                .filter(file ->
                        file.getName().endsWith(".ace"))
                .collect(Collectors.toList());
        for (File file : files) {
            String fileContent = getFileLines(file);
            ArduinoMLLexer lexer = new ArduinoMLLexer(CharStreams.fromString(fileContent));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ArduinoMLParser parser = new ArduinoMLParser(tokens);
            ParseTree tree = parser.test();
            MyOwnVisitor visitor = new MyOwnVisitor();
            visitor.visit(tree);
        }
    }
    private static String getFileLines(File file) {
        for (Charset charset : Charset.availableCharsets().values()) {
            String lines = getFileLinesWithEncoding(file, charset);
            if (lines != null) {
                return lines;
            }
        }
        return null;
    }
    private static String getFileLinesWithEncoding(File file, Charset charset) {
        try (Stream<String> lines = Files.lines(file.toPath(), charset)) {
            return lines.collect(Collectors.joining("\n"));
        } catch (UncheckedIOException e) {
            System.err.println(charset.displayName() + ": wrong encoding");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}