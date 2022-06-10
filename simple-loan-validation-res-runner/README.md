# Simple loan validation in an embedded Java application
This folder contains the source code to execute the ODM loan validation sample in an embedded Java application.

The ruleapp archive is packaged within the calling Java application. This pattern offers maximum performance as the rules are fully compiled into bytecode thanks to the Decision Engine compilation, loaded only once in memory, and executed with a local Java api. All material is packaged in a standalone Java application without any external dependency.

This project shows how to execute rules packaged in a ruleapp archive in a standalone Java standard application.
While the project leverages the ODM RES RuleSession API the ruleapp archive is embedded in the app jar and does not require any access to a RES DB.
The RuleSession API supports multiple concurrent engines and rulesets for multi-threaded execution.

This API allows to set the persistence though 2 ways: 
- a ra.xml descriptor file detected in the classpath, 
- or Java API. 

The code shows the solution based on the [ra.xml](src/main/resources/ra.xml) descriptor.

## Pre requisites
You need an IBM ODM 892 or higher installation to build the application. Root of your ODM installation is referred as <INSTALLDIR> in the instructions below. Maven files will look for the ODM jars under <INSTALLDIR>/executionserver/libs directory.

## Get the code
Clone this repository.
```console
git clone
```
Open an terminal where your have cloned this repository.
```console
cd odm-embedded-jse-exec/simple-loan-validation-res-runner
```
## Build
For ODM 8.11.X and 8.10.X releases
```console
mvn clean install -Dodm.install=<INSTALLDIR> -Dodm.version=<VERSION>
```
Or ODM 8.9.2
```console
mvn clean install -f pom-8.9.xml -Dodm.install=<INSTALLDIR>
```
INSTALLDIR is the ODM 892 or upper version installation directory.
VERSION is the version of ODM by example 8.10.3.0. This number has to match with the jar names.
By example 
```console
mvn clean install -Dodm.install=/Users/johndoe/IBM/ODM8105 -Dodm.version=8.10.5.0
```


## Run

Automate loan validation on a CSV applications dataset to produce a CSV decision set.
```console
java -cp target/loanvalidationresrunner-1.0-SNAPSHOT-withodmrt.jar com.ibm.decisions.loanvalidation.LoanValidationRESRunner
```
