# How to promote a ruleapp archive for embedded execution with ODM

## Step 1 - Generate a ruleapp from your project
### Step 1.1 - From Rule Designer
In your decision service project generate the ruleapp archive.

### Step 1.2 - From Decision Center
Alternatively you can generate the ruleapp archive for your decision service project in Decision Center.

At the end of this step you have obtained a ruleapp archive in your file system

## Step 2 - Create a Java application to execute the decision service

### Step 2.1 - Package the ODM execution jars
Add theses librairies to load and execute a decision service compiled as a ruleapp archive.

### Step 2.2 - Write a decision service runner
The code snipplet has a generic base. It just varie s depending on the signature of the decision service, the level of trace that your request, and potentially the configuration of the embedded Rule Execution Server rulesession factory.

### Step 2.3 - Package the ruleapp archive in the Java application jar
Add the ruleapp archive jar to the Java application classpath.

### Step 2.4 - Compile
javac commonly through a maven command

### Step 2.5 - Run
java
