# arduinoML-dsl


#How to run this example

First generate sources to be able to compile : 

> mvn antlr4:antlr4


Once the sources from antlr (lexer/parser/visitor) has been generated :

You just have to launch Runner using IntelliJ or this maven command : 

> mvn exec:java


In both cases : it should print 2 3 4

