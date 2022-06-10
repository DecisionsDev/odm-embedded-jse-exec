# Simple loan validation in an embedded Java application
This folder contains the source code to execute the ODM loan validation sample in an embedded Java application.

![Flow](docs/images/decision_automation_in_map_reduce.png "Architecture")

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
java -cp target/simpleloanvalidationresrunner-1.0-SNAPSHOT.jar com.ibm.decisions.loanvalidation.LoanValidationRESRunner --input ../data/loanvalidation/1K/loanvalidation-requests-1K.csv --output ../data/loanvalidation/1K/loanvalidation-decisions-1K.csv
```

Automate loan validation on a JSON applications dataset to produce a JSON decision set.
```console
java -cp target/simpleloanvalidationrunner-1.0-SNAPSHOT-withODM.jar com.ibm.decisions.loanvalidation.LoanValidationRESRunner --input ../data/loanvalidation/1K/loanvalidation-requests-1K.json --output ../data/loanvalidation/1K/loanvalidation-decisions-1K.json --master local[8]
```
