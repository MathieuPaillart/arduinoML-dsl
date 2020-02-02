# arduinoML-external-dsl


## Requirements

- `kernel-jvm:1.1-SNAPSHOT` locally in `.m2` folder

## Compilation 

First generate sources to be able to compile : 
```shell script
mvn clean package
```
It will generate sources from antlr.


## Run

You just have to run this maven command : 
```shell script
mvn exec:java
```
You will then find in target/classes/result all the scenarios in arduino.
(scenarios from resources/scenario)


