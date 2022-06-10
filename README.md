# IBM ODM Embedded Java standard execution
## Executing business rules in a standalone Java app with ODM

IBM ODM proposes several options to execute business rules in a Java application:
- invocation can be remote to HTDS through a REST/JSON call from any language,
- or local in Java.

For a local execution the preferred API is RuleSession to retreive the ruleapp archive from a relational database, a file system or the class path.
## Embedded Rule execution in a self contained Java application
- [simple-loan-validation-res-runner](simple-loan-validation-res-runner/README.md): embedded rule execution of a simple project
