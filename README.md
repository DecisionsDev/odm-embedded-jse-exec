# IBM ODM Embedded Java standard execution
## Executing business rules in a standalone Java app with ODM

IBM ODM proposes several options to execute business rules in a Java application:
- remote calls to HTDS through REST/JSON from any language,
- or local calls in Java.

For a local Java execution the preferred API is the ODM RuleSession one to retrieve the ruleapp archive from a relational database, a file system or the class path.
## How to promote a ruleapp for embedded execution
- [steps](docs/STEPS.md)
## Embedded Rule execution in a self contained Java application
- [simple-loan-validation-res-runner](simple-loan-validation-res-runner/README.md): embedded rule execution in a standalone Java application
