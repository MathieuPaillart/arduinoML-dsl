package fr.unice.polytech.arduinoMLdsl.visitor.antlr;

import dsl.ArduinoMLBaseVisitor;
import dsl.ArduinoMLParser;
import dsl.ArduinoMLVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class MyOwnVisitor extends ArduinoMLBaseVisitor<String> {

    public String visitTest(ArduinoMLParser.TestContext ctx) {
        return visitChildren(ctx);
    }

    public String visitAnothertest(ArduinoMLParser.AnothertestContext ctx) {
        System.out.println(ctx.integer.getText());
        return visitChildren(ctx);
    }

}
