# arduinoML-internal-dsl

## Requirements

- `kernel-jvm:1.1-SNAPSHOT` locally in `.m2` folder

## Compilation

```shell script
mvn clean install
```

## Run

```shell script
mvn exec:java -Dexec.args="scripts/Switch.groovy"
```

## Editing groovy in IntelliJ IDEA

In order to avoid compilation errors while editing in IntelliJ, 
you should edit your `arduinoML-internal.iml` by editing this line
```xml
<sourceFolder url="file://$MODULE_DIR$/src/main/groovy" isTestSource="false" />
```
into this line
```xml
<sourceFolder url="file://$MODULE_DIR$/src" isTestSource="false" />
```