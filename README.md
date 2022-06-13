# IBM ODM Embedded Java standard execution
## Executing business rules in a standalone Java app with ODM

IBM ODM proposes several options to execute business rules in a Java application by:
- remote calls to HTDS through REST/JSON from any language,
- or local calls in Java.

For a local Java execution the preferred API is the ODM RuleSession. It empowers to retrieve the ruleapp archive from the the classpath, as an alternate persistence mode to relational databases and file systems.

## How to promote a decision service project for embedded execution
- [embedding steps](docs/STEPS.md)
## Embedded Rule execution in a self contained Java application
- [simple-loan-validation-res-runner](simple-loan-validation-res-runner/README.md): embedded rule execution in a standalone Java application
