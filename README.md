# IBM ODM Embedded Java standard execution
## Executing business rules in a standalone Java app with ODM

IBM ODM proposes several options to execute business rules in a Java application:
- invocation can be remote to HTDS through a REST/JSON call,
- or local.

For a local execution the preferred API is RuleSession to retreive the ruleapp archive from a relational database, a file system or the class path.
## Embedded Rule execution in a self contained Java application
We detail the later design pattern in which the ruleapp archive is packaged within the calling Java application.
This pattern offers maximum performance as the rules are fully compiled into bytecode thanks to the Decision Engine compilation, loaded only once in memory, and executed with a local Java api. All material is packaged in a standalone Java application without any external dependency.

This project shows how to execute rules packaged in a ruleapp archive in a standalone Java standard application.
While the project leverages the ODM RES RuleSession API the ruleapp archive is embedded in the app jar and does not require any access to a RES DB.
