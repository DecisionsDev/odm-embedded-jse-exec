# A simple loan validation in an embedded Java application
This folder contains the source code to execute the ODM loan validation sample in an embedded Java application.

The ruleapp archive is packaged within the calling Java application. This pattern offers maximum performance as the rules are fully compiled into bytecode thanks to the Decision Engine compilation, loaded only once in memory, and executed with a local Java api. All material is packaged in a standalone Java application without any external dependency.

This project shows how to execute rules packaged in a ruleapp archive in a standalone Java standard application.
While the project leverages the ODM RES RuleSession API the ruleapp archive is embedded in the app jar and does not require any access to a RES DB.
The RuleSession API supports multiple concurrent engines and rulesets for multi-threaded execution.

This API allows to set the persistence though 2 ways: 
- a ra.xml descriptor file detected in the classpath or, 
- Java API. 

The code shows the solution based on the Java api to set a memory persistence. With this memory persistence configuration the ruleapp asked for execution is searched in the classpath once and kept in memory for all executions. 

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

Approve a loan application with busines rules
```console
java -cp target/loanvalidationresrunner-1.0-SNAPSHOT-withodmrt.jar com.ibm.decisions.loanvalidation.LoanValidationRESRunner
```
You should see a trace as follows: 
```console
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: Found default settings in file : META-INF/default_ra.xml.
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.xu.log.internal.LogHandler write
...
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.xu.log.internal.LogHandler write
INFO: The class path is target/loanvalidationresrunner-1.0-SNAPSHOT-withodmrt.jar.
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: Found default settings in file : META-INF/default_ra.xml.
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: Loading default settings from the file : META-INF/default_ra.xml.
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: Copying configuration settings from the rule session factory to the execution unit (XU).
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: XOM repository set to memory persistence mode
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: RuleApp loading strategy 'ResourceJARFile': looking up the RuleApp archive 'loanvalidation.jar' as a resource in the classloader 'sun.misc.Launcher$AppClassLoader@4e25154f'.
...
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: The RuleApp loading strategy 'ResourceDirectory' found the ruleset '/loanvalidation/1.0/loan_validation_with_score_and_grade/1.0'.
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: RESMGMT persistence: Adding RuleApp "/loanvalidation/1.0".
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: RESMGMT persistence: RuleApp "/loanvalidation/1.0" is added.
Jun 10, 2022 4:19:58 PM com.ibm.rules.res.logging.internal.RESLogger log
INFO: The default RuleApp loading strategies added the ruleset '/loanvalidation/1.0/loan_validation_with_score_and_grade/1.0' to the repository.
Jun 10, 2022 4:19:58 PM com.ibm.rules.engine.load.XUEngineDynamicLoaderBuilder buildFromClassLoader
INFO: Build XUEngineDynamicLoaderBuilder from class loader
Jun 10, 2022 4:19:58 PM com.ibm.rules.engine.load.XUEngineDynamicLoaderImpl createEngineDefinitionFromDSAR
INFO: Create engine definition from DSAR
  
Loan approved=true with a yearly repayment=460.5858714129317 insurance required:true messages= [ The loan amount is under the maximum authorized, Very low risk loan, Congratulations! Your loan has been approved] executed in thread main
Messages: The loan amount is under the maximum authorized
Very low risk loan
Congratulations! Your loan has been approved
```
